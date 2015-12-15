package com.efeiyi.jh.service.companyGift.impl;

import com.efeiyi.jh.dao.companyGift.ModalDao;
import com.efeiyi.jh.service.companyGift.ModalServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Administrator on 2015/9/8.
 *
 */
@Service
public class ModalServiceManagerImpl implements ModalServiceManager {

    @Autowired
    @Qualifier("modalDaoHibernate")
    private ModalDao modalDao;

    @Override
    public Set<Object> getListLikesName(String name, String type, String status) throws Exception {
        return modalDao.getListLikesName(name, type, status);
    }

}
