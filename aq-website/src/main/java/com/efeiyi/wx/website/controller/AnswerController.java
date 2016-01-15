package com.efeiyi.wx.website.controller;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.efeiyi.ec.yale.question.model.ParticipationRecord;
import com.efeiyi.ec.yale.question.model.Question;
import com.efeiyi.wx.website.service.ExaminationEditionHolder;
import com.efeiyi.wx.website.service.WxQAManager;
import com.efeiyi.wx.website.util.WxQAConst;
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
import java.math.BigDecimal;
import java.util.*;

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
        System.out.println("start----openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        //1.找到所属用户
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);

        //2.生成或取出待答的题
        Examination examination = wxQAManager.findExaminationByConsumer(consumer, examinationEditionHolder.getExaminationEditionList().get(0));
        examination = (examination != null ? examination : wxQAManager.generateNewExamination(consumer, examinationEditionHolder.getExaminationEditionList().get(0)));

        //3.判断是否已经答题
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //4.
        modelMap.put("examination", examination);
//        modelMap.put("consumer", consumer);//用于答题完成后更新答题记录
        return new ModelAndView((participationRecord == null ? "/question/examination" : "/question/examinationResult"), modelMap);
    }

    @RequestMapping("/assistAnswer.do")
    public ModelAndView assistAnswer(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String openid = request.getParameter("openid") != null ? request.getParameter("openid") : (String) (request.getSession().getAttribute("openid") != null ? request.getSession().getAttribute("openid") : (CookieTool.getCookieByName(request, "openid") != null ? CookieTool.getCookieByName(request, "openid").getValue() : null));
        System.out.println("assist---openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        //1.找到所属用户
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);

        //2.待继续答的题
        String examId = request.getParameter("examId");
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examId);

        //3.判断是否已经答题
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //4.
        modelMap.put("consumer", consumer);
        modelMap.put("examination", examination);
        return new ModelAndView((participationRecord == null ? "/question/examinationHelp" : "/question/examinationHelpResult"), modelMap);
    }

    @RequestMapping("/commitAnswer.do")
    public ModelAndView commitAnswer(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);

        String answerList = request.getParameter("answerList");
        modelMap.put("answerList", answerList);

        String consumerId = request.getParameter("consumerId");
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), consumerId);
        modelMap.put("consumer", consumer);
        if (!Examination.examFinished.equals(exam.getStatus())
                && !Examination.examRewarded.equals(exam.getStatus())) {
            wxQAManager.saveAnswer(exam, modelMap);
        }

        modelMap.put("examination", exam);
        return new ModelAndView("/question/examinationResult", modelMap);
    }

    @RequestMapping("/commitHelpAnswer.do")
    public ModelAndView commitHelpAnswer(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);

        String answerList = request.getParameter("answerList");
        modelMap.put("answerList", answerList);

        String consumerId = request.getParameter("consumerId");
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), consumerId);
        modelMap.put("consumer", consumer);

        if (!Examination.examFinished.equals(exam.getStatus())
                && !Examination.examRewarded.equals(exam.getStatus())) {
            List<ExaminationQuestion> eqList = wxQAManager.saveHelpAnswer(exam, modelMap);
            modelMap.put("eqList", eqList);
        }
        modelMap.put("examination", exam);

        return new ModelAndView("/question/examinationHelpResult", modelMap);
    }

    @RequestMapping("/questionDescription.do")
    public ModelAndView questionDescription(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);

        String qId = request.getParameter("qId");
        Question question = (Question) baseManager.getObject(Question.class.getName(), qId);

        modelMap.put("examination", exam);
        modelMap.put("question", question);
        return new ModelAndView("/question/questionDescription", modelMap);
    }

    @RequestMapping("/inquireProgress.do")
    public ModelAndView inquireProgress(HttpServletRequest request, ModelMap modelMap) {
        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);
        if (exam != null && exam.getStatus().equals(Examination.examStarted)) {
            exam.setStatus(Examination.examShared);//试题 1已分享
            baseManager.saveOrUpdate(exam.getClass().getName(), exam);
        }
        modelMap.put("examination", exam);

        return new ModelAndView("/question/shareProgress", modelMap);
    }


    @RequestMapping("/getAward.do")
    public ModelAndView getAward(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String openid = request.getParameter("openid") != null ? request.getParameter("openid") : (String) (request.getSession().getAttribute("openid") != null ? request.getSession().getAttribute("openid") : (CookieTool.getCookieByName(request, "openid") != null ? CookieTool.getCookieByName(request, "openid").getValue() : null));
        //1.找到当前用户和题
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);
        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, exam);

        //2.判断是否有领奖资格
        if (participationRecord != null
                && exam.getConsumer().getId().equals(consumer.getId())
//                && Examination.examFinished.equals(exam.getStatus())
                && ParticipationRecord.answerTrue.equals(participationRecord.getAnswer())
                && exam.getFinishDatetime().compareTo(exam.getExaminationEdition().getExpireDate()) <= 0) {
            String idLock = wxQAManager.getLock(participationRecord);

            synchronized (idLock) {
                wxQAManager.getReward(participationRecord,modelMap);
            }
        }
        return new ModelAndView("/question/reward", modelMap);
    }

    @RequestMapping("/getUserInfo.do")
    public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //用code取accessToken
        String code = request.getParameter("code");
        if (code == null) {
            return null;
        }
        String result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7f6aa253b75466dd&secret=04928de13ab23dca159d235ba6dc19ea&code=" + code + "&grant_type=authorization_code", null);
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
        consumer.setUnionid((String) map.get("unionid"));
        consumer.setBalance(new BigDecimal(0));
        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);
        WxCalledRecord wxCalledRecord = new WxCalledRecord();
        wxCalledRecord.setConsumerId(consumer.getId());
        wxCalledRecord.setDataKey(WxQAConst.dataKey);
        wxCalledRecord.setData(openid);
        wxCalledRecord.setAccessToken((String) map.get("refreshToken"));
        wxCalledRecord.setCreateDatetime(new Date());
        //头像暂放callback
        wxCalledRecord.setCallback(headimgurl);
        //名字暂放请求来源
        wxCalledRecord.setRequestSource(nickname);
        baseManager.saveOrUpdate(WxCalledRecord.class.getName(), wxCalledRecord);

        wxQAManager.saveOpenid2Cache(request, response, openid);

        return new ModelAndView("redirect:/answer/start2Answer.do?openid=" + openid);
    }

    @RequestMapping("/getUserInfo2.do")
    public ModelAndView getUserInfo2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openid = request.getParameter("openid");
        String unionid = request.getParameter("unionid");
        Consumer consumer = new Consumer();
        consumer.setUnionid(unionid);
        consumer.setBalance(new BigDecimal(0));
        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);
        WxCalledRecord wxCalledRecord = new WxCalledRecord();
        wxCalledRecord.setConsumerId(consumer.getId());
        wxCalledRecord.setDataKey(WxQAConst.dataKey);
        wxCalledRecord.setData(openid);
        wxCalledRecord.setAccessToken("accesstoken");
        wxCalledRecord.setCreateDatetime(new Date());
        //头像暂放callback
        wxCalledRecord.setCallback("headimgurl");
        //名字暂放请求来源
        wxCalledRecord.setRequestSource("nickname");
        baseManager.saveOrUpdate(WxCalledRecord.class.getName(), wxCalledRecord);

        wxQAManager.saveOpenid2Cache(request, response, openid);
        return new ModelAndView("redirect:/answer/start2Answer.do?openid=" + openid);
    }
}
