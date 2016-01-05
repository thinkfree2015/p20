package com.efeiyi.ec.system.yale.question.service;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationEdition;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 * 答题活动 service interface
 */
public interface ExaminationManagerService {

    /**
     * 获取试卷题目
     * @param examination 试卷
     * @return 与试卷关联的问题List
     * @throws Exception
     */
    List getExaminationQuestionList(Examination examination)throws Exception;

    /**
     * 获取试卷未关联的题目列表
     * @param examination 试卷
     * @return 试卷未关联的题目列表
     */
    List getQuestionList(Examination examination)throws Exception;


}
