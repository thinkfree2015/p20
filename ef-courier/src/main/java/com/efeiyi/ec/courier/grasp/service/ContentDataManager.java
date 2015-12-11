package com.efeiyi.ec.courier.grasp.service;

import com.efeiyi.ec.courier.model.CompanyFreight;
import com.efeiyi.ec.organization.model.AddressCity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/10.
 */
public interface ContentDataManager {


    void insertDataToDB(List<Map<String, String>> list);


    String getCityName(int number);


    void mergerUrl( String beginName, String endName, String weight, int pageNum) throws Exception;


    void getContentData(String sendUrl,Map<String,String> map) throws Exception;


    void batchSaveObject(List<CompanyFreight> list)throws Exception;


    void findCityList(int beginNum , int endNum)throws Exception;

}
