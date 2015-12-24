package com.efeiyi.ec.system.yale.question.handler;

import com.efeiyi.ec.system.yale.question.service.ExaminationManagerService;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.efeiyi.ec.yale.question.model.Question;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 * 题目表单保存 Handler
 */

public class ExaminationViewHandler implements DoHandler {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private ExaminationManagerService examinationManagerService = (ExaminationManagerService) ApplicationContextUtil.getApplicationContext().getBean("examinationManagerImpl");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest request) throws Exception {

        String examinationId = request.getParameter("id");
        if (examinationId.isEmpty() || examinationId.trim().equals("")) {
            throw new Exception("试卷Id为空");
        }
        Examination examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);
        List<ExaminationQuestion> list = examinationManagerService.getExaminationQuestionList(examination);
        modelMap.put("EQList", list);

        List<Question> questionList = examinationManagerService.getQuestionList(examination);
        modelMap.put("questionList", questionList);

        return modelMap;
    }

}
