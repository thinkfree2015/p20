package com.efeiyi.parser.controller;

import com.efeiyi.ec.master.model.Master;
import com.efeiyi.ec.organization.model.AddressDistrict;
import com.efeiyi.ec.project.model.Project;
import com.efeiyi.parser.service.AliOssUploadManager;
import com.efeiyi.parser.service.GrabDataManager;
import com.ming800.core.base.service.BaseManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/25.
 * 抓取非遗大师数据
 */
@Controller
@RequestMapping("/grabMasterData")
public class GrabMasterDataController {

    private List<String> urlList = new ArrayList<>();
    private List<Project> projectList = new ArrayList<>();
    private List<Master> masterList = new ArrayList<>();

    private final String space = new String(new char[]{32});//空格
    private final String colon = new String(new char[]{65306});//中文冒号
    private final String spaceX = new String(new char[]{12288});//大空格
    private final String spot = new String(new char[]{12539});//中间 大点・

    @Autowired
    private BaseManager baseManager;
    @Autowired
    @Qualifier("aliOssUploadManager")
    private AliOssUploadManager aliOssUploadManager;

    @Autowired
    @Qualifier("grabDataManager")
    private GrabDataManager grabDataManager;

    @RequestMapping("/test.do")
    public ModelAndView testMain() throws Exception{
        long begin = System.currentTimeMillis();
        //传统美术
        String url = "http://www.feiyicheng.com/cms/index.php?act=article&op=inherit&level_id=0&batch_id=0&area_id=&type_id=11&keyword=&button=%E7%A1%AE%E5%AE%9A";
        //传统技艺
        String url2 = "http://www.feiyicheng.com/cms/index.php?act=article&op=inherit&level_id=0&batch_id=0&area_id=&type_id=10&keyword=&button=%E7%A1%AE%E5%AE%9A";
        getInfo(url);
//        getLinksInPage(url);//访问外部资源,相对慢
//        System.out.println("共获取到链接数： " + urlList.size());
//        System.out.println("共获取非遗项目数： " + projectList.size());
        System.out.println("共获取传统美术大师数： " + masterList.size());

//        System.out.println("\n====================================");
//        System.out.println(System.currentTimeMillis()-begin);

        for (Master m:masterList){
//            System.out.println("头像： " + m.getFavicon());
//            System.out.println("姓名： " + m.getFullName());
//            System.out.println("简介： " + m.getBrief());
//            System.out.println("性别： " + (m.getSex().equals("1") ? "男" : "女"));
//            System.out.println("级别： " +
//                    (m.getLevel().equals("1") ? "国家级" : m.getLevel().equals("2") ? "省级" : m.getLevel().equals("3") ? "市级" : m.getLevel().equals("4") ? "县区级" : "未知"));
//            System.out.println("所在地： " + m.getPresentAddress());
//            System.out.println("长简介： " + m.getContent());
            baseManager.saveOrUpdate(Master.class.getName(), m);//保存数据
        }
        System.out.print("传统美术耗时==");
        System.out.println(System.currentTimeMillis()-begin);

        begin = System.currentTimeMillis();
        masterList = new ArrayList<>();
        System.out.println("masterList.size()= " +masterList.size());
        getInfo(url2);
        System.out.println("共获取传统技艺大师数： " + masterList.size());
        for (Master m:masterList){
            baseManager.saveOrUpdate(Master.class.getName(), m);//保存数据
        }

        System.out.print("传统技艺耗时==");
        System.out.println(System.currentTimeMillis()-begin);
        return new ModelAndView("/finish");
}

    //获取列表页大师所在地
    private void getInfo(String url) throws Exception {
        if (null != url){
            Parser parser = new Parser(url);
            NodeFilter filter = new AndFilter(
                    new NodeFilter[]{
                            new TagNameFilter("table"),
                            new HasAttributeFilter("width", "100%"),
                            new HasAttributeFilter("border", "0"),
                            new HasAttributeFilter("cellspacing", "0"),
                            new HasAttributeFilter("cellpadding", "0")
                    });
            NodeList nodes = parser.extractAllNodesThatMatch(filter);

            if (null != nodes && nodes.size() == 1){
                Node table = nodes.elementAt(0);
                NodeList trList = table.getChildren();
                for (int i=0;i<trList.size();i++){
                    Node tr = trList.elementAt(i);
                    if (tr instanceof TableRow) {
                        TableRow row = (TableRow) tr;
                        trHtml(row.getStringText());
                    }
                }
            }
        }
        getNextPageLink(url);
    }

