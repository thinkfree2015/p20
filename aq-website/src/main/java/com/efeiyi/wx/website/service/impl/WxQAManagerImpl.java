package com.efeiyi.wx.website.service.impl;

import com.efeiyi.ec.balance.model.BalanceRecord;
import com.efeiyi.ec.organization.model.MyUser;
import com.efeiyi.wx.website.util.WxQAConst;
import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.yale.question.model.*;
import com.efeiyi.wx.website.service.WxQAManager;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.model.WxCalledRecord;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.util.CookieTool;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<String, String> lockMap = new ConcurrentHashMap<>();

    @Override
    public void saveOpenid2Cache(HttpServletRequest request, HttpServletResponse response, String openid) throws Exception {
        request.getSession().setAttribute("openid", openid);
        CookieTool.addCookie(response, "openid", openid, 0, WxQAConst.HOSTNAME);
    }

    @Transactional
    @Override
    public void saveAnswer(Examination examination, ModelMap modelMap) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        String answerList = (String) modelMap.get("answerList");
        String[] answers = answerList.split(",");

        int count = 0;//统计用户回答正确数
        for (ExaminationQuestion eq : examination.getExaminationQuestionList()) {
            String answer = answers[eq.getQuestionOrder() - 1];
            eq.setAnswer(answer);
            if (answer.equalsIgnoreCase(eq.getQuestion().getAnswerTrue())) {
                eq.setAnswerStatus("1");
                count++;
            } else {
                eq.setAnswerStatus("2");
            }
            session.saveOrUpdate(eq.getClass().getName(), eq);
        }

        Consumer consumer = (Consumer) modelMap.get("consumer");
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumerId", consumer.getId());
        queryMap.put("openid",modelMap.get("openid"));
        WxCalledRecord wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey= 'wxqaopenid' and consumerId=:consumerId and data=:openid", queryMap);

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setRecordType("1");
        participationRecord.setConsumer(consumer);
        participationRecord.setExamination(examination);
        participationRecord.setWxCalledRecord(wxCalledRecord);
        if (count == examination.getExaminationQuestionList().size()) {
            participationRecord.setAnswer("1");
            if (examination.getExaminationEdition().getExpireDate().compareTo(new Date()) >= 0) {
                examination.setStatus(Examination.examFinished);//试题状态 都答对且未过期的-2已完成
            }
            examination.setFinishDatetime(new Date());
            session.saveOrUpdate(examination);
        } else {
            participationRecord.setAnswer("2");
        }
        session.saveOrUpdate(ParticipationRecord.class.getName(), participationRecord);

    }

    @Transactional
    @Override
    public List<ExaminationQuestion> saveHelpAnswer(Examination examination, ModelMap modelMap) {
        Session session = sessionFactory.getCurrentSession();

        //用户帮助好友答题的结果map<题目序号, 用户答案>
        Map<Integer, String> map = new HashMap<>();
        String answerList = (String) modelMap.get("answerList");
        String[] answers = answerList.split(" ");
        for (String str : answers) {
            String[] numAnswer = str.split(",");
            map.put(Integer.valueOf(numAnswer[0]), numAnswer[1]);
        }

        //试卷第一次答错的题目list
        List<ExaminationQuestion> errorEQList = new ArrayList<>();
        for (ExaminationQuestion eq : examination.getExaminationQuestionList()) {
            if (eq.getAnswerStatus().equals("2")) {
                errorEQList.add(eq);
            }
        }

        //未保存的试题题目用于结果页展示
        List<ExaminationQuestion> returnEQList = new ArrayList<>();
        //统计用户回答正确题目数
        int count = 0;
        for (ExaminationQuestion eq : errorEQList) {
            String answer = map.get(eq.getQuestionOrder());//获取用户答案; map<题目序号, 用户答案>
            eq.setAnswer(answer);
            if (answer.equalsIgnoreCase(eq.getQuestion().getAnswerTrue())) {
                eq.setAnswerStatus("1");
                count++;
            } else {
                eq.setAnswerStatus("2");
            }
            returnEQList.add(eq);
        }
        //用户回答全部正确
        if (count == errorEQList.size()) {
            for (ExaminationQuestion eq : returnEQList) {
                session.saveOrUpdate(eq.getClass().getName(), eq);
            }
            if (examination.getExaminationEdition().getExpireDate().compareTo(new Date()) >= 0) {
                examination.setStatus(Examination.examFinished);//试题状态 都答对且未过期的-2已完成
            }
            examination.setFinishDatetime(new Date());
            session.saveOrUpdate(Examination.class.getName(), examination);
        }

        Consumer consumer = (Consumer) modelMap.get("consumer");
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumerId", consumer.getId());
        queryMap.put("openid",modelMap.get("openid"));
        WxCalledRecord wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey= 'wxqaopenid' and consumerId=:consumerId and data=:openid", queryMap);

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setRecordType("2");
        participationRecord.setConsumer(consumer);
        participationRecord.setExamination(examination);
        participationRecord.setWxCalledRecord(wxCalledRecord);
        if (count == errorEQList.size()) {
            participationRecord.setAnswer("1");
        } else {
            participationRecord.setAnswer("2");
        }

        session.saveOrUpdate(ParticipationRecord.class.getName(), participationRecord);

        return returnEQList;
    }

    @Transactional
    @Override
    public Examination generateNewExamination(Consumer consumer, ExaminationEdition examinationEdition) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        Examination examination = new Examination();
        examination.setConsumer(consumer);
        examination.setSerial(autoSerialManager.nextSerial("examination"));
        examination.setExaminationEdition(examinationEdition);
        examination.setStatus(Examination.examStarted);//初始化试卷状态 0未分享
        session.saveOrUpdate(Examination.class.getName(), examination);

        List<Question> questionList = baseManager.listObject("from Question where status != 0", new LinkedHashMap());
        List<ExaminationQuestion> eqList = new ArrayList<>();//已选取的题目列表
        List<Integer> indexList = new ArrayList<>();//已取到题目的序号
        Random random = new Random();
        for (int x = 0; x < examinationEdition.getQuestionCount(); ) {
            //防止取到相同的题目
            int index = random.nextInt(questionList.size());
            if (!indexList.contains(index)) {
                x++;
                indexList.add(index);
                Question question = questionList.get(index);
                ExaminationQuestion examinationQuestion = new ExaminationQuestion();
                examinationQuestion.setQuestion(question);
                examinationQuestion.setExamination(examination);
                examinationQuestion.setQuestionOrder(x);
                session.saveOrUpdate(ExaminationQuestion.class.getName(), examinationQuestion);
                eqList.add(examinationQuestion);
            }
        }

        examination.setExaminationQuestionList(eqList);

        return examination;
    }

    @Override
    public Consumer findConsumerByOpenid(String openid) {
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openid", openid);
        WxCalledRecord wxCalledRecord = (WxCalledRecord) (baseManager.listObject("from WxCalledRecord where dataKey = 'wxqaopenid' and data =:openid order by createDatetime desc", queryMap).get(0));
        Consumer consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), wxCalledRecord.getConsumerId());
        return consumer;
    }

    @Override
    public Examination findExaminationByConsumer(Consumer consumer, ExaminationEdition examinationEdition) throws Exception {
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumer", consumer);
        queryMap.put("examinationEdition", examinationEdition);
        String examStr = "from Examination where consumer=:consumer and examinationEdition=:examinationEdition";
        Examination examination = (Examination) baseManager.getUniqueObjectByConditions(examStr, queryMap);
        return examination;
    }

    @Override
    public ParticipationRecord checkIfParticipated(Consumer consumer, Examination examination) {
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumer", consumer);
        queryMap.put("examination", examination);
        String pprStr = "from ParticipationRecord where consumer=:consumer and examination=:examination";
        ParticipationRecord participationRecord = (ParticipationRecord) baseManager.getUniqueObjectByConditions(pprStr, queryMap);
        return participationRecord;
    }

    @Override
    @Transactional
    public void getReward(ParticipationRecord participationRecord, ModelMap modelMap) throws Exception {
        participationRecord = (ParticipationRecord) baseManager.getObject(ParticipationRecord.class.getName(), participationRecord.getId());


        LinkedHashMap queryMap = new LinkedHashMap();
        QuestionSetting questionSetting = (QuestionSetting) baseManager.getUniqueObjectByConditions("from QuestionSetting", queryMap);
        queryMap.put("examinationEdition", participationRecord.getExamination().getExaminationEdition());
        queryMap.put("finishDatetime", participationRecord.getExamination().getFinishDatetime());
        List<ParticipationRecord> participationRecordList = baseManager.listObject("from ParticipationRecord p where examination.examinationEdition=:examinationEdition and recordType='1' and answer='1' and examination.finishDatetime <=:finishDatetime order by examination.finishDatetime asc", queryMap);
        participationRecordList.add(participationRecord);//把自己加进去
        //再次判断是否有领奖资格
        if (Examination.examFinished.equals(participationRecord.getExamination().getStatus())) {
            System.out.println("entered:253");
            BalanceRecord balanceRecord = new BalanceRecord();
            balanceRecord.setConsumer(participationRecord.getConsumer());
            balanceRecord.setCreateDateTime(new Date());
            balanceRecord.setStatus("1");
            if (participationRecordList.size() <= questionSetting.getRank32()) {
                System.out.println("entered:259");
                Session session = sessionFactory.getCurrentSession();
                Query query = session.createQuery("from Consumer where id='" + participationRecord.getConsumer().getId() + "'");
                query.setLockOptions(LockOptions.UPGRADE);
                Consumer consumer = (Consumer) query.uniqueResult();
                Consumer registeredConsumer = transferConsumer(consumer,participationRecord);
                balanceRecord.setCurrentBalance(registeredConsumer.getBalance());
                if (participationRecordList.size() <= questionSetting.getRank12() && participationRecordList.size() >= questionSetting.getRank11()) {
                    balanceRecord.setChangeBalance(questionSetting.getPrize10());
                    balanceRecord.setResultBalance(registeredConsumer.getBalance().add(questionSetting.getPrize10()));
                } else if (participationRecordList.size() >= questionSetting.getRank21() && participationRecordList.size() <= questionSetting.getRank22()) {
                    balanceRecord.setChangeBalance(questionSetting.getPrize20());
                    balanceRecord.setResultBalance(registeredConsumer.getBalance().add(questionSetting.getPrize20()));
                } else if (participationRecordList.size() >= questionSetting.getRank31() && participationRecordList.size() <= questionSetting.getRank32()) {
                    balanceRecord.setChangeBalance(questionSetting.getPrize30());
                    balanceRecord.setResultBalance(registeredConsumer.getBalance().add(questionSetting.getPrize30()));
                } else {
                    throw new Exception("prize exception");
                }
                session.saveOrUpdate(balanceRecord);
                registeredConsumer.setBalance(balanceRecord.getResultBalance());
                session.saveOrUpdate(registeredConsumer);
                session.delete(consumer);
                participationRecord.getExamination().setStatus(Examination.examRewarded);
                participationRecord.setBalanceRecord(balanceRecord);
                session.saveOrUpdate(participationRecord.getExamination());
                session.saveOrUpdate(participationRecord);
            }
        }
        modelMap.addAttribute("rank", participationRecordList.size());
        modelMap.addAttribute("rankList", participationRecordList);
        modelMap.addAttribute("balanceRecord", participationRecord.getBalanceRecord());
    }

    //微信用户和注册有用户绑定后，把微信用户的信息填入注册用户，并更新相关记录
    private Consumer transferConsumer(Consumer consumer,ParticipationRecord participationRecord) {
        //更新consumer

        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Consumer registeredConsumer = (Consumer) baseManager.getObject(Consumer.class.getName(), user.getId());
        registeredConsumer.setUnionid(consumer.getUnionid());
        if(registeredConsumer.getBalance() == null){
            registeredConsumer.setBalance(new BigDecimal(0));
        }
        //更新wxCalledRecord
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumerId",consumer.getId());
        queryMap.put("openid",participationRecord.getWxCalledRecord().getData());
        WxCalledRecord wxCalledRecord = (WxCalledRecord)baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey= 'wxqaopenid' and consumerId=:consumerId and data=:openid",queryMap);
        wxCalledRecord.setConsumerId(registeredConsumer.getId());
        baseManager.saveOrUpdate(wxCalledRecord.getClass().getName(),wxCalledRecord);
        //更新participationRecord
        participationRecord.setConsumer(registeredConsumer);
        participationRecord.setWxCalledRecord(wxCalledRecord);
        //更新examination
        participationRecord.getExamination().setConsumer(registeredConsumer);
        return registeredConsumer;
    }

    public String getLock(ParticipationRecord participationRecord) {
        String idLock = lockMap.get(participationRecord.getId());

        if (idLock == null) {
            synchronized (lockMap) {
                if (lockMap.get(participationRecord.getId()) == null) {
                    lockMap.put(participationRecord.getId(), participationRecord.getId());
                }
                idLock = lockMap.get(participationRecord.getId());
            }
        }
        return idLock;
    }

    @Override
    public String getOpenid(HttpServletRequest request) {
        return request.getParameter("openid") != null ? request.getParameter("openid") : (String) (request.getSession().getAttribute("openid") != null ? request.getSession().getAttribute("openid") : (CookieTool.getCookieByName(request, "openid") != null ? CookieTool.getCookieByName(request, "openid").getValue() : null));
    }
}
