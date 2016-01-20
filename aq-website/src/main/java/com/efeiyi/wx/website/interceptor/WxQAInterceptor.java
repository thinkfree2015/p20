package com.efeiyi.wx.website.interceptor;

import com.efeiyi.ec.organization.model.MyUser;
import com.efeiyi.wx.website.service.WxQAManager;
import com.efeiyi.wx.website.util.WxQAConst;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.model.WxCalledRecord;
import com.ming800.core.util.CookieTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/1/4.
 *
 */
public class WxQAInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private BaseManager baseManager;

    @Autowired
    private WxQAManager wxQAManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String openid = wxQAManager.getOpenid(request);
        System.out.println("openid:" + openid);
        if (openid == null) {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://dati.efeiyi.com/answer/getUserInfo.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://dati.efeiyi.com/answer/start2Answer.do&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
//            response.sendRedirect("http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/redirect.do");
            return false;
        }

        //保存openid到session和cookie
        wxQAManager.saveOpenid2Cache(request,response,openid);

        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openid", openid);
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("MyUser:" + object);
        WxCalledRecord wxCalledRecord;
        if(object instanceof MyUser){
            MyUser user = (MyUser)object;
            queryMap.put("consumerId",user.getId());
            wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey='wxqaopenid' and data=:openid and consumerId=:consumerId", queryMap);
        }else {
            wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey='wxqaopenid' and data=:openid", queryMap);
        }

        if (wxCalledRecord == null) {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://dati.efeiyi.com/answer/getUserInfo.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
//            response.sendRedirect( "http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/redirect2.do?openid=" + openid);
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
