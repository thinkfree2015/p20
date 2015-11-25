package com.efeiyi.jh.login.dao.hibernate;

import com.efeiyi.ec.organization.model.MyUser;
import com.efeiyi.jh.login.dao.UserDao;
import com.ming800.core.base.dao.hibernate.BaseDaoSupport;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.LinkedHashMap;

/**
 * 登录处理dao Hibernate
 */
public class UserDaoHibernate extends BaseDaoSupport<MyUser> implements UserDao {

    @Override
    public MyUser getUniqueMyUserByConditions(String branchName, String queryHql, LinkedHashMap<String, Object> queryParamMap) {

        Session tempSession = super.getSessionFactory().openSession();

        MyUser myUser = null;
        try {
            Query listQuery = tempSession.createQuery(queryHql);
            setQueryParams(listQuery, queryParamMap);
            myUser = (MyUser) listQuery.uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            tempSession.close();
        }

        return myUser;
    }

}
