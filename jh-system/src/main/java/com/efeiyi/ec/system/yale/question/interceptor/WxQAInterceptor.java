package com.efeiyi.ec.system.yale.question.interceptor;

import com.efeiyi.ec.system.yale.question.WxQAConst;
import com.efeiyi.ec.system.yale.question.service.WxQAManager;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.model.WxCalledRecord;
import com.ming800.core.util.CookieTool;
import com.ming800.core.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/1/4.
 */
public class WxQAInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private BaseManager baseManager;

    @Autowired
    private WxQAManager wxQAManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String openid;
        openid = (openid = request.getParameter("openid")) != null ? openid : (openid = (String) request.getSession().getAttribute("openid")) != null ? openid : (CookieTool.getCookieByName(request, "openid")) != null ? CookieTool.getCookieByName(request, "openid").getValue() : null;
        if (openid == null) {
            HttpUtil.getHttpResponse("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.appId + "&redirect_uri=" + request.getContextPath() + "/start2Answer.do&response_type=code&scope=snsapi_base&state=1#wechat_redirect", null);
            return super.preHandle(request, response, handler);
        }
        //微信访问过的记录写入session和cookie
        wxQAManager.saveOpenid2Cache(request,response,openid);

        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openid", openid);
        WxCalledRecord wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey='wxqaopenid' and data=:openid", queryMap);
        if (wxCalledRecord == null) {
            HttpUtil.getHttpResponse("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.appId + "&redirect_uri=" + request.getContextPath() + "/getUserInfo.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect", null);
        }
        return super.preHandle(request, response, handler);
    }
}
