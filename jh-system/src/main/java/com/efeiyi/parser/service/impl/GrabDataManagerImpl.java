package com.efeiyi.parser.service.impl;

import com.efeiyi.ec.organization.model.AddressDistrict;
import com.efeiyi.parser.dao.GrabDataDao;
import com.efeiyi.parser.dao.hibernate.GrabDataDaoHibernate;
import com.efeiyi.parser.service.GrabDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/24.
 * 抓取数据 service implement
 */
@Service
public class GrabDataManagerImpl implements GrabDataManager {

    @Autowired
    @Qualifier("grabDataDao")
    private GrabDataDao grabDataDao;

//    private GrabDataDao grabDataDao = new GrabDataDaoHibernate();

    @Override
    public AddressDistrict getProvince(String province)throws Exception {
        return grabDataDao.getProvince(province);
    }

    @Override
    public AddressDistrict getProvinceCity(String province, String city) throws Exception {
        return grabDataDao.getProvinceCity(province, city);
    }

    @Override
    public AddressDistrict getProvinceCityCounty(String province, String city, String county) throws Exception {
        return grabDataDao.getProvinceCityCounty(province, city, county);
    }
}
