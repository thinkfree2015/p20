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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
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
//        System.out.println("start----openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        //1.找到最近所属用户
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);

        //2.生成或取出待答的题
        Examination examination = wxQAManager.findExaminationByConsumer(consumer, examinationEditionHolder.getExaminationEditionList().get(0));
        examination = (examination != null ? examination : wxQAManager.generateNewExamination(consumer, examinationEditionHolder.getExaminationEditionList().get(0)));

        //3.判断是否已经答题
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //4.答案随机排个序
        List randomAnswerList = wxQAManager.randomSortAnswer(examination);
        modelMap.put("randomAnswerList", randomAnswerList);
        modelMap.put("examination", examination);//用于答题完成后更新答题记录

        System.out.println(new Date() + "\nopenid:" + openid +"--start2Answer--examination:" + examination.getId() + "--participation:" + (participationRecord == null ? "null" : participationRecord.getId()) + "--consumer:" + consumer.getId());
        return new ModelAndView((participationRecord == null ? "/question/examination" : (WxQAConst.recordCreatorType.equals(participationRecord.getRecordType()) ? "/question/examinationResult" : "/question/examinationHelpResult")), modelMap);
    }

    @RequestMapping("/assistAnswer/{examinationId}")
    public ModelAndView assistAnswer(@PathVariable String examinationId, HttpServletRequest request, ModelMap modelMap) throws Exception {
        String openid = wxQAManager.getOpenid(request);
//        System.out.println("assist---openid:" + openid + "   unionid:" + request.getParameter("unionid"));

        //1.找到所属用户
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);

        //2.待继续答的题
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);

        //3.判断是否已经答题
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //4.答案随机排个序
        List randomAnswerList = wxQAManager.randomSortAnswer(examination);
        modelMap.put("randomAnswerList", randomAnswerList);

        modelMap.put("consumer", consumer);
        modelMap.put("examination", examination);
        System.out.println(new Date() + "\nopenid:" + openid +"--assistAnswer--examination:" + examination.getId() + "--participation:" + (participationRecord == null ? "null" : participationRecord.getId()) + "--consumer:" + consumer.getId());

        if (participationRecord == null) {
            if (Examination.examFinished.equals(examination.getStatus()) || Examination.examRewarded.equals(examination.getStatus())) {
                return new ModelAndView("/question/examinationHelpResult", modelMap);
            } else {
                return new ModelAndView("/question/examinationHelp", modelMap);
            }
        } else {
            if (WxQAConst.recordCreatorType.equals(participationRecord.getRecordType())) {
                return new ModelAndView("/question/examinationResult", modelMap);
            } else {
                return new ModelAndView("/question/examinationHelpResult", modelMap);
            }
        }
