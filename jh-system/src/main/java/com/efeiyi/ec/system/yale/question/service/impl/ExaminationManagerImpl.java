package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.system.yale.question.dao.ExaminationDao;
import com.efeiyi.ec.system.yale.question.service.ExaminationManagerService;
import com.efeiyi.ec.yale.question.model.*;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.service.AutoSerialManager;
import org.logicalcobwebs.concurrent.FJTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/23.
 * 答题活动 Service
 */

@Service
public class ExaminationManagerImpl implements ExaminationManagerService {

    @Autowired
    @Qualifier("examinationDaoHibernate")
    private ExaminationDao examinationDao;

    @Autowired
    private AutoSerialManager autoSerialManager;

    @Autowired
    private BaseManager baseManager;

    @Override
    public List getExaminationQuestionList(Examination examination) throws Exception {
        return examinationDao.getExaminationQuestionList(examination);
    }

    @Override
    public List getQuestionList(Examination examination) throws Exception {
        return examinationDao.getQuestionList(examination);
    }

}
