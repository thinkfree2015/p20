package com.efeiyi.ec.system.yale.question.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/12/30.
 */
public interface WxQAManager {

    void saveOpenid2Cache(HttpServletRequest request,HttpServletResponse response,String openid) throws Exception;
}
