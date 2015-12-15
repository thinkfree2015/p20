package com.efeiyi.jh.Handler;

import com.efeiyi.ec.organization.model.User;
import com.ming800.core.base.dao.XdoDao;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.base.util.XDoUtil;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/12/14.
 * 保存企业用户 Handler
 */
public class CompanyUserHandler implements DoHandler {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private XdoDao xdoDao = (XdoDao) ApplicationContextUtil.getApplicationContext().getBean("xdoDaoSupport");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest request) throws Exception {

        User user = new User();

        String userId = request.getParameter("id");
        String type = "new";
        if (userId != null && !"".equals(userId.trim())) {
            type = "edit";
            user = (User) baseManager.getObject(User.class.getName(), userId);
        }
        Do tempDo = (Do) modelMap.get("tempDo");
        user = (User) XDoUtil.processSaveOrUpdateTempObject(tempDo, user, user.getClass(), request, type, xdoDao);

        if (type.equals("new")){
            user.setStatus("3");//企业用户状态为3
        }
        modelMap.put("object", user);
        return modelMap;
    }
}
