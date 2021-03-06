package com.efeiyi.pal.system.product.controller;

import com.efeiyi.pal.product.model.Product;
import com.efeiyi.pal.product.model.ProductPropertyValue;
import com.efeiyi.pal.product.model.ProductSeriesPropertyName;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/8/4.
 * 商品属性 Controller
 */

@Controller
@RequestMapping("/productPropertyValue")
public class ProductPropertyValueController {

    @Autowired
    private BaseManager baseManager;

    @RequestMapping("/saveProductPropertyValueList.do")
    public ModelAndView saveProductSeriesPropertyNameList(HttpServletRequest request,HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String productId = request.getParameter("product.id");
        if (productId == null || "".equals(productId.trim())) {
            throw new Exception("productId不能为空");
        }

        Product product = (Product) baseManager.getObject(Product.class.getName(), productId);
        int count = Integer.parseInt(request.getParameter("count"));
        String resultPage = saveProductPropertyValueList(request, count, product, productId);

        return new ModelAndView(resultPage);
    }

    /**
     * 获取listForm页面商品系列属性并保存在商品属性值
     * @param request 获取页面图片
     * @param flag    统计属性个数
     * @param product 商品
     * @param productId 商品ID
     * @return 跳转链接
     * @throws Exception
     */
    private String saveProductPropertyValueList(HttpServletRequest request, int flag, Product product, String productId) throws Exception{

        if (product == null) {
            throw new Exception("Id为" + productId + "的商品不存在!");
        }

        for (int i=1; i<=flag; i++){
            ProductSeriesPropertyName propertyName = null;
            String propertyNameId = request.getParameter("propertyNameId"+i);
            if (propertyNameId != null && !propertyNameId.equals("")) {
                propertyName = (ProductSeriesPropertyName) baseManager.getObject(ProductSeriesPropertyName.class.getName(), propertyNameId);
            }

            ProductPropertyValue propertyValue = new ProductPropertyValue();
            String propertyValueId = request.getParameter("propertyValueId"+i);
            if (propertyValueId != null && !propertyValueId.equals("")) {
                propertyValue = (ProductPropertyValue) baseManager.getObject(ProductPropertyValue.class.getName(), propertyValueId);
            }

            String value = request.getParameter("value"+i);

            if (value == null || "".equals(value.trim())){
                continue;
            }
            String newValue = new String (value.getBytes("utf-8"), "utf-8");
            propertyValue.setProduct(product);
            propertyValue.setProductSeriesPropertyName(propertyName);
            propertyValue.setValue(newValue);
            propertyValue.setStatus("1");

            baseManager.saveOrUpdate(propertyValue.getClass().getName(), propertyValue);
            propertyValue.getValue();
        }

        return "redirect:/basic/xm.do?qm=viewProduct&product=product&id="+ product.getId();
    }
}
