package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.system.yale.question.service.WxLoginManager;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.HttpUtil;
import com.ming800.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30.
 */
@Service
public class WxLoginManagerImpl implements WxLoginManager {

    @Autowired
    private BaseManager baseManager;

    public String[] getAccessToken(String code) throws Exception {

        String result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7f6aa253b75466dd&secret=04928de13ab23dca159d235ba6dc19ea&code=\" + code + \"&grant_type=authorization_code", null);
        System.out.println(result);
        Map map = JsonUtil.parseJsonStringToMap(result.toString());
        String accessToken = (String) map.get("access_token");
        String openId = (String) map.get("openid");
        System.out.println("acess_token: " + accessToken + "\n" + "openid: " + openId);
        return new String[]{accessToken, openId};
    }

    @Override
    public Consumer saveOrGetConsumer(String accessToken, String openId) throws Exception {

        String result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId, null);
        System.out.println(result);
        Map map = JsonUtil.parseJsonStringToMap(result.toString());
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("unionid", map.get("unionid"));
        Consumer consumer = (Consumer) baseManager.getUniqueObjectByConditions("from Consumer where unionid =:unionid", queryMap);
        if (consumer != null) {
            return consumer;
        }
        consumer = new Consumer();
        consumer.setUnionid((String) map.get("unionid"));
        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);

        System.out.println("unionid :" + consumer.getUnionid());
        return consumer;
    }
}
