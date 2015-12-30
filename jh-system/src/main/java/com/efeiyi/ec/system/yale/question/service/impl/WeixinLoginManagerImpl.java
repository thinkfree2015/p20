package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.system.yale.question.service.WeixinLoginManager;
import com.efeiyi.ec.yale.question.model.Participator;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30.
 */
@Service
public class WeixinLoginManagerImpl implements WeixinLoginManager {

    @Autowired
    private BaseManager baseManager;

    public String [] getAccessToken(String code) throws IOException {
        BufferedReader reader = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGetToken = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7f6aa253b75466dd&secret=04928de13ab23dca159d235ba6dc19ea&code=" + code + "&grant_type=authorization_code");
            HttpEntity entity = httpClient.execute(httpGetToken).getEntity();
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line.trim());
            }
            System.out.println(result);
            Map map = JsonUtil.parseJsonStringToMap(result.toString());
            String accessToken = (String) map.get("access_token");
            String openId = (String) map.get("openid");
            System.out.println("acess_token: " + accessToken + "\n" + "openid: " + openId);
            return new String[]{accessToken,openId};
        }catch (Exception e){
            throw e;
        }finally {
            reader.close();
        }
    }

    @Override
    public Participator saveOrGetParticipator(String accessToken, String openId) throws Exception {
        BufferedReader reader = null;
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openId",openId);
        Participator participator = (Participator)baseManager.getUniqueObjectByConditions("from Participator where openid =:openId",queryMap);
        if(participator != null){
            return  participator;
        }

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGetInfo = new HttpGet("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId);
            HttpEntity entity = httpClient.execute(httpGetInfo).getEntity();
            reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line.trim());
            }
            System.out.println(result);
            Map map = JsonUtil.parseJsonStringToMap(result.toString());
            participator.setNickname((String) map.get("nickname"));
            participator.setHeadimgurl((String) map.get("headimgurl"));
            baseManager.saveOrUpdate(Participator.class.getName(),participator);

            System.out.println("nickname :" + participator.getNickname());
            System.out.println("headimgurl :" + participator.getHeadimgurl());
            return participator;
        }catch (Exception e){
            throw  e;
        }finally {
            reader.close();
        }
    }
}
