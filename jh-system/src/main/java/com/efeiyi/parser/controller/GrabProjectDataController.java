package com.efeiyi.parser.controller;

import com.efeiyi.ec.organization.model.AddressDistrict;
import com.efeiyi.ec.project.model.Project;//正式使用
import com.efeiyi.parser.service.GrabDataManager;
import com.ming800.core.base.service.BaseManager;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/22.
 * 抓取非遗项目数据
 */
@Controller
@RequestMapping("/grabProjectData")
public class GrabProjectDataController {

    private List<Project> projectList = new ArrayList<>();

    private final String space = new String(new char[]{32});//空格
    private final String colon = new String(new char[]{65306});//中文冒号
    private final String spaceX = new String(new char[]{12288});//大空格
    private final String spot = new String(new char[]{12539});//中间 大点・

    @Autowired
    private BaseManager baseManager;

    @Autowired
    @Qualifier("grabDataManager")
    private GrabDataManager grabDataManager;

    @RequestMapping("/test.do")
    public ModelAndView testMain() throws Exception{
        long begin = System.currentTimeMillis();
        String url = "http://fy.folkw.com/Sort.Asp?Sort_Id=8";

        getLinksInPage(url);//访问外部资源,相对慢

//        System.out.println("\n====================================");
//        System.out.println("共获取非遗项目数： " + projectList.size());

//        System.out.println("\n====================================");
//        System.out.println(System.currentTimeMillis()-begin);


        //遍历 projectList
        System.out.println("\n====================================\n");
        for (Project p: projectList){
            baseManager.saveOrUpdate(Project.class.getName(), p);
        }
        System.out.print("\n===========");
        System.out.println(System.currentTimeMillis()-begin);
        return new ModelAndView("/finish");
    }

    private void getLinksInPage(String url) throws Exception {
        if (url != null) {
            Parser parser = new Parser(url);
            NodeFilter filter = new StringFilter("查看");
            NodeList nodes = parser.extractAllNodesThatMatch(filter);

            if (nodes != null) {
                //遍历所有的节点
                for (int i = 0; i < nodes.size(); i++) {
                    Node eachNode = nodes.elementAt(i);
                    if (eachNode.getParent() instanceof LinkTag) {
                        LinkTag linkTag = (LinkTag) eachNode.getParent();
                        //获得查看链接
                        String hrefPath = linkTag.getLink();
//                        System.out.println(hrefPath);
                        getProjectDetails(hrefPath);
                    }
                }
            }
        }
        //获取下一页链接
        getPageLinks(url);
    }

    private void getPageLinks(String url) throws Exception {
        if (url != null) {
            Parser parser = new Parser(url);
            NodeFilter filter = new StringFilter("下一页");
            NodeList nodes = parser.extractAllNodesThatMatch(filter);

            //耗时68622
            if (nodes != null && nodes.size() == 1) {
                Node eachNode = nodes.elementAt(0);
                if (eachNode.getParent() instanceof LinkTag) {
                    LinkTag linkTag = (LinkTag) eachNode.getParent();
                    //获得html文本的原来的src属性
                    String hrefPath = linkTag.getLink();
                    getLinksInPage(hrefPath);
                }
            }
        }
    }

    private void getProjectDetails(String url) throws Exception {
        Parser parser = new Parser(url);
        parser.setEncoding("GB2312");
        //非遗项目基本属性过滤条件
        NodeFilter fieldFilter = new AndFilter(
                new NodeFilter[]{
                        new TagNameFilter("td"),
                        new HasAttributeFilter("bgcolor","#E0DBBE")
                });
        //非遗项目介绍过滤条件
        NodeFilter introductionFilter = new AndFilter(
                new NodeFilter[]{
                        new TagNameFilter("td"),
                        new HasAttributeFilter("class","b142 h26")
                });

        NodeList fieldNodes = parser.extractAllNodesThatMatch(fieldFilter);
        Parser parser2 = new Parser(url);
        parser2.setEncoding("GB2312");
        NodeList introductionNodes = parser2.extractAllNodesThatMatch(introductionFilter);

        Project project = new Project();//非遗项目

        if (fieldNodes != null) {
            //遍历所有的节点
            for (int i = 0; i < fieldNodes.size(); i++) {
                Node eachNode = fieldNodes.elementAt(i);
                if (eachNode instanceof TableColumn) {
                    TableColumn tdTag = (TableColumn) eachNode;
                    //获得--非遗项目属性
                    String text = tdTag.toPlainTextString().replaceAll("\r\n", "").replaceAll(space, "");
                    if (!text.contains(colon)){
                        continue;
                    }
                    String str = text.substring(text.indexOf(colon)+1).replaceAll(space, "").replaceAll("\t", "");
                    project = saveProject(project, str, i);
                }
            }
        }

        if (introductionNodes != null && introductionNodes.size() == 1) {
            Node eachNode = introductionNodes.elementAt(0);
            if (eachNode instanceof TableColumn) {
                TableColumn tdTag = (TableColumn) eachNode;

                //获得--非遗项目介绍
//                String text = tdTag.toPlainTextString()/*.replaceAll("\r\n", "").replaceAll(space, "")*/;
//                String str = text/*.replaceAll("&nbsp;", "")*/;
//                project.setIntroduce(str);
                //保留img标签
                String text = tdTag.getStringText();
                String str = text/*.replaceAll("&nbsp;", "")*/;
//                project.setIntroduce(str);//测试使用
                project.setDescription(str);
            }
        }

        project.setStatus("2");//0假删 1正常 2隐藏
        project.setCreateDateTime(new Date());

        projectList.add(project);//非遗项目
    }

    //正式
    private Project saveProject(Project project, String str, int tag) throws Exception {
        switch (tag){
            case 0:project.setAddressDistrict(getAddressDistrict(str));break;
            case 1:project.setName(str);break;
            case 2:project.setSerial(str);break;
            case 3:project.setType("1");break;//1技艺  2美术
            case 4:/*project.setDeclarationDate(str);*/break;//没有申报日期
//            case 5:project.setApplicant(str);break;//没有申报人
            case 6:project.setLevel(stringToInt(str));break;//1国家级  2省级  3市级  4县级  5未知
            case 7:/*project.setContact(str);*/break;
            case 8:/*project.setIntroduce(str);*/break;
        }
        return project;
    }

    private String stringToInt(String level){
        if (null != level && !level.trim().isEmpty()){
            return level.contains("国家") ? "1" : level.contains("省") ? "2" : level.contains("市") ? "3" : level.contains("县") ? "4" : "5";
        }
        return "5";
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
