package com.efeiyi.wx.website.interceptor;

import com.efeiyi.wx.website.service.WxQAManager;
import com.efeiyi.wx.website.util.WxQAConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/4.
 *
 */
public class WxQAInterceptor extends HandlerInterceptorAdapter {

//    @Autowired
//    private BaseManager baseManager;

    @Autowired
    private WxQAManager wxQAManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String openid = wxQAManager.getOpenid(request);
//        System.out.println(new Date() + "openid:" + openid + " code:" + request.getParameter("code"));
        String requestPath = request.getServletPath();
        request.getSession().setAttribute("requestPath",requestPath);
        System.out.println(new Date() + "\nintercepted--openid:" + openid);
        if ((openid == null || "".equals(openid))) {
//            response.sendRedirect("http://www.efeiyi.com/wx/getUserBaseInfo.do?dataKey=openid;nickname;headimgurl&callback=dati.efeiyi.com/answer/getUserInfo2.do&source=dati");
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://dati.efeiyi.com/answer/getUserInfo.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://a.iq7000.com/answer/getUserInfo.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
//            response.sendRedirect("http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/redirect.do");
//            System.out.println("intercepted..");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

}
