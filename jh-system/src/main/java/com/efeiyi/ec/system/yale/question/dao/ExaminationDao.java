package com.efeiyi.ec.system.yale.question.dao;

import com.efeiyi.ec.yale.question.model.Examination;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 * 答题活动 dao interface
 */
public interface ExaminationDao {

    List getExaminationQuestionList(Examination examination) throws Exception;

    List getQuestionList(Examination examination) throws Exception;
}
