package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.system.yale.question.dao.ExaminationDao;
import com.efeiyi.ec.system.yale.question.service.ExaminationManagerService;
import com.efeiyi.ec.yale.question.model.Examination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 * 答题活动 Service
 */

@Service
public class ExaminationManagerImpl implements ExaminationManagerService {

    @Autowired
    @Qualifier("examinationDaoHibernate")
    private ExaminationDao examinationDao;

    @Override
    public List getExaminationQuestionList(Examination examination) throws Exception {
        return examinationDao.getExaminationQuestionList(examination);
    }

    @Override
    public List getQuestionList(Examination examination) throws Exception {
        return examinationDao.getQuestionList(examination);
    }

    @Override
    public List<Examination> generateNewExamination() throws Exception {
        return null;
    }

    @Override
    public boolean checkStarted() throws Exception {
        return false;
    }
}
