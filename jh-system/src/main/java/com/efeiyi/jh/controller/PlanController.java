package com.efeiyi.jh.controller;

import com.efeiyi.jh.model.PlanConst;
import com.efeiyi.jh.model.task.CoreTaskScheduler;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;
import com.efeiyi.jh.plan.model.VirtualPlan;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.service.AutoSerialManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Created by Administrator on 2015/11/19.
 */
@Controller
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private BaseManager baseManager;
    @Autowired
    @Qualifier("autoSerialManager")
    private AutoSerialManager autoSerialManager;

    @RequestMapping("/saveOrEditVirtualUser.do")
    public ModelAndView saveOrEditVirtualUser(VirtualPlan plan) {


        return new ModelAndView("/main");
    }

    @RequestMapping("/saveOrEditPlan.do")
    public ModelAndView saveOrEditPlan(VirtualPlan plan) throws Exception{
        String virtualPlanId = plan.getId();
        if (virtualPlanId.isEmpty() || virtualPlanId.trim().equals("")){
            String serial = autoSerialManager.nextSerial("virtualPlan");
            plan.setSerial(serial);
            plan.setStatus("1");
            plan.setCreateDatetime(new Date());
        }

        baseManager.saveOrUpdate(plan.getClass().getName(), plan);
        String resultPage = "redirect:/basic/xm.do?qm=plistVirtualPlan_default";
        return new ModelAndView(resultPage);
    }

    @RequestMapping("/startPlan.do")
    public ModelAndView startPlan(VirtualPlan virtualPlan, ModelMap modelMap,HttpServletRequest request) {

        List<VirtualPlan> virtualPlanList = new ArrayList<>();
        virtualPlan = (VirtualPlan)baseManager.getObject(VirtualPlan.class.getName(), virtualPlan.getId());
        if(!PlanConst.planStatusStarted.equals(virtualPlan.getStatus())) {
            virtualPlanList.add(virtualPlan);
            CoreTaskScheduler.getInstance().execute(virtualPlanList);
        }
        modelMap.addAttribute(virtualPlan);
        return new ModelAndView("redirect:" + request.getParameter("resultPage"), modelMap);
    }

    @RequestMapping("/pausePlan.do")
    public ModelAndView pausePlan(VirtualPlan virtualPlan, ModelMap modelMap,HttpServletRequest request) {

        virtualPlan = (VirtualPlan)baseManager.getObject(VirtualPlan.class.getName(), virtualPlan.getId());
        SubTimer subTimer = SuperTimer.getInstance().getSubTimerMap().get(virtualPlan);
        if(subTimer != null){
            subTimer.cancel();
        }
        modelMap.addAttribute(virtualPlan);
        return new ModelAndView("redirect:" + request.getParameter("resultPage"),modelMap);
    }

    @RequestMapping("/deletePlan.do")
    public ModelAndView deletePlan(VirtualPlan plan) {


        return new ModelAndView("/main");
    }

    @RequestMapping("/logger.do")
    public ModelAndView startPlan() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Logger logger = Logger.getLogger(PlanController.class);

        logger.error("----------------------------------");
        return new ModelAndView("/main");
    }

}
