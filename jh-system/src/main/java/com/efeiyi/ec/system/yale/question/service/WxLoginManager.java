package com.efeiyi.ec.system.yale.question.service;

import com.efeiyi.ec.organization.model.Consumer;


/**
 * Created by Administrator on 2015/12/30.
 */
public interface WxLoginManager {

    String [] getAccessToken(String code) throws Exception;

    Consumer saveOrGetConsumer(String accessToken,String openId) throws Exception;
}
