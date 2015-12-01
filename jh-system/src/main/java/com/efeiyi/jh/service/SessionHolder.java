package com.efeiyi.jh.service;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/11/26.
 */
@Deprecated
@Service
public class SessionHolder {

    @Autowired
    @Qualifier("scheduleSessionFactory")
    private SessionFactory sessionFactory;
    private Session session;

    public Session getSession() {
        if(session == null || !session.isOpen()){
            synchronized (this){
                if(session == null || !session.isOpen()){
                    session = sessionFactory.openSession();
                    session.setCacheMode(CacheMode.IGNORE);
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
