package com.efeiyi.ec.system.yale.question.service.impl;

import com.efeiyi.ec.yale.question.model.ExaminationEdition;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.ApplicationContextUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/25.
 */
public class ExaminationEditionHolderImpl implements ExaminationEditionHolder{

    private LinkedList<ExaminationEdition> examinationEditionList;
    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");

    public List<ExaminationEdition> getExaminationEditionList() {
        if (examinationEditionList == null) {
            synchronized (this) {
                if (examinationEditionList == null) {
                    examinationEditionList.addAll(baseManager.listObject("from ExaminationEdition order by createDatetime desc"));
                }
            }
        }
        return examinationEditionList;
    }
}
