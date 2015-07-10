package com.efeiyi.ec.system.organization.controller;

import com.efeiyi.ec.system.organization.util.AuthorizationUtil;
import com.efeiyi.ec.organization.model.MyUser;
import com.ming800.core.base.controller.BaseController;
import com.ming800.core.p.PConst;
import com.ming800.core.p.model.Jmenu;
import com.ming800.core.p.model.Jnode;
import com.ming800.core.p.service.GlobalManager;
import com.ming800.core.p.service.JmenuManager;
import com.ming800.core.p.service.impl.JmenuManagerImpl;
import com.ming800.core.util.DateUtil;
import org.dom4j.Document;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: han
 * Date: 12-7-30
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MainController extends BaseController {

    @Autowired
    private JmenuManager jmenuManager;
    @Autowired
    private GlobalManager globalManager;

    @RequestMapping("/index.do")
    public ModelAndView index(HttpServletRequest request) throws Exception {
        ModelMap modelMap = new ModelMap();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            modelMap.put("exist", false);
            modelMap.put("message", "请按F12键，在缓存中选择启用浏览器cookies");
        } else {
            for (Cookie c : cookies) {
                modelMap.put(c.getName(), c.getValue());
            }
            String name = request.getParameter("name"); //地址栏上传递的机构名称 例：http://localhost:8080/index.do?name=t1 ；j_yey是cookie中保存的机构名
            if (name != null) {
                modelMap.put("j_yey", name);
                modelMap.put("name", name);

                modelMap.put("j_username0", name);
            }
//            String j_yey = (String) modelMap.get("j_yey");
            /*Tenant branch = branchManager.checkBranchName(j_yey);
            if (branch != null) {
                modelMap.put("exist", true);
                if (branch.getTheStatus() > 3) {
                    modelMap.put("message", "登录异常009");
                } else {*/
            modelMap.put("exist", true);
            if (request.getParameter("error") != null && request.getParameter("error").equals("true")) {
                modelMap.put("message", "用户名密码不匹配");
            }
//                }

            /*} else {
                modelMap.put("exist", false);
                modelMap.put("message", "该机构不存在,请核实学校名称");
            }*/
        }

        Document globalDocument = globalManager.load();
        List<Node> nodeList = globalDocument.selectNodes("/global");
        String index = nodeList.get(0).selectSingleNode("@index").getText();

        return new ModelAndView(index, modelMap);
    }

    /**
     * 登录后进入的主页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/main.do")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        String menuId = request.getParameter("menuId");
        ModelMap modelMap = new ModelMap();
        MyUser myUser = AuthorizationUtil.getMyUser();
        modelMap.put("myUser", myUser);
        modelMap.put("currentCalendar", DateUtil.getCurrentCalendarString());
        if (menuId == null) {
            menuId = "organmgmt";
        }
        Jmenu jmenu = JmenuManagerImpl.menuHashMap.get("commonMenu");
        modelMap.put("jmenu", jmenu);
        for (Jnode jnode : jmenu.getChildren()) {
            if (menuId.equals(jnode.getId())) {
                modelMap.put("jnode", jnode);
                break;
            }
        }
        request.setAttribute("menuId", menuId);

        return new ModelAndView("/main", modelMap);
    }


    /**
     * 主页iframe中默认显示的页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/home.do")
    public String home() throws Exception {
        return "home";
    }

    /**
     * 主页菜单 （包括 顶部菜单 和左侧的菜单树）
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/getMenu")
    @ResponseBody
    public String getMenu(HttpServletRequest request) {
        String jMenuJson = "";
        String menuId = request.getParameter("menuId");
//        if (menuId != null && !menuId.equals("")) {
        //如果menuId 等于 head 说明是加载头部导航栏  否则是加载左侧导航栏
            /*if (menuId.equals("head")) {
                menuId = "training";
                jMenuJson = jmenuManager.getJmenuJson(menuId, PConst.JMENU_TYPE_HEAD);

            } else {
                jMenuJson = jmenuManager.getJmenuJson(menuId, PConst.JMENU_TYPE_LEFT);
            }*/
        menuId = "organmgmt";
        jMenuJson = jmenuManager.getJmenuJson(menuId, PConst.JMENU_TYPE_HEAD);
//        }
        return jMenuJson;
    }
}
