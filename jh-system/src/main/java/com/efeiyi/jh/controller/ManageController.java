package com.efeiyi.jh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 导航切换
 */
@Controller
public class ManageController {

    @RequestMapping({"main.do"})
    public String main(){
        return "/main";
    }

}
