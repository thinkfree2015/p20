package com.efeiyi.jh.Handler;

import com.efeiyi.ec.organization.model.User;
import com.efeiyi.ec.product.model.Product;
import com.efeiyi.jh.companyGift.model.CompanyGifts;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.service.DoHandler;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/15.
 */
public class CompanyGiftHandler implements DoHandler {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private AutoSerialManager autoSerialManager = (AutoSerialManager) ApplicationContextUtil.getApplicationContext().getBean("autoSerialManager");

    @Override
    public ModelMap handle(ModelMap modelMap, HttpServletRequest request) throws Exception {

        CompanyGifts gift = new CompanyGifts();

        String giftId = request.getParameter("id");
        String type = "new";
        if (giftId != null && !"".equals(giftId.trim())) {
            type = "edit";
            gift = (CompanyGifts) baseManager.getObject(CompanyGifts.class.getName(), giftId);
        }

        gift = getRelationProperty(gift, request);
        gift = getBaseProperty(gift, request);

        if (type.equals("new")){
            gift.setStatus("1");//企业用户状态为3
            gift.setCreateDatetime(new Date());
            gift.setSerial(autoSerialManager.nextSerial("companyGift"));
        }
        modelMap.put("object", gift);
        return modelMap;
    }

    /**
     * 企业礼品卡关联对象
     * @param gift 企业礼品卡
     * @param request request
     * @return gift
     * @throws Exception
     */
    private CompanyGifts getRelationProperty(CompanyGifts gift, HttpServletRequest request) throws Exception{
        String userId = request.getParameter("user.id");
        String productId = request.getParameter("product.id");
        User user = (User) baseManager.getObject(User.class.getName(), userId);
        Product product = (Product) baseManager.getObject(Product.class.getName(), productId);
        gift.setUser(user);
        gift.setProduct(product);
        return gift;
    }

    /**
     * 企业礼品卡基本属性
     * @param gift 企业礼品卡
     * @param request request
     * @return gift
     * @throws Exception
     */
    private CompanyGifts getBaseProperty(CompanyGifts gift, HttpServletRequest request)throws Exception{
        String amount = request.getParameter("amount");
        String message = request.getParameter("message");
        gift.setMessage(message);
        gift.setAmount(Integer.parseInt(amount));
        return gift;
    }
}
