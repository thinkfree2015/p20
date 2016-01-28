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
import java.net.URLEncoder;
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
    public void saveOpenid2Cache(HttpServletRequest request, HttpServletResponse response, WxCalledRecord wxCalledRecord) throws Exception {
        request.getSession().setAttribute("openid", wxCalledRecord.getData());
        request.getSession().setAttribute("headimgurl", wxCalledRecord.getCallback());
        request.getSession().setAttribute("nickname", wxCalledRecord.getRequestSource());
        CookieTool.addCookie(response, "openid", wxCalledRecord.getData(), 0, WxQAConst.HOSTNAME);
        CookieTool.addCookie(response, "headimgurl", wxCalledRecord.getCallback(), 0, WxQAConst.HOSTNAME);
        //tomcat7以上cookie存中文要转码
        CookieTool.addCookie(response, "nickname", URLEncoder.encode(wxCalledRecord.getRequestSource(), "UTF-8"), 0, WxQAConst.HOSTNAME);
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
        queryMap.put("openid", modelMap.get("openid"));
        WxCalledRecord wxCalledRecord = (WxCalledRecord) baseManager.getUniqueObjectByConditions("from WxCalledRecord where dataKey= 'wxqaopenid' and consumerId=:consumerId and data=:openid", queryMap);

        ParticipationRecord participationRecord = new ParticipationRecord();
        participationRecord.setCreateDatetime(new Date());
        participationRecord.setRecordType("1");
        participationRecord.setConsumer(consumer);
        participationRecord.setExamination(examination);
        participationRecord.setWxCalledRecord(wxCalledRecord);
        if (count == examination.getExaminationQuestionList().size()) {
            participationRecord.setAnswer("1");
//            if (examination.getExaminationEdition().getExpireDate().compareTo(new Date()) >= 0) {
                examination.setStatus(Examination.examFinished);//试题状态 都答对且未过期的-2已完成
//            }
            examination.setFinishDatetime(new Date());
            session.saveOrUpdate(examination);
        } else {
            participationRecord.setAnswer("2");
        }
        session.saveOrUpdate(ParticipationRecord.class.getName(), participationRecord);

    }

    @Transactional
    @Override
    public List<ExaminationQuestion> saveHelpAnswer(Examination examination, ModelMap modelMap) throws Exception {
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
//            if (examination.getExaminationEdition().getExpireDate().compareTo(new Date()) >= 0) {
                examination.setStatus(Examination.examFinished);//试题状态 都答对且未过期的-2已完成
//            }
            examination.setFinishDatetime(new Date());
            session.saveOrUpdate(Examination.class.getName(), examination);
        }

        Consumer consumer = (Consumer) modelMap.get("consumer");
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumerId", consumer.getId());
        queryMap.put("openid", modelMap.get("openid"));
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
        ParticipationRecord creatorRecord = examination.getParticipationRecordList().get(0);
        if (creatorRecord == null) {
            throw new Exception("found creatorParticipationRecord null.Invalid access!");
        }
        participationRecord.setCreationRecord(creatorRecord);
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
    public Consumer findConsumerByOpenid(String openid) throws Exception {
        WxCalledRecord wxCalledRecord = findLatestWxCalledRecordByOpenid(openid);
        if (wxCalledRecord == null || wxCalledRecord.getConsumerId() == null) {
            throw new Exception("invalid openid！");
        }
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
    public ParticipationRecord checkIfParticipated(Consumer consumer, Examination examination) throws Exception {
        if (consumer == null || examination == null) {
            throw new Exception("invalid consumer or examination!");
        }
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("consumer", consumer);
        queryMap.put("examination", examination);
        String pprStr = "from ParticipationRecord where consumer=:consumer and examination=:examination";
        ParticipationRecord participationRecord = (ParticipationRecord) baseManager.getUniqueObjectByConditions(pprStr, queryMap);
        return participationRecord;
    }

    @Transactional
    @Override
    public void reward(ParticipationRecord participationRecord, ModelMap modelMap) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        participationRecord = (ParticipationRecord) session.get(ParticipationRecord.class.getName(), participationRecord.getId());
        LinkedHashMap queryMap = new LinkedHashMap();
        QuestionSetting questionSetting = (QuestionSetting) baseManager.getUniqueObjectByConditions("from QuestionSetting", queryMap);
        queryMap.put("examinationEdition", participationRecord.getExamination().getExaminationEdition());
        queryMap.put("finishDatetime", participationRecord.getExamination().getExaminationEdition().getExpireDate());
//        queryMap.put("finished", Examination.examFinished);
        queryMap.put("rewarded", Examination.examRewarded);
        List<ParticipationRecord> participationRecordList = baseManager.listObject("from ParticipationRecord p where p.examination.examinationEdition=:examinationEdition and p.recordType='1' and p.examination.status =:rewarded and examination.finishDatetime <=:finishDatetime order by examination.finishDatetime asc", queryMap);
        System.out.println("rankList:" + participationRecordList.size());

        //再次判断是否有领奖资格
        if (Examination.examFinished.equals(participationRecord.getExamination().getStatus())) {
            participationRecordList.add(participationRecord);//把自己加进去
            System.out.println("test examination.status passed");
            BalanceRecord balanceRecord = new BalanceRecord();
            balanceRecord.setConsumer(participationRecord.getConsumer());
            balanceRecord.setCreateDateTime(new Date());
            balanceRecord.setStatus("1");
            if (participationRecordList.size() <= questionSetting.getRank32()) {
                System.out.println("computing prize and rank");
                Consumer consumer = (Consumer) session.get(Consumer.class.getName(), participationRecord.getConsumer().getId());
                Consumer registeredConsumer = transferConsumer(session, consumer, participationRecord);
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
                    System.out.println("coupon available");
                }
                if (balanceRecord.getChangeBalance() != null) {
                    session.saveOrUpdate(balanceRecord);
                    registeredConsumer.setBalance(balanceRecord.getResultBalance());
                    participationRecord.setBalanceRecord(balanceRecord);
                    session.saveOrUpdate(registeredConsumer);
                }
                if (!consumer.getId().equals(registeredConsumer.getId())) {
                    session.delete(consumer);
                }
            }
            participationRecord.getExamination().setStatus(Examination.examRewarded);
            participationRecord.getExamination().setFinishDatetime(new Date());
            session.saveOrUpdate(participationRecord.getExamination());
            session.saveOrUpdate(participationRecord);
        }

        if (participationRecord.getBalanceRecord() == null && "1".equals(questionSetting.getCommonPrizeTrue())) {
            modelMap.put("coupon", questionSetting.getCommonPrize());
            modelMap.put("couponUrl", questionSetting.getCouponUrl());
        }
        modelMap.addAttribute("rank", participationRecordList.lastIndexOf(participationRecord) + 1);
        modelMap.addAttribute("rankList", participationRecordList);
        modelMap.addAttribute("balanceRecord", participationRecord.getBalanceRecord());
    }

    //微信用户和注册有用户绑定后，把微信用户的信息填入注册用户，并更新相关记录
    private Consumer transferConsumer(Session session, Consumer consumer, ParticipationRecord participationRecord) {
        //更新consumer
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Consumer registeredConsumer = (Consumer) session.get(Consumer.class.getName(), user.getId());
        registeredConsumer.setUnionid(consumer.getUnionid());
        if (registeredConsumer.getBalance() == null) {
            registeredConsumer.setBalance(new BigDecimal(0));
        }
        //更新wxCalledRecord
        Query query = session.createQuery("from WxCalledRecord where dataKey= 'wxqaopenid' and consumerId=:consumerId and data=:openid");
        query.setParameter("consumerId", consumer.getId());
        query.setParameter("openid", participationRecord.getWxCalledRecord().getData());
        WxCalledRecord wxCalledRecord = (WxCalledRecord) query.uniqueResult();
        wxCalledRecord.setConsumerId(registeredConsumer.getId());
        session.saveOrUpdate(wxCalledRecord.getClass().getName(), wxCalledRecord);
        //更新participationRecord
        participationRecord.setConsumer(registeredConsumer);
        participationRecord.setWxCalledRecord(wxCalledRecord);
        //更新examination
        participationRecord.getExamination().setConsumer(registeredConsumer);
        return registeredConsumer;
    }

    public String getLock(String id) {
        String idLock = lockMap.get(id);

        if (idLock == null) {
            synchronized (lockMap) {
                if (lockMap.get(id) == null) {
                    lockMap.put(id, id);
                }
                idLock = lockMap.get(id);
            }
        }
        return idLock;
    }

    @Override
    public String getOpenid(HttpServletRequest request) {
//        System.out.println("getOpenid:" + request.getParameter("openid"));
        return request.getParameter("openid") != null ? request.getParameter("openid") : (String) (request.getSession().getAttribute("openid") != null ? request.getSession().getAttribute("openid") : (CookieTool.getCookieByName(request, "openid") != null ? CookieTool.getCookieByName(request, "openid").getValue() : ""));
    }

    @Override
    public WxCalledRecord findLatestWxCalledRecordByOpenid(String openid) {
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("openid", openid);
        List wxCalledRecordList = baseManager.listObject("from WxCalledRecord where dataKey='wxqaopenid' and data=:openid order by createDatetime desc", queryMap);
        WxCalledRecord wxCalledRecord = wxCalledRecordList == null || wxCalledRecordList.size() == 0 ? new WxCalledRecord() : (WxCalledRecord) wxCalledRecordList.get(0);
        return wxCalledRecord;
    }

    @Override
    public WxCalledRecord wxLogin(Map map) {
        //保存用户
        WxCalledRecord wxCalledRecord = findLatestWxCalledRecordByOpenid((String) map.get("openid"));
        Consumer consumer;
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("MyUser:" + object);

        //efeiyi已登录
        if (object instanceof MyUser) {
            MyUser user = (MyUser) object;
            consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), user.getId());
        }
        //efeiyi未登录
        else {
            //微信未登录过
            if (wxCalledRecord.getConsumerId() == null) {
                consumer = new Consumer();
                consumer.setBalance(new BigDecimal(0));
            }
            //微信登录过
            else {
                consumer = (Consumer) baseManager.getObject(Consumer.class.getName(), wxCalledRecord.getConsumerId());
            }
        }
        if (map.get("unionid") != null && !map.get("unionid").equals(consumer.getUnionid())) {
            consumer.setUnionid((String) map.get("unionid"));
        }
        baseManager.saveOrUpdate(Consumer.class.getName(), consumer);


        wxCalledRecord.setConsumerId(consumer.getId());
        wxCalledRecord.setDataKey(WxQAConst.dataKey);
        wxCalledRecord.setData((String) map.get("openid"));
        wxCalledRecord.setAccessToken((String) map.get("refreshToken"));
        wxCalledRecord.setCreateDatetime(new Date());
        //头像暂放callback
        wxCalledRecord.setCallback((String) map.get("headimgurl"));
        //名字暂放请求来源
        wxCalledRecord.setRequestSource((String) map.get("nickname"));
        baseManager.saveOrUpdate(WxCalledRecord.class.getName(), wxCalledRecord);
        return wxCalledRecord;
    }

    public WxCalledRecord wxLogin(String openid, String nickname, String headimgurl){
        Map map = new HashMap();
        map.put("openid",openid);
        map.put("nickname",nickname);
        map.put("headimgurl",headimgurl);
        return wxLogin(map);
    }

    public List<Map<String, String>> randomSortAnswer(Examination examination){
        List<Map<String, String>> randomAnswerList = new ArrayList<>();
        for(ExaminationQuestion examinationQuestion : examination.getExaminationQuestionList()){
            randomAnswerList.add(getRandomAnswers(examinationQuestion));
        }
        return randomAnswerList;
    }

    //答案随机排个序
    private Map<String, String> getRandomAnswers(ExaminationQuestion examinationQuestion) {
        Map<Integer, String> storeAnswerMap = new HashMap<>();
        storeAnswerMap.put(0, examinationQuestion.getQuestion().getAnswerA());
        storeAnswerMap.put(1, examinationQuestion.getQuestion().getAnswerB());
        storeAnswerMap.put(2, examinationQuestion.getQuestion().getAnswerC());
        storeAnswerMap.put(3, examinationQuestion.getQuestion().getAnswerD());
        Random random = new Random();
        Map<String, String> randomAnswerMap = new LinkedHashMap<>();
        List<Map.Entry<Integer, String>> entryList = new ArrayList();
        entryList.addAll(storeAnswerMap.entrySet());
        for (int x = 4; x > 0; x--) {
            int index = random.nextInt(x);

            Map.Entry<Integer,String> entry = entryList.remove(index);
            String key = numberMap2Letter(entry.getKey());
            String value = entry.getValue();
            randomAnswerMap.put(key, value);
//            randomAnswerMap.put(numberMap2Letter(4 - x), storeAnswerMap.remove(random.nextInt(x)));
        }
        return randomAnswerMap;
    }

    private String numberMap2Letter(int num) {
        String letter;
        switch (num) {
            case 0:
                letter = "A";
                break;
            case 1:
                letter = "B";
                break;
            case 2:
                letter = "C";
                break;
            case 3:
                letter = "D";
                break;
            default:
                letter = "D";
        }
        return letter;
    }
}
