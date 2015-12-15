package com.efeiyi.ec.courier.grasp.service;

import com.efeiyi.ec.courier.model.CompanyFreight;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/10.
 */
public interface ContentDataManager {



    void mergerUrl(String beginName, String endName, int weight, int pageNum) throws Exception;


    void getContentData(String sendUrl, Map<String, String> map) throws Exception;


    void batchSaveObject(List<CompanyFreight> list)throws Exception;


    Map findCityList(int beginNum, int endNum)throws Exception;
}
