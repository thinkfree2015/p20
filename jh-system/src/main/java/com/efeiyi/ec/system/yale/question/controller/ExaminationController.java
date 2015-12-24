package com.efeiyi.ec.system.yale.question.controller;

import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.efeiyi.ec.yale.question.model.Question;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/12/23.
 * 试卷Controller
 */

@Controller
@RequestMapping("/examination")
public class ExaminationController {
    @Autowired
    private BaseManager baseManager;

    @RequestMapping("/relateQuestions.do")
    public ModelAndView relateQuestions(HttpServletRequest request)throws Exception{
        String examId = request.getParameter("examId");
        String qIdList = request.getParameter("qIdList");
        if (examId.isEmpty() || examId.trim().equals("")){
            throw new Exception("获取试卷ID失败");
        }
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examId);
        buildExaminationQuestion(examination, qIdList);

        return new ModelAndView("redirect:/basic/xm.do?qm=viewExamination&id="+examId);
    }

    @RequestMapping("/delRelatedQuestion.do")
    public ModelAndView delRelatedQuestion(HttpServletRequest request)throws Exception{
        String examId = request.getParameter("examId");
        String eqId = request.getParameter("eqId");
        if (eqId.isEmpty() || eqId.trim().equals("")){
            throw new Exception("获取试卷题目ID失败");
        }
        baseManager.delete(ExaminationQuestion.class.getName(), eqId);
        return new ModelAndView("redirect:/basic/xm.do?qm=viewExamination&id="+examId);
    }

    private void buildExaminationQuestion(Examination examination, String idList){
        String[] ids = idList.split(",");
        for (String id: ids){
            Question question = (Question) baseManager.getObject(Question.class.getName(), id);
            if (question == null){
                continue;
            }
            ExaminationQuestion eq = new ExaminationQuestion();
            eq.setExamination(examination);
            eq.setQuestion(question);
            baseManager.saveOrUpdate(eq.getClass().getName(), eq);
        }
    }

}
