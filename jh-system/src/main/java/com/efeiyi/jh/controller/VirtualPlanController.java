package com.efeiyi.jh.controller;

import com.efeiyi.ec.purchase.model.PurchaseOrderProduct;
import com.efeiyi.jh.plan.model.VirtualOrderPlan;
import com.efeiyi.jh.plan.model.VirtualPlan;
import com.efeiyi.jh.plan.model.VirtualUser;
import com.efeiyi.jh.plan.model.VirtualUserPlan;
import com.efeiyi.jh.service.virtualPlan.VirtualPlanManagerService;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.taglib.PageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 * 虚拟计划 Controller
 */

@Controller
public class VirtualPlanController {

    @Autowired
    private BaseManager baseManager;

    @Autowired
    @Qualifier("virtualPlanManagerImpl")
    private VirtualPlanManagerService vpmService;

    @RequestMapping("/virtualPlan/getTypeObjectList.do")
    public ModelAndView getTypeObjectList(ModelMap modelMap, HttpServletRequest request) throws Exception {

        //虚拟计划Id
        String planId = request.getParameter("id");
        if (planId.isEmpty() || planId.trim().equals("")){
            throw new Exception("获取计划完成列表失败:VirtualPlanId为空!");
        }
        modelMap.put("planId", planId);
        //虚拟计划对象类型
        String type = request.getParameter("type");
        modelMap.put("objectType", type);
        //虚拟计划完成列表分页信息
        PageEntity pageEntity = new PageEntity();
        String pageIndex = request.getParameter("pageEntity.index");
        String pageSize = request.getParameter("pageEntity.size");
        if (pageIndex != null) {
            pageEntity.setIndex(Integer.parseInt(pageIndex));
            pageEntity.setSize(Integer.parseInt(pageSize));
        }
        modelMap.put("pageEntity", pageEntity);
        //虚拟计划对象--订单order
        if (!type.isEmpty() && type.trim().equals(VirtualPlan.PLAN_TYPE_ORDER)){
            return virtualOrderList(modelMap, request);
        }
        //虚拟计划对象--用户user
        if (!type.isEmpty() && type.trim().equals(VirtualPlan.PLAN_TYPE_USER)){
            return virtualUserList(modelMap, request);
        }

        return new ModelAndView("redirect:/basic/xm.do?qm=plistVirtualPlan_default");
    }

    private ModelAndView virtualOrderList(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String planId = (String) modelMap.get("planId");
        VirtualOrderPlan virtualOrderPlan = (VirtualOrderPlan)baseManager.getObject(VirtualOrderPlan.class.getName(), planId);
        PageEntity pageEntity = (PageEntity) modelMap.get("pageEntity");
        List<PurchaseOrderProduct> popList = vpmService.getOrderProductList(virtualOrderPlan, pageEntity);
        pageEntity.setCount(virtualOrderPlan.getVirtualPurchaseOrderList().size());
        modelMap.put("popList", popList);

        return new ModelAndView("/virtualPlan/virtualPurchaseOrderPList");
    }

    private ModelAndView virtualUserList(ModelMap modelMap, HttpServletRequest request) throws Exception{
        String planId = (String) modelMap.get("planId");
        VirtualUserPlan virtualUserPlan = (VirtualUserPlan)baseManager.getObject(VirtualUserPlan.class.getName(), planId);
        PageEntity pageEntity = (PageEntity) modelMap.get("pageEntity");
        List<VirtualUser> vUserList = vpmService.getVirtualUserList(virtualUserPlan, pageEntity);
        pageEntity.setCount(virtualUserPlan.getVirtualUserList().size());
        modelMap.put("vUserList", vUserList);

        return new ModelAndView("/virtualPlan/virtualUserPList");
    }

}
