package com.efeiyi.ec.courier.organization.dao.hibernate;

import com.efeiyi.ec.courier.organization.dao.ContentDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/12/11.
 */
public class ContentDataDao extends HibernateDaoSupport implements ContentDao{


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    public Object createQuery(String queryString){
        Query query = getSession().createQuery(queryString);
        return query.uniqueResult();
    }

    public void saveOrUpdate(String modelType, Object object){
        getSession().saveOrUpdate(modelType, object);
    }
}
