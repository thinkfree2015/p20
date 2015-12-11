package com.efeiyi.ec.courier.grasp.service.impl;

import com.efeiyi.ec.courier.grasp.service.ContentDataManager;
import com.efeiyi.ec.courier.model.AddressCity;
import com.efeiyi.ec.courier.organization.util.OrganizationConst;
import com.ming800.core.base.dao.XdoDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ContentDataManagerImpl implements ContentDataManager {

    @Autowired
    XdoDao xdoDao;

    @Override
    public void insertDataToDB(List<Map<String, String>> list) {

    }

    @Override
    public String getCityName(int number) {
        AddressCity city = (AddressCity) xdoDao.getObject("from AddressCity a where a.sort = ?",String.valueOf(number));
        if (city != null && city.getId() != null){
            return city.getName();
        }else{
            return "";
        }
    }

    @Override
    public List<Object> mergerUrl(HttpServletRequest request ,int beginNum, int endNum, String weight, int pageNum) {
        String beginCityName = getCityName(beginNum);
        String endCityName = getCityName(endNum);
        String sendUrl = OrganizationConst.EF_COURIER_BASE_URL + "&start=" + beginCityName +
                "&end="+endCityName + "&weight="+Double.valueOf(weight)+"&page="+pageNum;
        try {
            sendUrl = URLEncoder.encode(sendUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getContentData(sendUrl);
    }

    @Override
    public List<Object> getContentData(String sendUrl) {

        return null;
    }
}
