package com.efeiyi.wx.website.service.impl;

import com.efeiyi.ec.yale.question.model.ExaminationEdition;
import com.efeiyi.wx.website.service.ExaminationEditionHolder;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/25.
 *
 */
@Service
public class ExaminationEditionHolderImpl implements ExaminationEditionHolder {

    private LinkedList<ExaminationEdition> examinationEditionList = new LinkedList<>();
    @Autowired
    private BaseManager baseManager ;

    private Date retrieveExaminationEditionTimeInterval = new Date();

    public List<ExaminationEdition> getExaminationEditionList() {
        if (examinationEditionList.isEmpty()) {
            synchronized (this) {
                if (examinationEditionList.isEmpty()) {
                    examinationEditionList.addAll(baseManager.listObject("from ExaminationEdition order by createDatetime desc"));
                }
            }
        }
        Date newDate = new Date();
        if(newDate.getTime() - retrieveExaminationEditionTimeInterval.getTime() > 60000){
            synchronized (this) {
                if (newDate.getTime() - retrieveExaminationEditionTimeInterval.getTime() > 60000) {
                    retrieveExaminationEditionTimeInterval = newDate;
                    examinationEditionList.clear();
                    examinationEditionList.addAll(baseManager.listObject("from ExaminationEdition order by createDatetime desc"));
                }
            }
        }
        return examinationEditionList;
    }
}
