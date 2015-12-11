package com.efeiyi.ec.courier.grasp.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/10.
 */
public interface ContentDataManager{


    void insertDataToDB(List<Map<String,String>> list);


    String getCityName(int number);


    List<Object> mergerUrl(HttpServletRequest request ,int beginNum, int endNum, String weight, int pageNum);


    List<Object> getContentData(String sendUrl);
}
