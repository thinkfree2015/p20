package com.efeiyi.jh.model.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/11/26.
 */
@Service
public class SessionHolder {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    public Session getSession() {
        if(session == null){
            synchronized (this){
                if(session == null){
                    session = sessionFactory.openSession();
                }
            }
        }
        return session;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
