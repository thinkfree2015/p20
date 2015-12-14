package com.efeiyi.ec.courier.organization.dao;

/**
 * Created by Administrator on 2015/12/11.
 */
public interface ContentDao {

    Object createQuery(String queryHql);
    void saveOrUpdate(String modelType, Object object);

}
