package com.efeiyi.jh.controller;

import com.efeiyi.jh.model.entity.VirtualPlan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by Administrator on 2015/11/19.
 */
@Controller
@RequestMapping("/plan")
public class TimerController {


    @RequestMapping("/makeVirtualMember.do")
    public ModelAndView makeVirtualMemeber(VirtualPlan plan ) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


        return new ModelAndView("/main");
    }

    @RequestMapping("/makeVirtualOrder.do")
    public ModelAndView makeVirtualOrder(VirtualPlan plan ) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


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
