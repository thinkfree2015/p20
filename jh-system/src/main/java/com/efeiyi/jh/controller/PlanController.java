package com.efeiyi.jh.controller;

import com.efeiyi.jh.model.PlanConst;
import com.efeiyi.jh.model.entity.VirtualPlan;
import com.efeiyi.jh.model.task.CoreTaskScheduler;
import com.efeiyi.jh.model.timer.SubTimer;
import com.efeiyi.jh.model.timer.SuperTimer;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    BaseManager baseManager;

    @RequestMapping("/saveOrEditVirtualUser.do")
    public ModelAndView saveOrEditVirtualUser(VirtualPlan plan) {


        return new ModelAndView("/main");
    }

    @RequestMapping("/saveOrEditPlan.do")
    public ModelAndView saveOrEditPlan(VirtualPlan plan) {


        return new ModelAndView("/main");
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
        SubTimer subTimer = SuperTimer.getInstance().getSubTimerTaskMap().get(virtualPlan);
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

//    @RequestMapping("/start.do")
//    public ModelAndView startPlan() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//
//       Timer timer = new Timer();
//
//        timer.schedule(new BaseTimerTask(),0);
//
//        return new ModelAndView("/main");
//    }

}
