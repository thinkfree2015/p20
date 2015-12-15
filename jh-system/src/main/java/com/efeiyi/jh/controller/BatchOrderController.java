package com.efeiyi.jh.controller;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.ec.purchase.model.PurchaseOrderProduct;
import com.efeiyi.jh.util.Reactor;
import com.ming800.core.base.service.XdoManager;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.model.DoQuery;
import com.ming800.core.does.service.DoManager;
import com.ming800.core.util.JsonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
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

    public static void main(String[] args) throws Exception {
        Map jsonMap = new HashMap<>();
        jsonMap.put("logisticCompanyID", "DEPPON");
        jsonMap.put("logisticID", "EWHY123");
        jsonMap.put("orderSource", "EWBHUAYUNN");
        jsonMap.put("serviceType", "2");
        jsonMap.put("customerCode", "F2015120966058945");
        jsonMap.put("customerID", "EWBHUAYUNN");
        jsonMap.put("gmtCommit", "2015-12-15 10:00:19");
        jsonMap.put("cargoName", "BigPrize");
        jsonMap.put("special", "[1]");
        jsonMap.put("payType", "2");
        jsonMap.put("transportType", "EPEP");
        jsonMap.put("deliveryType", "3");
        jsonMap.put("backSignBill", "4");
        Map senderMap = new HashMap();
        senderMap.put("companyName", "efeiyi");
        senderMap.put("name", "efeiyi");
        senderMap.put("phone", "01012306");
        senderMap.put("province", "北京市");
        senderMap.put("city", "北京市");
        senderMap.put("address", "北京市朝阳区酒仙桥街道");
        senderMap.put("country","酒仙桥街道");
        senderMap.put("town","朝阳区");
        jsonMap.put("sender",senderMap);
        Map receiverMap = new HashMap();
        receiverMap.put("name", "zxxlxx");
        receiverMap.put("mobile", "13333333333");
        receiverMap.put("province", "北京市");
        receiverMap.put("city", "北京市");
        receiverMap.put("address", "北京市海淀区西北旺镇");
        receiverMap.put("country","西北旺镇");
        receiverMap.put("town","海淀区");
        jsonMap.put("receiver",receiverMap);
        String jsonString = JsonUtil.getJsonString(jsonMap);
        System.out.println("明文：" + jsonString);

        String apiKey = "deppontest";
        long timestamp = System.currentTimeMillis();
        String digest = jsonString + apiKey + timestamp;
        System.out.println("param+apikey+timestamp:  " + digest);
        digest = md5(digest);
        System.out.println("MD5(param+apikey+timestamp):  " + digest);
        digest = new String(Base64.encodeBase64(digest.getBytes()));
        System.out.println("base64(MD5(param+apikey+timestamp)):  " + digest);

        HttpClient httpClient = new DefaultHttpClient();
        String url = "http://58.40.17.67/dop/order/ewaybillNewSyncSieveOrder.action";
        HttpPost httppost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity("companyCode=EWBHUAYUNN&params=" + jsonString + "&digest=" + digest,"utf-8");
        httppost.setEntity(stringEntity);
        System.out.println("url:  " + url);
        byte [] b = new byte[(int)stringEntity.getContentLength()];
        stringEntity.getContent().read(b);
        System.out.println("报文:" + new String(b,"utf-8"));

        HttpResponse response = httpClient.execute(httppost);
        HttpEntity entity = response.getEntity();

//        HttpGet httpGet = new HttpGet("http://58.40.17.67/dop/order/ewaybillNewSyncSieveOrder.action" + "?companyCode=EWBHUAYUNN&params=" + jsonString + "&digest=" + digest);
//        HttpResponse response = httpClient.execute(httpGet);
//        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));

        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static String md5(String s) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte [] md5Bytes = md5.digest(s.getBytes("utf-8"));
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return  hexValue.toString();
    }


}
