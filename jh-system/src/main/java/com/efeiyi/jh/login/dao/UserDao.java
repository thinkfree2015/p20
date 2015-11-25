package com.efeiyi.jh.login.dao;

import com.efeiyi.ec.organization.model.MyUser;
import com.ming800.core.base.dao.BaseDao;

import java.util.LinkedHashMap;

/**
 * 登录处理dao
 */
public interface UserDao extends BaseDao<MyUser> {

    MyUser getUniqueMyUserByConditions(String branchName, String queryHql, LinkedHashMap<String, Object> queryParamMap);

}
