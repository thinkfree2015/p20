package com.efeiyi.wx.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/14.
 */
@Controller
public class TestRedirectController {

    @RequestMapping("/redirect.do")
    public void testRedirect(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Random random = new Random();
        int openid = random.nextInt(100000);
        response.sendRedirect("http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/answer/start2Answer.do?openid="  + openid);
    }

    @RequestMapping("/redirect2.do")
    public void testRedirect2(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Random random = new Random();
        String openid = request.getParameter("openid");
        int unionid = random.nextInt(100000);
        response.sendRedirect("http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/answer/getUserInfo2.do?openid=" + openid + "&unionid=" + unionid);
    }
}
