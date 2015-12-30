package com.efeiyi.ec.system.yale.question.service.impl;

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

    @Override
    public Examination generateNewExamination(Participator participator,ExaminationEdition examinationEdition) throws Exception {
        Examination examination = new Examination();
        examination.setParticipator(participator);
        examination.setSerial(autoSerialManager.nextSerial("purchaseOrder"));
        examination.setExaminationEdition(examinationEdition);
        baseManager.saveOrUpdate(Examination.class.getName(),examination);

        List<Question> questionList = baseManager.listObject("from Question where status != 0",new LinkedHashMap());
        Random random = new Random();
        for(int x=0; x<examinationEdition.getQuestionCount(); x++){
            Question question = questionList.get(random.nextInt());
            ExaminationQuestion examinationQuestion = new ExaminationQuestion();
            examinationQuestion.setQuestion(question);
            examinationQuestion.setExamination(examination);
            examinationQuestion.setQuestionOrder(x);
            baseManager.saveOrUpdate(ExaminationQuestion.class.getName(),examinationQuestion);
        }

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setParticipatorOpenId(participator.getId());
        participationRecord.setRecordType("1");
        participationRecord.setParticipatorOpenId(participator.getOpenid());
        baseManager.saveOrUpdate(ParticipationRecord.class.getName(),participationRecord);

        return examination;
    }

}