//        return new ModelAndView((participationRecord == null ? "/question/examinationHelpResult" : (WxQAConst.recordCreatorType.equals(participationRecord.getRecordType()) ? "/question/examinationResult" : "/question/examinationHelpResult")), modelMap);
    }

    @RequestMapping({"/commitAnswer.do/{examinationId}/{answerList}/{consumerId}"})
    public ModelAndView commitAnswer(@PathVariable String examinationId, @PathVariable String answerList, @PathVariable String consumerId, HttpServletRequest request, ModelMap modelMap) throws Exception {
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);

        modelMap.put("answerList", answerList);

        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), consumerId);
        modelMap.put("consumer", consumer);
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);
        if (!Examination.examFinished.equals(examination.getStatus())
                && !Examination.examRewarded.equals(examination.getStatus())
                && participationRecord == null) {
            String openid = wxQAManager.getOpenid(request);
            modelMap.put("openid", openid);
            wxQAManager.saveAnswer(examination, modelMap);
        }

        modelMap.put("examination", examination);

        System.out.println(new Date() + "\nopenid:" + wxQAManager.getOpenid(request) +"--commitAnswer--examination:" + examination.getId() + "--participation:" + (participationRecord == null ? "null" : participationRecord.getId()) + "--consumer:" + consumer.getId());
        return new ModelAndView("/question/examinationResult", modelMap);
    }

    @RequestMapping("/commitHelpAnswer.do/{examinationId}/{answerList}/{consumerId}")
    public ModelAndView commitHelpAnswer(@PathVariable String examinationId, @PathVariable String answerList, @PathVariable String consumerId, HttpServletRequest request, ModelMap modelMap) throws Exception {
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);

        modelMap.put("answerList", answerList);

        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), consumerId);
        modelMap.put("consumer", consumer);

        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);
        if (!Examination.examFinished.equals(examination.getStatus())
                && !Examination.examRewarded.equals(examination.getStatus())
                && participationRecord == null) {
            String openid = wxQAManager.getOpenid(request);
            modelMap.put("openid", openid);
            List<ExaminationQuestion> eqList = wxQAManager.saveHelpAnswer(examination, modelMap);
            modelMap.put("eqList", eqList);
            //标记此人已协助答题，再次刷新时会显示另一套提示信息,如“不好意思，您来晚了，已有好友帮助完成！”
            modelMap.put("assistance", "assisted");
        }
        modelMap.put("examination", examination);
        System.out.println(new Date() + "\nopenid:" + wxQAManager.getOpenid(request) +"--commitHelpAnswer--examination:" + examination.getId() + "--participation:" + (participationRecord == null ? "null" : participationRecord.getId()) + "--consumer:" + consumer.getId());

        return new ModelAndView("/question/examinationHelpResult", modelMap);
    }

    @RequestMapping("/questionDescription.do")
    public ModelAndView questionDescription(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String examId = request.getParameter("examId");
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examId);

        String qId = request.getParameter("qId");
        Question question = (Question) baseManager.getObject(Question.class.getName(), qId);

        modelMap.put("examination", examination);
        modelMap.put("question", question);
        System.out.println(new Date() + "\nopenid:" + wxQAManager.getOpenid(request) +"--questionDescription--examination:" + examination.getId());

        return new ModelAndView("/question/questionDescription", modelMap);
    }

    @RequestMapping("/inquireProgress.do")
    public ModelAndView inquireProgress(HttpServletRequest request, ModelMap modelMap) {
        String examId = request.getParameter("examId");
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examId);
        modelMap.put("examination", examination);
        System.out.println(new Date() + "\nopenid:" + wxQAManager.getOpenid(request) +"--inquireProgress--examination:" + examination.getId());

        return new ModelAndView("/question/shareProgress", modelMap);
    }


    @RequestMapping("/getAward/{examinationId}")
    public ModelAndView getAward(@PathVariable String examinationId, HttpServletRequest request, ModelMap modelMap) throws Exception {
        String openid = wxQAManager.getOpenid(request);
        modelMap.put("openid", openid);
        //1.找到当前用户和题
        Consumer consumer = wxQAManager.findConsumerByOpenid(openid);
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);
        ParticipationRecord participationRecord = wxQAManager.checkIfParticipated(consumer, examination);

        //2.判断是否有领奖资格
        if (participationRecord != null
                && examination.getConsumer().getId().equals(consumer.getId())
                && examination.getFinishDatetime().compareTo(examination.getExaminationEdition().getExpireDate()) <= 0) {
            String idLock = wxQAManager.getLock(examination.getId());
            System.out.println("idLock:" + idLock);
            synchronized (idLock) {
                wxQAManager.reward(participationRecord, modelMap);
            }
        }
        System.out.println(new Date() + "\nopenid:" + openid +"--getAward--examination:" + examination.getId() + "--participation:" + (participationRecord == null ? "null" : participationRecord.getId()) + "--consumer:" + consumer.getId());

        return modelMap.get("coupon") == null ? new ModelAndView("/question/reward", modelMap) : new ModelAndView("/question/rewardCoupon", modelMap);
    }

//    @RequestMapping("/getUserInfo.do")
//    public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        //用code取accessToken
//        String code = request.getParameter("code");
//        System.out.println(new Date() + "code=" + code);
//        if (code == null) {
//            throw new Exception("code is null");
//        }
//        String result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxQAConst.APPID + "&secret=" + WxQAConst.APPSECRET + "&code=" + code + "&grant_type=authorization_code", null);
////        System.out.println("result1:" + result);
//        Map map = JsonUtil.parseJsonStringToMap(result.toString());
//        String accessToken = (String) map.get("access_token");
//        String openid = (String) map.get("openid");
////        System.out.println("acess_token: " + accessToken + "\n" + "openid: " + openid);
//
//        //用accessToken取Info
//        result = HttpUtil.getHttpResponse("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN", null);
////        System.out.println("result2:" + result);
//        map = JsonUtil.parseJsonStringToMap(result.toString());
//        String nickname = (String) map.get("nickname");
//        String headimgurl = (String) map.get("headimgurl");
////        System.out.println("nickname: " + nickname + "\n" + "headimgurl: " + headimgurl);
//
//        WxCalledRecord wxCalledRecord = wxQAManager.wxLogin(map);
//        wxQAManager.saveOpenid2Cache(request, response, wxCalledRecord);
//
//        String requestPath = (String) request.getSession().getAttribute("requestPath");
////        System.out.println("requestPath:" + requestPath);
//        return new ModelAndView("redirect:" + requestPath);
//    }


    @RequestMapping("/getUserInfo2.do")
    public ModelAndView getUserInfo2(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String openid = request.getParameter("openid");
        String nickname = URLDecoder.decode(request.getParameter("nickname"), "UTF-8");
        String headimgurl = URLDecoder.decode(request.getParameter("headimgurl"), "UTF-8");
        System.out.println(new Date() + "\nopenid:" + openid + "--nickname:" + request.getParameter("nickname") + "--headimgurl:" + request.getParameter("headimgurl") + "--nickname2:" + nickname + "--headimgurl2:" + headimgurl);

        WxCalledRecord wxCalledRecord = wxQAManager.wxLogin(openid, nickname, headimgurl);
        wxQAManager.saveOpenid2Cache(request, response, wxCalledRecord);

        String requestPath = (String) request.getSession().getAttribute("requestPath");
//        System.out.println("requestPath:" + requestPath);
        return new ModelAndView("redirect:" + requestPath);
    }

}