    //根据tr标签获得非遗大师信息
    private void trHtml(String str)throws Exception{
        Parser parser = new Parser(str);
        NodeFilter filter = new TagNameFilter("td");
        NodeList nodeList = parser.extractAllNodesThatMatch(filter);

        if (null != nodeList && nodeList.size() > 0){
            Master master = new Master();
            for (int j = 0; j < nodeList.size(); j++) {
                Node td = nodeList.elementAt(j);
                if (td instanceof TableColumn) {
                    TableColumn column = (TableColumn) td;
                    master = getMaster(master, column, j);
                    if (j == 0){
                        master = getBriefAndContent(master, column.getStringText());
                    }
                }
            }
            master.setStatus("2");//隐藏
            master.setCreateDateTime(new Date());
            masterList.add(master);
        }
    }

    //分析数据，保存到大师对象
    private Master getMaster(Master master, TableColumn td, int tag)throws Exception{
        switch (tag){
            case 0:master.setFavicon(getFavicon(td.getStringText()));break;//头像
            case 1:master.setFullName(td.toPlainTextString().trim());break;//姓名
            case 2:master.setSex(sexToInt(td.toPlainTextString()));break;//性别
            case 3:break;//项目名称
            case 4:break;//类型-传统美术
            case 5:master.setLevel(levelToInt(td.toPlainTextString()));break;//级别-1国家、2省、3市、4县级、5未知
            case 6:master.setPresentAddress(td.toPlainTextString());break;//所在地
        }
        return master;
    }

    //获取大师头像
    private String getFavicon(String str)throws Exception{
        Parser parser = new Parser(str);
        NodeFilter filter = new TagNameFilter("img");
        NodeList nodeList = parser.extractAllNodesThatMatch(filter);
        if (null != nodeList && nodeList.size() == 1){
            Node node = nodeList.elementAt(0);
            if (node instanceof ImageTag){
                ImageTag img = (ImageTag) node;
//                return img.getAttribute("data-src");//获取头像地址

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");

                HttpClient httpClient = new DefaultHttpClient();
                httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
                HttpResponse response = httpClient.execute(new HttpPost(img.getAttribute("data-src")));
                String identify = sdf.format(new Date());
                String url = "photo/" + identify + ".jpg";
                aliOssUploadManager.uploadFile(response.getEntity().getContent(), "tenant", url, response.getEntity().getContentLength());
                Thread.sleep(2000);
                return url;
            }
        }
        return null;
    }

    //大师性别 string To int
    private String sexToInt(String str){
        return str.trim().equals("男") ? "1" : "2";
    }

    //获取大师级别
    private String levelToInt(String str){
        if (null != str && !str.trim().isEmpty()){
            return str.equals("国家级") ? "1" : str.equals("省级") ? "2" : str.equals("市级") ? "3" : str.equals("县区级") ? "4" : "5";
        }
        return "5";
    }

    //获取大师短简介/长简介
    private Master getBriefAndContent(Master master, String str)throws Exception{
        Parser parser = new Parser(str);
        NodeFilter filter = new TagNameFilter("a");
        NodeList nodeList = parser.extractAllNodesThatMatch(filter);
        if (null != nodeList && nodeList.size() == 1){
            Node node = nodeList.elementAt(0);
            if (node instanceof LinkTag){
                LinkTag link = (LinkTag) node;
                String url = link.getAttribute("href");
                getMasterDetails(master, url);
            }
        }
        return master;
    }

