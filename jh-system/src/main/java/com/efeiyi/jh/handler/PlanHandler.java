package com.efeiyi.jh.handler;

import com.efeiyi.jh.model.VirtualPlan;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.ApplicationContextUtil;

/**
 * Created by Administrator on 2015/11/20.
 */
public interface PlanHandler {

    BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");

    void execute(VirtualPlan plan);
}
