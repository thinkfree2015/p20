package com.efeiyi.ec.system.yale.question.controller;

import com.efeiyi.ec.system.yale.question.service.ExaminationManagerService;
import com.efeiyi.ec.system.yale.question.service.WeixinLoginManager;
import com.efeiyi.ec.system.yale.question.service.impl.ExaminationEditionHolder;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.efeiyi.ec.yale.question.model.Participator;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

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
    @Autowired
    WeixinLoginManager weixinLoginManager;

    @RequestMapping("/start2Answer.do")
    public ModelAndView start2Answer(HttpServletRequest request, ModelMap modelMap) throws Exception {
        LinkedHashMap queryMap = new LinkedHashMap();
//        System.out.println(request.getParameter("openid"));
        Participator participator = (Participator) baseManager.getUniqueObjectByConditions("from Participator where openid =:openid", queryMap);
        if(participator == null){
            throw new Exception("请先登录微信");
        }
        queryMap.put("participator", participator);
        queryMap.put("examinationEdition", examinationEditionHolder.getExaminationEditionList().get(0));
        Examination examination = (Examination) baseManager.getUniqueObjectByConditions("from Examination where participator=:participator and examinationEdition=:examinationEdition", queryMap);
        modelMap.addAttribute("examination", examination != null ? examination : examinationManagerService.generateNewExamination(participator,examinationEditionHolder.getExaminationEditionList().get(0)));

        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }

    @RequestMapping("/commitAnswer.do")
    public ModelAndView commitAnswer(HttpServletRequest request, ModelMap modelMap, Examination examination) throws Exception {

        for(ExaminationQuestion examinationQuestion : examination.getExaminationQuestionList()){
            if(examinationQuestion.getQuestion().getAnswerTrue().equals(examinationQuestion.getAnswer())){
                examinationQuestion.setAnswerStatus("1");
                baseManager.saveOrUpdate(examinationQuestion.getClass().getName(),examinationQuestion);
            }else{
                modelMap.put("answer",false);
                break;
            }
        }
        modelMap.put("examination",examination);
        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }


    public ModelAndView inquireProgress(HttpServletRequest request, ModelMap modelMap, Examination examination) {

        return new ModelAndView(request.getParameter("resultPage"), modelMap);
    }


    @RequestMapping("/saveOrGetUserByCode.do")
    public ModelAndView getAccessTokenByCode(HttpServletRequest request, ModelMap modelMap) throws Exception {

        //用code获取token和openid
        String code = request.getParameter("code");
        System.out.println("code : " + code);
        String[] accessTokenAndOpenid = weixinLoginManager.getAccessToken(code);

        //用token获取user信息
        Participator participator = weixinLoginManager.saveOrGetParticipator(accessTokenAndOpenid[0], accessTokenAndOpenid[1]);
        modelMap.put("participator",participator);
        return new ModelAndView(request.getParameter("resultPage"),modelMap);
    }

}
