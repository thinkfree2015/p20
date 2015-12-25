package com.efeiyi.ec.system.yale.question.controller;

import com.efeiyi.ec.system.yale.question.service.ExaminationManagerService;
import com.efeiyi.ec.system.yale.question.service.impl.ExaminationEditionHolder;
import com.efeiyi.ec.yale.question.model.Examination;
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

    @RequestMapping("/start2Answer.do")
    public ModelAndView start2Answer(HttpServletRequest request,ModelMap modelMap) throws Exception{
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openId",request.getParameter("open_id"));
        queryMap.put("examinationEdition",examinationEditionHolder.getExaminationEditionList().get(0));
        Examination examination = (Examination)baseManager.getUniqueObjectByConditions("from Examination where creatorOpenId=:openId and examinationEdition=:examinationEdition",queryMap);
        modelMap.addAttribute("examination", examinationManagerService.checkStarted() ? examination : examinationManagerService.generateNewExamination());

        return new ModelAndView(request.getParameter("resultPage"),modelMap);
    }

    @RequestMapping("/commitAnswer.do")
    public ModelAndView commitAnswer(HttpServletRequest request, ModelMap modelMap,Examination examination) throws Exception{


        return  new ModelAndView(request.getParameter("resultPage"),modelMap);
    }
}