    private void getLinksInPage(String url) throws Exception {
        if (null != url) {
            Parser parser = new Parser(url);
            NodeFilter childFilter = new AndFilter(
                    new NodeFilter[]{
                            new TagNameFilter("img"),
                            new HasAttributeFilter("class","image_lazy_load")
                    });
            NodeFilter filter = new HasChildFilter(childFilter);
            NodeList nodes = parser.extractAllNodesThatMatch(filter);

            if (nodes != null) {
                //遍历所有的节点
                for (int i = 0; i < nodes.size(); i++) {
                    Node eachNode = nodes.elementAt(i);
                    if (eachNode instanceof LinkTag) {
                        LinkTag linkTag = (LinkTag) eachNode;
                        //获得查看链接
                        String hrefPath = linkTag.getLink();
                        urlList.add(hrefPath);//非遗项目详细信息页面链接
                        System.out.println(hrefPath);
//                        getMasterDetails(master, hrefPath);
                    }
                }
            }
        }
        //获取下一页链接
//        getPageLinks(url);
    }

    //获取下一页链接
    private void getNextPageLink(String url) throws Exception {
        if (url != null) {
            Parser parser = new Parser(url);
            NodeFilter filter = new StringFilter("下一页");
            NodeList nodes = parser.extractAllNodesThatMatch(filter);

            if (nodes != null && nodes.size() == 1) {
                Node eachNode = nodes.elementAt(0);
                if (eachNode.getParent().getParent() instanceof LinkTag) {
                    LinkTag linkTag = (LinkTag) eachNode.getParent().getParent();
                    //获得下一页链接
                    String hrefPath = linkTag.getLink();
                    getInfo(hrefPath);
                }
            }
        }
    }

    //解析大师详情页，获取短简介和长简介
    private void getMasterDetails(Master master, String url) throws Exception {
        //短简介过滤条件
        Parser parser = new Parser(url);
//        parser.setEncoding("GB2312");
        NodeFilter filter = new TagNameFilter("h3");
        NodeList shotNodes = parser.extractAllNodesThatMatch(filter);
        //长简介过滤条件
        Parser parser2 = new Parser(url);
//        parser2.setEncoding("GB2312");
        filter = new AndFilter(
                new NodeFilter[]{
                        new TagNameFilter("div"),
                        new HasAttributeFilter("class", "ccrcon")
                });
        NodeList langNodes = parser2.extractAllNodesThatMatch(filter);

        //获取短简介
        if (null != shotNodes && shotNodes.size() == 1) {
            Node eachNode = shotNodes.elementAt(0);
            if (eachNode instanceof HeadingTag) {
                HeadingTag h3 = (HeadingTag) eachNode;
                master.setBrief(h3.toPlainTextString());
            }
        }

        //获取长简介
        if (null != langNodes && langNodes.size() == 1) {
            Node eachNode = langNodes.elementAt(0);
            if (eachNode instanceof Div) {
                Div div = (Div) eachNode;
                master.setContent(div.getStringText());
            }
        }

    }



    private AddressDistrict getAddressDistrict(String region) throws Exception {
        String [] address = region.split(spot);
        AddressDistrict addressDistrict;
        switch(address.length){
            case 1:{addressDistrict = getProvince(address[0].replaceAll(space, "").replaceAll(spaceX, ""));}break;
            case 2:{addressDistrict = getProvinceCity(address[0].replaceAll(space, "").replaceAll(spaceX, ""), address[1].replaceAll(space, "").replaceAll(spaceX, ""));}break;
            case 3:{addressDistrict = getProvinceCityCounty(address[0].replaceAll(space, "").replaceAll(spaceX, ""), address[1].replaceAll(space, "").replaceAll(spaceX, ""), address[2].replaceAll(space, "").replaceAll(spaceX, ""));}break;
            default:{addressDistrict = null;}break;
        }

        return addressDistrict;
    }

    private AddressDistrict getProvince(String province) throws Exception {
        return grabDataManager.getProvince(province);
    }

    private AddressDistrict getProvinceCity(String province, String city) throws Exception {
        return grabDataManager.getProvinceCity(province, city);
    }

    private AddressDistrict getProvinceCityCounty(String province, String city, String county) throws Exception {
        return grabDataManager.getProvinceCityCounty(province, city, county);
    }
}
