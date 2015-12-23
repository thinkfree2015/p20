package com.efeiyi.ec.system.yale.question.handler;

import com.efeiyi.ec.yale.question.model.Examination;
import com.ming800.core.base.dao.XdoDao;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.base.util.XDoUtil;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/23.
 * 试卷新建更新 Handler
 */

public class ExaminationHandler implements DoHandler {

    private XdoDao xdoDao = (XdoDao) ApplicationContextUtil.getApplicationContext().getBean("xdoDaoSupport");
    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private AutoSerialManager autoSerialManager = (AutoSerialManager) ApplicationContextUtil.getApplicationContext().getBean("autoSerialManagerImpl");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest request) throws Exception {
        Examination examination = new Examination();

        String examinationId = request.getParameter("id");
        String type = "new";
        if (examinationId != null && !examinationId.trim().equals("")) {
            type = "edit";
            examination = (Examination) baseManager.getObject(Examination.class.getName(), examinationId);
        }
        Do tempDo = (Do) modelMap.get("tempDo");
        examination = (Examination) XDoUtil.processSaveOrUpdateTempObject(tempDo, examination, Examination.class, request, type, xdoDao);

        if ("new".equals(type)){
            examination.setStatus("1");
            examination.setSerial(autoSerialManager.nextSerial("examination"));
            examination.setCreateDatetime(new Date());
        }

        modelMap.put("object", examination);
        return modelMap;
    }
}
