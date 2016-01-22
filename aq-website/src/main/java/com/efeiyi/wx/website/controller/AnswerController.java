package com.efeiyi.wx.website.controller;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.organization.model.MyUser;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.efeiyi.ec.yale.question.model.ParticipationRecord;
import com.efeiyi.ec.yale.question.model.Question;
import com.efeiyi.wx.website.service.ExaminationEditionHolder;
import com.efeiyi.wx.website.service.WxQAManager;
import com.efeiyi.wx.website.util.WxQAConst;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.model.WxCalledRecord;
import com.ming800.core.util.HttpUtil;
import com.ming800.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
        String openid = wxQAManager.getOpenid(request);
        System.out.println("start----openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        //1.找到最近所属用户
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);

        //2.生成或取出待答的题
        Examination examination = wxQAManager.findExaminationByConsumer(consumer, examinationEditionHolder.getExaminationEditionList().get(0));
        examination = (examination != null ? examination : wxQAManager.generateNewExamination(consumer, examinationEditionHolder.getExaminationEditionList().get(0)));

        //3.判断是否已经答题
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //4.
        modelMap.put("examination", examination);
//        modelMap.put("consumer", consumer);//用于答题完成后更新答题记录
        return new ModelAndView((participationRecord == null ? "/question/examination" : (WxQAConst.recordCreatorType.equals(participationRecord.getRecordType()) ? "/question/examinationResult" : "/question/examinationHelpResult")), modelMap);
    }

    @RequestMapping("/assistAnswer.do/{examinationId}")
    public ModelAndView assistAnswer(@PathVariable String examinationId, HttpServletRequest request, ModelMap modelMap) throws Exception {
        String openid = wxQAManager.getOpenid(request);
        System.out.println("assist---openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        //1.找到所属用户
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);

        //2.待继续答的题
//        String examId = request.getParameter("examId");
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);

        //3.判断是否已经答题
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //4.
        modelMap.put("consumer", consumer);
        modelMap.put("examination", examination);
        return new ModelAndView((participationRecord == null ? "/question/examinationHelp" : (WxQAConst.recordCreatorType.equals(participationRecord.getRecordType()) ? "/question/examinationResult" : "/question/examinationHelpResult")), modelMap);
    }

    @RequestMapping({"/commitAnswer.do/{examinationId}/{answerList}/{consumerId}"})
    public ModelAndView commitAnswer(@PathVariable String examinationId, @PathVariable String answerList, @PathVariable String consumerId, HttpServletRequest request, ModelMap modelMap) throws Exception {
//        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);

//        String answerList = request.getParameter("answerList");
        modelMap.put("answerList", answerList);

//        String consumerId = request.getParameter("consumerId");
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), consumerId);
        modelMap.put("consumer", consumer);
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, exam);
        if (!Examination.examFinished.equals(exam.getStatus())
                && !Examination.examRewarded.equals(exam.getStatus())
                && participationRecord == null) {
            String openid = wxQAManager.getOpenid(request);
            modelMap.put("openid", openid);
            wxQAManager.saveAnswer(exam, modelMap);
        }

        modelMap.put("examination", exam);
        return new ModelAndView("/question/examinationResult", modelMap);
    }

    @RequestMapping("/commitHelpAnswer.do/{examinationId}/{answerList}/{consumerId}")
    public ModelAndView commitHelpAnswer(@PathVariable String examinationId, @PathVariable String answerList, @PathVariable String consumerId, HttpServletRequest request, ModelMap modelMap) throws Exception {
//        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);

//        String answerList = request.getParameter("answerList");
        modelMap.put("answerList", answerList);

//        String consumerId = request.getParameter("consumerId");
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), consumerId);
        modelMap.put("consumer", consumer);

        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, exam);
        if (!Examination.examFinished.equals(exam.getStatus())
                && !Examination.examRewarded.equals(exam.getStatus())
                && participationRecord == null) {
            String openid = wxQAManager.getOpenid(request);
            modelMap.put("openid", openid);
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


    @RequestMapping("/getAward/{examinationId}")
    public ModelAndView getAward(@PathVariable String examinationId, HttpServletRequest request, ModelMap modelMap) throws Exception {
        String openid = wxQAManager.getOpenid(request);
        modelMap.put("openid", openid);
        //1.找到当前用户和题
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);
        //String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, exam);

        //2.判断是否有领奖资格
        if (participationRecord != null
                && exam.getConsumer().getId().equals(consumer.getId())
//                && Examination.examFinished.equals(exam.getStatus())
                && ParticipationRecord.answerTrue.equals(participationRecord.getAnswer())
                && exam.getFinishDatetime().compareTo(exam.getExaminationEdition().getExpireDate()) <= 0) {
            String idLock = wxQAManager.getLock(exam.getId());
            System.out.println("idLock:" + idLock);
            synchronized (idLock) {
                wxQAManager.reward(participationRecord, modelMap);
            }
        }
        return new ModelAndView("/question/reward", modelMap);
    }

    @RequestMapping("/getUserInfo.do")
    public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //用code取accessToken
        String code = request.getParameter("code");
        System.out.println(new Date() + "code=" + code);
        if (code == null) {
            throw new Exception("code is null");
        }
        String result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxQAConst.APPID + "&secret=" + WxQAConst.APPSECRET + "&code=" + code + "&grant_type=authorization_code", null);
        System.out.println("result1:" + result);
        Map map = JsonUtil.parseJsonStringToMap(result.toString());
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        System.out.println("acess_token: " + accessToken + "\n" + "openid: " + openid);

        //用accessToken取Info
        result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "lang=zh_CN", null, null);
        System.out.println("result2:" + result);
        map = JsonUtil.parseJsonStringToMap(result.toString());
        String nickname = (String) map.get("nickname");
        String headimgurl = (String) map.get("headimgurl");
        System.out.println("nickname: " + nickname + "\n" + "headimgurl: " + headimgurl);

        //保存用户
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("MyUser:" + object);
        Consumer consumer;
        if(object instanceof MyUser){
            MyUser user = (MyUser)object;
            consumer = (Consumer)baseManager.getObject(Consumer.class.getName(),user.getId());
        }else {
            consumer = new Consumer();
            consumer.setBalance(new BigDecimal(0));
        }
        if(map.get("unionid") != null && !map.get("unionid").equals(consumer.getUnionid())) {
            consumer.setUnionid((String) map.get("unionid"));
        }
        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);
