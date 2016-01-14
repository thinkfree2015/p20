package com.efeiyi.wx.website.service;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationEdition;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
public interface WxQAManager {

    void saveOpenid2Cache(HttpServletRequest request, HttpServletResponse response, String openid) throws Exception;
    void saveAnswer(Examination examination, ModelMap modelMap) throws Exception;
    Examination generateNewExamination(Consumer consumer, ExaminationEdition examinationEdition) throws Exception;

    List<ExaminationQuestion> saveHelpAnswer(Examination examination, ModelMap modelMap);
}
