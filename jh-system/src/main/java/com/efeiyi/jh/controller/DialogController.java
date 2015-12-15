package com.efeiyi.jh.controller;

import com.efeiyi.jh.service.companyGift.ModalServiceManager;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.model.XQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/7/29.
 *
 */
@RestController
public class DialogController {

    @Autowired
    private BaseManager baseManager;
    @Autowired
    @Qualifier("modalServiceManagerImpl")
    private ModalServiceManager modalServiceManager;

    /**
     * 获取所有商品
     * @param model model
     * @param request request
     * @return list
     * @throws Exception
     */
    @RequestMapping({"/gift/product/list/json"})
    public List listProduct(Model model, HttpServletRequest request) throws Exception{
        XQuery xQuery = new XQuery("listProduct_default", request);
        xQuery.addRequestParamToModel(model, request);
        return baseManager.listObject(xQuery);
    }

    /**
     * 根据商品名称获取商品
     * @param request request
     * @return set
     * @throws Exception
     */
    @RequestMapping({"/gift/productLikesName/list/json"})
    public Set<Object> listProductLikesName(HttpServletRequest request) throws Exception{
        String name = request.getParameter("name");
        return modalServiceManager.getListLikesName(name.trim(), "Product", "1");
    }

    /**
     * 获取所有用户
     * @param request request
     * @return list
     * @throws Exception
     */
    @RequestMapping({"/gift/user/list/json"})
    public List listUser(HttpServletRequest request) throws Exception{
        XQuery xQuery = new XQuery("listUser_default", request);
        return baseManager.listObject(xQuery);
    }

    /**
     * 根据用户名称获取企业用户
     * @param request request
     * @return set
     * @throws Exception
     */
    @RequestMapping({"/gift/userLikesName/list/json"})
    public Set<Object> listUserLikesName(HttpServletRequest request) throws Exception{
        String username = request.getParameter("name");
        return modalServiceManager.getListLikesName(username.trim(), "User", "3");
    }

}
