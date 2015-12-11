package com.efeiyi.ec.courier.grasp.service.impl;

import com.efeiyi.ec.courier.grasp.service.ContentDataManager;
import com.efeiyi.ec.courier.grasp.util.spiderUtil;
import com.efeiyi.ec.courier.model.AddressCityCopy;
import com.efeiyi.ec.courier.model.CompanyFreight;
import com.efeiyi.ec.courier.organization.dao.hibernate.ContentDataDao;
import com.efeiyi.ec.courier.organization.util.OrganizationConst;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Service
public class ContentDataManagerImpl implements ContentDataManager {
    private static Logger log = Logger.getLogger(ContentDataManagerImpl.class);

    @Autowired
    private ContentDataDao contentDataDao;


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
        spiderUtil spiderUtil = new spiderUtil();
        List<Object> list = null;
        try{
            list = spiderUtil.parserHtml(sendUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<CompanyFreight> companyFreights = new ArrayList<CompanyFreight>();
        for (Object obj: list) {//解析返回数据
            if (obj instanceof Map) {
                int pageSize = Integer.parseInt(((Map) obj).get("size").toString());
                for (int j = 2; j <= pageSize; j++) {
                    mergerUrl(mapArgs.get("beginName"), mapArgs.get("endName"), Integer.parseInt(mapArgs.get("weight")), j);
                }
                System.out.println(((Map) obj).get("size").toString());
            } else if (obj instanceof List) {
                if (!((List<Map<String, String>>) obj).isEmpty()) {
                    for (Map<String, String> map : (List<Map<String, String>>) obj) {
                        log.info("开始解析---->");
                        CompanyFreight cf = new CompanyFreight();
                        cf.setFrom(mapArgs.get("beginName"));
                        cf.setTo(mapArgs.get("endName"));
                        cf.setWeight(mapArgs.get("weight"));
                        cf.setName(map.get("name"));
                        cf.setPrice(map.get("price"));
                        cf.setTimes(map.get("times"));
                        companyFreights.add(cf);
                        System.out.println(map.get("name") + "  " + map.get("price") + "   " + map.get("times"));
                    }
                }
            }
        }
        Thread.currentThread().sleep(1000);
        batchSaveObject(companyFreights);


    }

    @Override
    public void batchSaveObject(List<CompanyFreight> list)throws Exception{
        Session session = contentDataDao.getSession();
        session.setCacheMode(CacheMode.IGNORE);//关闭与二级缓存的交互
        long time = System.currentTimeMillis();

        for(CompanyFreight companyFreight : list){
            session.saveOrUpdate(companyFreight);
        }
        session.flush();
        session.clear();

        System.out.println("消耗时间--"+(System.currentTimeMillis()-time));
    }

    @Override
    public void findCityList(int beginNum, int endNum) throws Exception{
        Query querySub = contentDataDao.getSession().createQuery("from AddressCityCopy as a where a.sort between ? and ?");
        querySub.setInteger(0,beginNum);
        querySub.setInteger(1,endNum);
        Query queryAll = contentDataDao.getSession().createQuery("from AddressCityCopy");
        List<AddressCityCopy> allList = null;
        List<AddressCityCopy> subList = null;
        try{
            allList = queryAll.list();
            subList = querySub.list();
        }catch (Exception e){
           e.printStackTrace();
        }

        for (int i = 1;i <= 10; i++){
            for (AddressCityCopy subCity : subList){
                for (AddressCityCopy allCity : allList){
                    mergerUrl(subCity.getName(),allCity.getName(),i,1);
                }
            }
        }
    }

}
