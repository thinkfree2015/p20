package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.system.yale.question.WxQAConst;
import com.efeiyi.ec.system.yale.question.service.WxQAManager;
import com.efeiyi.ec.yale.question.model.*;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.util.CookieTool;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/30.
 */
@Service
@EnableTransactionManagement
public class WxQAManagerImpl implements WxQAManager {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AutoSerialManager autoSerialManager;

    @Autowired
    private BaseManager baseManager;

    @Override
    public void saveOpenid2Cache(HttpServletRequest request, HttpServletResponse response, String openid) throws Exception {
        request.getSession().setAttribute("openid", openid);
        CookieTool.addCookie(response, "openid", openid, 0, WxQAConst.hostName);
    }

    @Transactional
    @Override
    public void saveAnswer(Examination examination ,ModelMap modelMap) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        for (ExaminationQuestion examinationQuestion : examination.getExaminationQuestionList()) {
            if (examinationQuestion.getQuestion().getAnswerTrue().equals(examinationQuestion.getAnswer())) {
                examinationQuestion.setAnswerStatus("1");
                session.saveOrUpdate(examinationQuestion.getClass().getName(), examinationQuestion);
            } else {
                modelMap.put("answerResult", false);
                break;
            }
        }
    }
    @Transactional
    @Override
    public Examination generateNewExamination(Consumer consumer,ExaminationEdition examinationEdition) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        Examination examination = new Examination();
        examination.setConsumer(consumer);
        examination.setSerial(autoSerialManager.nextSerial("purchaseOrder"));
        examination.setExaminationEdition(examinationEdition);
        session.saveOrUpdate(Examination.class.getName(),examination);

        List<Question> questionList = baseManager.listObject("from Question where status != 0",new LinkedHashMap());
        Random random = new Random();
        for(int x=0; x<examinationEdition.getQuestionCount(); x++){
            Question question = questionList.get(random.nextInt());
            ExaminationQuestion examinationQuestion = new ExaminationQuestion();
            examinationQuestion.setQuestion(question);
            examinationQuestion.setExamination(examination);
            examinationQuestion.setQuestionOrder(x);
            session.saveOrUpdate(ExaminationQuestion.class.getName(),examinationQuestion);
        }

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setRecordType("1");
        participationRecord.setUnionid(consumer.getUnionid());
        session.saveOrUpdate(ParticipationRecord.class.getName(),participationRecord);

        return examination;
    }
}
