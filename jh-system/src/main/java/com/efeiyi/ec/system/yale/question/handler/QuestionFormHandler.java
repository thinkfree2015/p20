package com.efeiyi.ec.system.yale.question.handler;

import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/12/22.
 * 题目表单保存 Handler
 */
public class QuestionFormHandler implements DoHandler {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private AutoSerialManager autoSerialManager = (AutoSerialManager) ApplicationContextUtil.getApplicationContext().getBean("autoSerialManagerImpl");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        Product product = new Product();

        String productId = request.getParameter("id");
        String type = "new";
        if (productId != null && !productId.trim().equals("")) {
            type = "edit";
            product = (Product) baseManager.getObject(Product.class.getName(), productId);
        }
        product = setProductBaseProperty(product, request);
        product = getRelationAttributeObject(product, request, type);
        product = upLoadLogo(multipartRequest, product);

        baseManager.saveOrUpdate(product.getClass().getName(), product);

        modelMap.put("object", object);
        return modelMap;
    }

    private Product setProductBaseProperty(Product product, HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String masterName = request.getParameter("masterName");
        String shoppingUrl = request.getParameter("shoppingUrl");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd" );
        Date date = sdf.parse(request.getParameter("madeYear"));

        product.setName(name);
        product.setMasterName(masterName);
        product.setShoppingUrl(shoppingUrl);
        product.setMadeYear(date);

        return product;
    }

    private Product upLoadLogo(MultipartRequest multipartRequest, Product product) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String identify = sdf.format(new Date());
        String url = "product/" + identify + ".jpg";

        if (!multipartRequest.getFile("logo").getOriginalFilename().equals("")) {
            aliOssUploadManager.uploadFile(multipartRequest.getFile("logo"), "315pal", url);
            product.setLogo(url);
        }

        return product;
    }
}
