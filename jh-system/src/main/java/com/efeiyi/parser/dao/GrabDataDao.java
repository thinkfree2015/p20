package com.efeiyi.parser.dao;

import com.efeiyi.ec.organization.model.AddressDistrict;

/**
 * Created by Administrator on 2016/2/24.
 * 数据抓取 dao interface
 */
public interface GrabDataDao {
    AddressDistrict getProvince(String province)throws Exception;

    AddressDistrict getProvinceCity(String province, String city)throws Exception;

    AddressDistrict getProvinceCityCounty(String province, String city, String county)throws Exception;
}
