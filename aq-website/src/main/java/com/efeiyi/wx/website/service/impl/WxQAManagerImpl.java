package com.efeiyi.wx.website.service.impl;

import com.efeiyi.wx.website.util.WxQAConst;
import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.yale.question.model.*;
import com.efeiyi.wx.website.service.WxQAManager;
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
import java.util.*;

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
        CookieTool.addCookie(response, "openid", openid, 0, WxQAConst.HOSTNAME);
    }

    @Transactional
    @Override
    public void saveAnswer(Examination examination ,ModelMap modelMap) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        String answerList = (String) modelMap.get("answerList");
        String[] answers = answerList.split(",");

        for (ExaminationQuestion eq: examination.getExaminationQuestionList()){
            String answer = answers[eq.getQuestionOrder()-1];
            eq.setAnswer(answer);
            if (answer.equalsIgnoreCase(eq.getQuestion().getAnswerTrue())){
                eq.setAnswerStatus("1");
            }else {
                eq.setAnswerStatus("2");
            }
            session.saveOrUpdate(eq.getClass().getName(), eq);
        }

        Consumer consumer = (Consumer) modelMap.get("consumer");

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setRecordType("1");
        participationRecord.setConsumer(consumer);
        participationRecord.setExamination(examination);
        session.saveOrUpdate(ParticipationRecord.class.getName(),participationRecord);

    }

    @Transactional
    @Override
    public Examination generateNewExamination(Consumer consumer,ExaminationEdition examinationEdition) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        Examination examination = new Examination();
        examination.setConsumer(consumer);
        examination.setSerial(autoSerialManager.nextSerial("examination"));
        examination.setExaminationEdition(examinationEdition);
        session.saveOrUpdate(Examination.class.getName(),examination);

        List<Question> questionList = baseManager.listObject("from Question where status != 0",new LinkedHashMap());
        List<ExaminationQuestion> eqList = new ArrayList<>();//已选取的题目列表
        List<Integer> indexList = new ArrayList<>();//已取到题目的序号
        Random random = new Random();
        for(int x=0; x<examinationEdition.getQuestionCount();){
            //防止取到相同的题目
            int index = random.nextInt(questionList.size());
            if (indexList.size()>0 && indexList.contains(index)){
                continue;
            }
            x++;
            indexList.add(index);

            Question question = questionList.get(index);
            ExaminationQuestion examinationQuestion = new ExaminationQuestion();
            examinationQuestion.setQuestion(question);
            examinationQuestion.setExamination(examination);
            examinationQuestion.setQuestionOrder(x);
            session.saveOrUpdate(ExaminationQuestion.class.getName(),examinationQuestion);
            eqList.add(examinationQuestion);
        }

        examination.setExaminationQuestionList(eqList);

        return examination;
    }
}
