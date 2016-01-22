package com.efeiyi.wx.website.controller;

import com.efeiyi.ec.yale.question.model.Examination;
import com.efeiyi.wx.website.util.WxQAConst;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.model.WxCalledRecord;
import com.ming800.core.util.HttpUtil;
import com.ming800.core.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 * 微信答题 Controller
 */

@Controller
@RequestMapping("/wx")
public class WxQAController {

    @Autowired
    private BaseManager baseManager;

    @RequestMapping("/start.do")
    public ModelAndView start(HttpServletRequest request, ModelMap modelMap){
        String examinationId = request.getParameter("examinationId");
        if (examinationId != null && !examinationId.trim().equals("")){
            Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);
            modelMap.put("examination", exam);
        }
        return new ModelAndView("/question/activityDescription");
    }


    @RequestMapping({"/init.do"})
    @ResponseBody
    public String initWxConfig(HttpServletRequest request) throws Exception {
        String timestamp = request.getParameter("timestamp");
        String nonceStr = request.getParameter("nonceStr");
        String callUrl = request.getParameter("callUrl");
        String ticket;

        //获取当前的ticket
        String hql = "select obj from " + WxCalledRecord.class.getName() + " obj where obj.dataKey=:dataKey order by obj.createDatetime desc";
        LinkedHashMap<String, Object> param = new LinkedHashMap<>();
        param.put("dataKey", "dati_jsapi_ticket");
        List<Object> wxCallRecordList = baseManager.listObject(hql, param);

        if ((wxCallRecordList != null && wxCallRecordList.isEmpty()) || (wxCallRecordList != null && !wxCallRecordList.isEmpty() && System.currentTimeMillis() - ((WxCalledRecord) wxCallRecordList.get(0)).getCreateDatetime().getTime() >= 7000000)) {
            //首先获得accessToken
            String fetchAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WxQAConst.APPID + "&secret=" + WxQAConst.APPSECRET;
            String tokenResult = HttpUtil.getHttpResponse(fetchAccessTokenUrl, null);
            JSONObject tokenObject = JSONObject.fromObject(tokenResult);
            String accessToken = tokenObject.getString("access_token");
            //获得jsapiTickit
            String fetchJsApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
            String ticketResult = HttpUtil.getHttpResponse(fetchJsApiTicketUrl, null);
            JSONObject ticketObject = JSONObject.fromObject(ticketResult);
            ticket = ticketObject.getString("ticket");

            WxCalledRecord wxCalledRecord = new WxCalledRecord();
            wxCalledRecord.setData(ticket);
            wxCalledRecord.setDataKey("dati_jsapi_ticket");
            wxCalledRecord.setCreateDatetime(new Date());
            wxCalledRecord.setAccessToken(accessToken);
            baseManager.saveOrUpdate(WxCalledRecord.class.getName(), wxCalledRecord);
        } else {
            ticket = ((WxCalledRecord) wxCallRecordList.get(0)).getData();
        }
        //生成signature
        String signature = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + URLDecoder.decode(callUrl, "UTF-8");
        System.out.println(signature);
        signature = StringUtil.encodePassword(signature, "SHA1");
        return signature;
    }

    @RequestMapping("/shareExamination/examinationId")
    @ResponseBody
    public String shareExamination(HttpServletRequest request, @PathVariable String examinationId){
        Examination exam = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);
        if (exam != null && !exam.getStatus().equals(Examination.examStarted)) {
            exam.setStatus(Examination.examShared);//试题 1已分享
            baseManager.saveOrUpdate(exam.getClass().getName(), exam);
        }
        return "updated";
    }
}
