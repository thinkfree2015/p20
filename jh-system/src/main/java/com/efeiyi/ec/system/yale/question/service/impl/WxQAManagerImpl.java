package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.system.yale.question.WxQAConst;
import com.efeiyi.ec.system.yale.question.service.WxQAManager;
import com.ming800.core.util.CookieTool;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/12/30.
 */
@Service
//@EnableTransactionManagement
public class WxQAManagerImpl implements WxQAManager {


    @Override
    public void saveOpenid2Cache(HttpServletRequest request, HttpServletResponse response, String openid) throws Exception {
        request.getSession().setAttribute("openid", openid);
        CookieTool.addCookie(response, "openid", openid, 0, WxQAConst.hostName);
    }
}
