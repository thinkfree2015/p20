package com.efeiyi.parser.service;

import com.efeiyi.ec.organization.model.AddressDistrict;

/**
 * Created by Administrator on 2016/2/24.
 * 抓取数据 service interface
 */
public interface GrabDataManager {
    AddressDistrict getProvince(String province)throws Exception;

    AddressDistrict getProvinceCity(String province, String city)throws Exception;

    AddressDistrict getProvinceCityCounty(String province, String city, String county)throws Exception;
}
