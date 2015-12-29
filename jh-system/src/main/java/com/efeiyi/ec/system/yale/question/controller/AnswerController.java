package com.efeiyi.ec.system.yale.question.controller;

import com.efeiyi.ec.system.yale.question.service.ExaminationManagerService;
import com.efeiyi.ec.system.yale.question.service.impl.ExaminationEditionHolder;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.Participator;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/23.
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    ExaminationManagerService examinationManagerService;
    @Autowired
    BaseManager baseManager;
    @Autowired
    ExaminationEditionHolder examinationEditionHolder;

    @RequestMapping("/start2Answer.do")
    public ModelAndView start2Answer(HttpServletRequest request,ModelMap modelMap) throws Exception{
        LinkedHashMap queryMap = new LinkedHashMap();
        System.out.println(request.getParameter("openid"));
        queryMap.put("openid",request.getParameter("openid"));
        Participator participator = (Participator)baseManager.getUniqueObjectByConditions("from Participator where openId =:openid",queryMap);
        queryMap.put("examinationEdition",examinationEditionHolder.getExaminationEditionList().get(0));
        Examination examination = (Examination)baseManager.getUniqueObjectByConditions("from Examination where creatorOpenId=:openId and examinationEdition=:examinationEdition",queryMap);
        modelMap.addAttribute("examination", examinationManagerService.checkStarted() ? examination : examinationManagerService.generateNewExamination());

        return new ModelAndView(request.getParameter("resultPage"),modelMap);
    }

    @RequestMapping("/commitAnswer.do")
    public ModelAndView commitAnswer(HttpServletRequest request, ModelMap modelMap,Examination examination) throws Exception{


        return  new ModelAndView(request.getParameter("resultPage"),modelMap);
    }


    public ModelAndView inquireProgress(HttpServletRequest request, ModelMap modelMap,Examination examination){

        return  new ModelAndView(request.getParameter("resultPage"),modelMap);
    }


    @RequestMapping(name = "/getTokenByCode.do")
    public void getAccessTokenByCode(HttpServletRequest request, ModelMap modelMap) throws IOException {
        String code = request.getParameter("code");
        System.out.println("code : "+code);
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGetToken = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7f6aa253b75466dd&secret=04928de13ab23dca159d235ba6dc19ea&code=" + code + "&grant_type=authorization_code");
        HttpEntity entity = httpClient.execute(httpGetToken).getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            result.append(line.trim());
        }
        System.out.println(result);
        Map map = JsonUtil.parseJsonStringToMap(result.toString());
        String accessToken = (String)map.get("access_token");
        String openId = (String)map.get("openId");
        System.out.println("acess_token: " + accessToken + "\n" + "openid: " + openId);

        HttpGet httpGetInfo = new HttpGet("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId);
        entity = httpClient.execute(httpGetInfo).getEntity();
        reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line.trim());
        }
        System.out.println(result);
        map = JsonUtil.parseJsonStringToMap(result.toString());
        String nickname = (String)map.get("nickname");
        String headImgUrl = (String)map.get("headimgurl");
        System.out.println("nickname :" + nickname);
        System.out.println("headimgurl :" + headImgUrl);
    }

}
