package com.efeiyi.ec.courier.grasp.service.impl;

import com.efeiyi.ec.courier.grasp.service.ContentDataManager;
import com.efeiyi.ec.courier.grasp.util.spiderUtil;
import com.efeiyi.ec.courier.model.CompanyFreight;
import com.efeiyi.ec.courier.organization.dao.hibernate.ContentDataDao;
import com.efeiyi.ec.courier.organization.util.OrganizationConst;
import com.efeiyi.ec.organization.model.AddressCity;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

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
public class ContentDataManagerImpl implements ContentDataManager {
    private static Logger log = Logger.getLogger(ContentDataManagerImpl.class);

    @Autowired
    ContentDataDao contentDataDao;


    @Override
    public void insertDataToDB(List<Map<String, String>> list) {

    }

    @Override
    public String getCityName(int number) {
        AddressCity city = (AddressCity) contentDataDao.createQuery("from AddressCity a where a.sort = ?"+String.valueOf(number));
        if (city != null && city.getId() != null){
            return city.getName();
        }else{
            return "";
        }
    }

    @Override
    public void mergerUrl(String beginName,String endName, String weight, int pageNum) throws Exception {
        String sendUrl = OrganizationConst.EF_COURIER_BASE_URL + "&start=" + beginName +
                "&end="+endName + "&weight="+Double.valueOf(weight)+"&page="+pageNum;
        Map<String,String> map = new HashMap<String, String>();
        map.put("beginName",beginName);
        map.put("endName",endName);
        map.put("weight",weight);
        try {
            sendUrl = URLEncoder.encode(sendUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         getContentData(sendUrl,map);
    }

    @Override
    public void getContentData(String sendUrl,Map<String,String> mapArgs) throws Exception {
        spiderUtil spiderUtil = new spiderUtil();
        List<Object> list = spiderUtil.parserHtml(sendUrl);
        List<CompanyFreight> companyFreights = new ArrayList<CompanyFreight>();
        for (Object obj: list) {//解析返回数据
            if (obj instanceof Map) {
                int pageSize = Integer.parseInt(((Map) obj).get("size").toString());
                for (int j = 2; j <= pageSize; j++) {
                    mergerUrl(mapArgs.get("beginName"), mapArgs.get("endName"), mapArgs.get("weight"), j);
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
        //Thread.currentThread().sleep(1000);
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

        System.out.print("消耗时间--"+(System.currentTimeMillis()-time));
    }

    @Override
    public void findCityList(int beginNum, int endNum) throws Exception{
        Query querySub = (Query) contentDataDao.createQuery("from AddressCity a where a.sort between ?"+beginNum+" and ?"+endNum);
        Query queryAll = (Query) contentDataDao.createQuery("from AddressCity a order by a.sort ace");
        List<AddressCity> allList = queryAll.list();
        List<AddressCity> subList = querySub.list();
        for (int i = 0;i < 11; i++){
            for (AddressCity subCity : subList){
                for (AddressCity allCity : allList){
                    mergerUrl(subCity.getName(),allCity.getName(),String.valueOf(i),1);
                }
            }
        }
    }

}
