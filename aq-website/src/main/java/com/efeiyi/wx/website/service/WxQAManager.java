package com.efeiyi.wx.website.service;

import com.efeiyi.ec.organization.model.Consumer;
import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.ec.yale.question.model.ExaminationEdition;
import com.efeiyi.ec.yale.question.model.ExaminationQuestion;
import com.efeiyi.ec.yale.question.model.ParticipationRecord;
import com.ming800.core.p.model.WxCalledRecord;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30.
 */
public interface WxQAManager {


    void saveOpenid2Cache(HttpServletRequest request, HttpServletResponse response, WxCalledRecord wxCalledRecord) throws Exception;

    Examination generateNewExamination(Consumer consumer, ExaminationEdition examinationEdition) throws Exception;

    void saveAnswer(Examination examination, ModelMap modelMap) throws Exception;

    List<ExaminationQuestion> saveHelpAnswer(Examination examination, ModelMap modelMap) throws Exception;

    Consumer findConsumerByOpenid(String openid) throws Exception;

    Examination findExaminationByConsumer(Consumer consumer,ExaminationEdition examinationEdition) throws Exception;

    ParticipationRecord checkIfParticipated(Consumer consumer,Examination examination) throws Exception;

    void reward(ParticipationRecord participationRecord, ModelMap modelMap) throws Exception;

    String getLock(String id);

    String getOpenid(HttpServletRequest request) ;

    WxCalledRecord findLatestWxCalledRecordByOpenid(String openid);

    WxCalledRecord wxLogin(Map map);

    WxCalledRecord wxLogin(String openid, String nickname, String headimgurl);

    List<Map<String, String>> randomSortAnswer(Examination examination);
}
