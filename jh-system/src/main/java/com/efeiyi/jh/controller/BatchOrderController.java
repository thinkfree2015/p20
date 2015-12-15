package com.efeiyi.jh.controller;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.ec.purchase.model.PurchaseOrderProduct;
import com.efeiyi.jh.util.Reactor;
import com.ming800.core.base.service.XdoManager;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.model.DoQuery;
import com.ming800.core.does.service.DoManager;
import com.ming800.core.util.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2015/12/11.
 */
@Controller
@RequestMapping("/batch")
public class BatchOrderController {

    @Autowired
    private DoManager doManager;
    @Autowired
    private XdoManager xdoManager;

    @RequestMapping("/submit2Deppon.do")
    public ModelAndView submit2Deppon(HttpServletRequest request, ModelMap modelMap) throws Exception {

        String qm = request.getParameter("qm");
        request.setAttribute("qm", qm);
        String conditions = request.getParameter("conditions");

        Do tempDo = doManager.getDoByQueryModel(qm.split("_")[0]);
        DoQuery tempDoQuery = tempDo.getDoQueryByName(qm.split("_")[1]);
        List<PurchaseOrderProduct> list = (List<PurchaseOrderProduct>) xdoManager.list(tempDo, tempDoQuery, conditions);

        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (PurchaseOrderProduct purchaseOrderProduct : list) {
            new Thread(new Reactor(purchaseOrderProduct.getPurchaseOrder(), PurchaseOrder.ORDER_STATUS_POSTED, countDownLatch)).start();
            if(--count == 0){
                countDownLatch.await();
                count = 100;
            }
        }


        modelMap.put("objectList", list);
        String resultPage = request.getParameter("result");
        return new ModelAndView(resultPage, modelMap);
    }

    public static void main(String[] args) throws IOException {
        Map jsonMap = new HashMap<>();
        jsonMap.put("logisticCompanyID", "DEPPON");
        jsonMap.put("logisticID", "EHUAY1234567890");
        jsonMap.put("orderSource", "EWBHUAYUN");
        jsonMap.put("serviceType", "2");
        jsonMap.put("customerCode", "F2015120966058945");
        jsonMap.put("customerID", "EWBHUAYUN");
        jsonMap.put("sender:companyName", "efeiyi");
        jsonMap.put("sender:name", "efeiyi");
        jsonMap.put("sender:phone", "01012306");
        jsonMap.put("sender:province", "beijing");
        jsonMap.put("sender:city", "beijing");
        jsonMap.put("sender:address", "chaoyang");
        jsonMap.put("receiver:name", "zxxlxx");
        jsonMap.put("receiver:mobile", "13333333333");
        jsonMap.put("receiver:province", "beijing");
        jsonMap.put("receiver:city", "beijing");
        jsonMap.put("receiver:address", "haidian");
        jsonMap.put("gmtCommit", "2015-12-14 11:44:19");
        jsonMap.put("cargoName", "BigPrize");
        jsonMap.put("special", "[1]");
        jsonMap.put("payType", "2");
        jsonMap.put("transportType", "EPEP");
        jsonMap.put("deliveryType", "3");
        jsonMap.put("backSignBill", "4");

        String jsonString = JsonUtil.getJsonString(jsonMap);
        System.out.println(jsonString);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://58.40.17.67/dop/order/traceOrder.action");
        System.out.println("http://58.40.17.67/dop/order/traceOrder.action");
        HttpResponse response = httpClient.execute(httppost);
        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));

        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
