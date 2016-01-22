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
        System.out.println(new Date() + "openid:" + openid);
        if (openid == null) {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://dati.efeiyi.com" + request.getServletPath() + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
//            response.sendRedirect("http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/redirect.do");
            return false;
        }

        //保存openid到session和cookie
//        wxQAManager.saveOpenid2Cache(request,response,openid);

//        LinkedHashMap queryMap = new LinkedHashMap();
//        queryMap.put("openid", openid);
//        List wxCalledRecordList = baseManager.listObject("from WxCalledRecord where dataKey='wxqaopenid' and data=:openid order by createDatetime desc", queryMap);
//        WxCalledRecord wxCalledRecord = wxCalledRecordList == null || wxCalledRecordList.size() == 0 ? null : (WxCalledRecord)wxCalledRecordList.get(0);

//        if (wxCalledRecord == null) {
//            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxQAConst.APPID + "&redirect_uri=http://dati.efeiyi.com/answer/getUserInfo.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
//            response.sendRedirect( "http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/redirect2.do?openid=" + openid);
//            return false;
//        }
        return super.preHandle(request, response, handler);
    }

}
