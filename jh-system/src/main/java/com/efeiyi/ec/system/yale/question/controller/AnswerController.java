package com.efeiyi.ec.system.yale.question.controller;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.system.yale.question.service.WxQAManager;
import com.efeiyi.ec.system.yale.question.service.ExaminationEditionHolder;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ParticipationRecord;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.model.WxCalledRecord;
import com.ming800.core.util.CookieTool;
import com.ming800.core.util.HttpUtil;
import com.ming800.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/23.
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    BaseManager baseManager;
    @Autowired
    ExaminationEditionHolder examinationEditionHolder;
    @Autowired
    WxQAManager wxQAManager;

    @RequestMapping("/start2Answer.do")
    public ModelAndView start2Answer(HttpServletRequest request, /*HttpServletResponse response,*/ ModelMap modelMap) throws Exception {
        String openid = request.getParameter("openid") != null ? request.getParameter("openid") : (String) (request.getSession().getAttribute("openid") != null ? request.getSession().getAttribute("openid") : (CookieTool.getCookieByName(request, "openid") != null ? CookieTool.getCookieByName(request, "openid").getValue() : null));
//        wxQAManager.saveOpenid2Cache(request,response,openid);

        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openid", openid);
        System.out.println("openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        WxCalledRecord wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey = 'wxqaopenid' and data =:openid", queryMap);
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), wxCalledRecord.getConsumerId());
        queryMap.put("consumer", consumer);
        queryMap.put("examinationEdition", examinationEditionHolder.getExaminationEditionList().get(0));
        Examination examination = (Examination) baseManager.getUniqueObjectByConditions("from Examination where consumer=:consumer and examinationEdition=:examinationEdition", queryMap);
        modelMap.addAttribute("examination", examination != null ? examination : wxQAManager.generateNewExamination(consumer, examinationEditionHolder.getExaminationEditionList().get(0)));

        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }

    @RequestMapping("/assistAnswer.do")
    public ModelAndView assistAnswer(HttpServletRequest request, Examination examination, ModelMap modelMap) throws Exception {
        String openid = request.getParameter("openid") != null ? request.getParameter("openid") : (String) (request.getSession().getAttribute("openid") != null ? request.getSession().getAttribute("openid") : (CookieTool.getCookieByName(request, "openid") != null ? CookieTool.getCookieByName(request, "openid").getValue() : null));
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openid", openid);
        System.out.println("openid:" + openid + "   unionid:" + request.getParameter("unionid"));
        WxCalledRecord wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey = 'wxqaopenid' and data =:openid", queryMap);
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), wxCalledRecord.getConsumerId());

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setRecordType("2");
        participationRecord.setConsumer(consumer);
        baseManager.saveOrUpdate(ParticipationRecord.class.getName(),participationRecord);


        modelMap.put("examination",examination);
        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }

    @RequestMapping("/commitAnswer.do")
    public ModelAndView commitAnswer(HttpServletRequest request, ModelMap modelMap, Examination examination) throws Exception {

        wxQAManager.saveAnswer(examination,modelMap);

        modelMap.put("examination", examination);
        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }


    public ModelAndView inquireProgress(HttpServletRequest request, ModelMap modelMap, Examination examination) {

        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }


    @RequestMapping("/getAward.do")
    public ModelAndView getAward(HttpServletRequest request, ModelMap modelMap) throws Exception {

//        String result = HttpUtil.getHttpResponse("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7f6aa253b75466dd&redirect_uri=" + request.getContextPath() + "/getUserByCode.do?resultPage=redirect:" + request.getContextPath() + "/start2Answer.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect", null);
//        Map map = JsonUtil.parseJsonStringToMap(result);
//        String code = (String)map.get("code");
//        System.out.println("code : " + code);

        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }

    @RequestMapping("/getUserInfo.do")
    public ModelAndView getUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //用code取accessToken
        String result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7f6aa253b75466dd&secret=04928de13ab23dca159d235ba6dc19ea&code=" + request.getParameter("code") + "&grant_type=authorization_code", null);
        System.out.println(result);
        Map map = JsonUtil.parseJsonStringToMap(result.toString());
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        System.out.println("acess_token: " + accessToken + "\n" + "openid: " + openid);

        //用accessToken取Info
        result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid, null);
        System.out.println(result);
        map = JsonUtil.parseJsonStringToMap(result.toString());
        String nickname = (String) map.get("nickname");
        String headimgurl = (String) map.get("headimgurl");
        System.out.println("nickname: " + nickname + "\n" + "headimgurl: " + headimgurl);

        //保存用户
        Consumer consumer = new Consumer();
        consumer.setUnionid((String)map.get("unionid"));
        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);
        WxCalledRecord wxCalledRecord = new WxCalledRecord();
        wxCalledRecord.setConsumerId(consumer.getId());
        wxCalledRecord.setDataKey("wxqaopenid");
        wxCalledRecord.setData(openid);
        wxCalledRecord.setAccessToken((String)map.get("refreshToken"));
        wxCalledRecord.setCreateDatetime(new Date());
        //头像暂放callback
        wxCalledRecord.setCallback(headimgurl);
        //名字暂放请求来源
        wxCalledRecord.setRequestSource(nickname);
        baseManager.saveOrUpdate(WxCalledRecord.class.getName(), wxCalledRecord);

        wxQAManager.saveOpenid2Cache(request,response,openid);
        return new ModelAndView("redirect:/start2Answer.do?openid=" + openid);
    }
}
