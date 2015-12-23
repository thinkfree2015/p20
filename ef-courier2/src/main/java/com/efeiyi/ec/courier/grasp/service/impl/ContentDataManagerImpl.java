package com.efeiyi.ec.courier.grasp.service.impl;

import com.efeiyi.ec.courier.grasp.service.ContentDataManager;
import com.efeiyi.ec.courier.grasp.util.spiderUtil;
import com.efeiyi.ec.courier.model.AddressCityCopy;
import com.efeiyi.ec.courier.model.CompanyFreight;
import com.efeiyi.ec.courier.organization.dao.hibernate.ContentDataDao;
import com.efeiyi.ec.courier.organization.util.OrganizationConst;
import com.ming800.core.base.service.BaseManager;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Service
public class ContentDataManagerImpl implements ContentDataManager {
    private static Logger log = Logger.getLogger(ContentDataManagerImpl.class);

    @Autowired
    private ContentDataDao contentDataDao;

    @Autowired
    private BaseManager baseManager;

    private Lock lock = new ReentrantLock();

    FileWriter fw =null;
    File f = null;
    @Override
    public void mergerUrl(String beginName,String endName, int weight, int pageNum) throws Exception {
        String sendUrl = OrganizationConst.EF_COURIER_BASE_URL + "&start=" + URLEncoder.encode(beginName) +
                "&end="+URLEncoder.encode(endName) + "&weight="+weight+"&page="+pageNum;
        Map<String,String> map = new HashMap<String, String>();
        map.put("beginName",beginName);
        map.put("endName",endName);
        map.put("weight",weight+"");
        /*try {
            sendUrl = URLEncoder.encode(sendUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
         getContentData(sendUrl,map);
    }

    @Override
    public void getContentData(String sendUrl,Map<String,String> mapArgs) throws Exception {
        try{
            spiderUtil spiderUtil = new spiderUtil();
            List<Object> list = null;
            try{
                list = spiderUtil.parserHtml(sendUrl);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<CompanyFreight> companyFreights = new ArrayList<CompanyFreight>();
            for (Object obj: list) {//解析返回数据
                if (obj instanceof List) {
                    if (!((List<Map<String, String>>) obj).isEmpty()) {
                        for (Map<String, String> map : (List<Map<String, String>>) obj) {
                            System.out.println("开始解析---->");
                            log.info("开始解析---->");
                            CompanyFreight cf = new CompanyFreight();
                            cf.setFrom(mapArgs.get("beginName"));
                            cf.setTo(mapArgs.get("endName"));
                            cf.setWeight(mapArgs.get("weight"));
                            cf.setName(map.get("name"));
                            cf.setPrice(map.get("price"));
                            cf.setTimes(map.get("times"));
                            companyFreights.add(cf);
                        }
                    }
                }else if (obj instanceof Map){
                    int pageSize = Integer.parseInt(((Map) obj).get("size").toString());
                    for (int j = 2; j <= pageSize; j++) {
                        mergerUrl(mapArgs.get("beginName"), mapArgs.get("endName"), Integer.parseInt(mapArgs.get("weight")), j);
                    }
                    System.out.println(((Map) obj).get("size").toString());
                }
            }
           if (!companyFreights.isEmpty())
            batchSaveObject(companyFreights);
            Thread.currentThread().sleep(1000);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }

    }

    @Override
    public void batchSaveObject(List<CompanyFreight> list)throws Exception{
        try{
           /* Session session = contentDataDao.getSession();
            session.setCacheMode(CacheMode.IGNORE);//关闭与二级缓存的交互
            long time = System.currentTimeMillis();*/
            fw = new FileWriter(f, true);
            for(CompanyFreight companyFreight : list){
                //System.out.println(getEncoding(companyFreight.getName()));
                //session.saveOrUpdate(companyFreight);
                //System.out.println(companyFreight.toString());
                fw.write(companyFreight.toString());
            }
           /* session.flush();
            session.clear();*/

            //System.out.println("消耗时间--" + (System.currentTimeMillis() - time));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (fw!=null){
                fw.close();
            }
        }



    }

    public void batchSaveObjects(int beginNum, int endNum) throws Exception{
            Map  map =findCityList(beginNum,endNum);
        try {//如果文件存在，则追加内容；如果文件不存在，则创建文件
             f=new File("d:\\courier"+beginNum+".txt");
            System.out.println("已创建文件d:\\courier"+beginNum+".txt");
          } catch (Exception e) {
            e.printStackTrace();
          }
        if (!map.isEmpty()){
            List<AddressCityCopy> allList =(List<AddressCityCopy>)map.get("allList");
            List<AddressCityCopy> subList =(List<AddressCityCopy>)map.get("subList");
            System.out.println("开始跑任务了");
             for (int i = 1;i <= 5; i++){
                for (AddressCityCopy subCity : subList){
                    for (AddressCityCopy allCity : allList){
                        mergerUrl(subCity.getName(),allCity.getName(),i,1);
                    }
                    log.error(i+"kg "+subCity.getName()+" 插入任务执行完成===================================");
                    System.out.println(i+"kg "+subCity.getName()+" 插入任务执行完成");
                }
                 log.error(i+"kg  插入任务执行完成===================================");
                 System.out.println(i+"kg  插入任务执行完成");
            }
            System.out.println("插入任务执行完成");
        }
   /*     try {//如果文件存在，则追加内容；如果文件不存在，则创建文件
            f=new File("d:\\courier"+beginNum+".txt");
            mergerUrl("北京","北京",1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }




    @Override
    public Map findCityList(int beginNum, int endNum) throws Exception{
        Map map = new HashMap();
        try{
        Query querySub = contentDataDao.getSession().createQuery("from AddressCityCopy as a where a.sort between ? and ?");
        querySub.setInteger(0,beginNum);
        querySub.setInteger(1,endNum);
        Query queryAll = contentDataDao.getSession().createQuery("from AddressCityCopy");
        List<AddressCityCopy> allList =queryAll.list();
        List<AddressCityCopy> subList = querySub.list();
            map.put("allList",allList);
            map.put("subList",subList);
            /*for (int i = 1;i <= 5; i++){
                for (AddressCityCopy subCity : subList){
                    for (AddressCityCopy allCity : allList){
                        mergerUrl(subCity.getName(),allCity.getName(),i,1);
                    }
                }
            }*/
            System.out.println("查询任务完成");
        }catch (Exception e){
           e.printStackTrace();
        }

    return  map;

    }
    private  String getEncoding(String str) {

        String encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
         encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        return "";
    }
    public CompanyFreight convert(CompanyFreight companyFreight){
        CompanyFreight companyFreight1 = new CompanyFreight();
        try{
            companyFreight1.setTo(    new String(companyFreight.getTo().getBytes("gb2312"),"utf-8"));
            companyFreight1.setWeight(new String(companyFreight.getWeight().getBytes("gb2312"), "utf-8"));
            companyFreight1.setPrice( new String(companyFreight.getPrice().getBytes("gb2312"), "utf-8"));
            companyFreight1.setFrom(  new String(companyFreight.getFrom().getBytes("gb2312"), "utf-8"));
            companyFreight1.setName(  new String(companyFreight.getName().getBytes("gb2312"), "utf-8"));
            companyFreight1.setTimes( new String(companyFreight.getTimes().getBytes("gb2312"), "utf-8"));

        }catch (Exception e){
           e.printStackTrace();
        }
return  companyFreight1;
    }


}
