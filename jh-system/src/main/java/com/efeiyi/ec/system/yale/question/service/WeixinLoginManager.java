package com.efeiyi.ec.system.yale.question.service;

import com.efeiyi.ec.yale.question.model.Participator;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/30.
 */
public interface WeixinLoginManager {

    String [] getAccessToken(String code) throws IOException;

    Participator saveOrGetParticipator(String accessToken,String openId) throws Exception;
}