//        LinkedHashMap queryMap = new LinkedHashMap();
//        queryMap.put("openid", openid);
//        List wxCalledRecordList = baseManager.listObject("from WxCalledRecord where dataKey='wxqaopenid' and data=:openid order by createDatetime desc", queryMap);
//        WxCalledRecord wxCalledRecord = wxCalledRecordList == null || wxCalledRecordList.size() == 0 ? new WxCalledRecord() : (WxCalledRecord)wxCalledRecordList.get(0);
        WxCalledRecord wxCalledRecord = wxQAManager.findLatestWxCalledRecordByOpenid(openid);
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

//    @RequestMapping("/getUserInfo2.do")
//    public ModelAndView getUserInfo2(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String openid = request.getParameter("openid");
//        String unionid = request.getParameter("unionid");
//        Consumer consumer = new Consumer();
//        consumer.setUnionid(unionid);
//        consumer.setBalance(new BigDecimal(0));
//        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);
//        WxCalledRecord wxCalledRecord = new WxCalledRecord();
//        wxCalledRecord.setConsumerId(consumer.getId());
//        wxCalledRecord.setDataKey(WxQAConst.dataKey);
//        wxCalledRecord.setData(openid);
//        wxCalledRecord.setAccessToken("accesstoken");
//        wxCalledRecord.setCreateDatetime(new Date());
//        //头像暂放callback
//        wxCalledRecord.setCallback("headimgurl");
//        //名字暂放请求来源
//        wxCalledRecord.setRequestSource("nickname");
//        baseManager.saveOrUpdate(WxCalledRecord.class.getName(), wxCalledRecord);
//
//        wxQAManager.saveOpenid2Cache(request, response, openid);
////        return new ModelAndView("redirect:/answer/assistAnswer.do/ijjq442t3di7jl1p?openid=" + openid);
//        return new ModelAndView("redirect:/answer/start2Answer.do?openid=" + openid);
//    }

}
