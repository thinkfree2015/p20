package com.efeiyi.wx.website.controller;

import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 * 微信答题 Controller
 */

@Controller
@RequestMapping("/wx")
public class WxQAController {

    @Autowired
    private BaseManager baseManager;

    @RequestMapping("/start.do")
    public ModelAndView start(){
        return new ModelAndView("/question/activityDescription");
    }

    @RequestMapping("/descriptionReturn.do")
    public ModelAndView descriptionReturn(HttpServletRequest request, ModelMap modelMap)throws Exception{
        String examId = request.getParameter("examId");
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);

        modelMap.put("examination", exam);
        return new ModelAndView("/question/examinationResult", modelMap);
    }




//    @RequestMapping("/answerExaminationSave.do")
//    public ModelAndView saveQuestionAnswer(HttpServletRequest request){
//        String examId = request.getParameter("examId");
//        String answerList = request.getParameter("answerList");
//        String[] answers = answerList.split(",");
//
//        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examId);
//        List<ExaminationQuestion> eqList = exam.getExaminationQuestionList();
//
//        for (ExaminationQuestion eq: eqList){
//            String answer = answers[eq.getQuestionOrder()-1];
//            eq.setAnswer(answer);
//            if (answer.equalsIgnoreCase(eq.getQuestion().getAnswerTrue())){
//                eq.setAnswerStatus("1");
//            }else {
//                eq.setAnswerStatus("2");
//            }
//            baseManager.saveOrUpdate(eq.getClass().getName(), eq);
//        }
//
//        return new ModelAndView("/question/examinationResult");
//    }

}
