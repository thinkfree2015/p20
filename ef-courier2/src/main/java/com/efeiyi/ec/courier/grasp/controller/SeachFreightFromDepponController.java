package com.efeiyi.ec.courier.grasp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efeiyi.ec.courier.model.Product;
import com.efeiyi.ec.courier.organization.util.ContextUtils;
import com.ming800.core.base.service.XdoManager;
import com.ming800.core.does.service.DoManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2015/12/15.
 *
 */
@Controller
@RequestMapping("/freight")
public class SeachFreightFromDepponController {
    @Autowired
    private DoManager doManager;
    @Autowired
    private XdoManager xdoManager;

    @RequestMapping(value ="/seachPrice.do", method = RequestMethod.POST)
    @ResponseBody
    public String submit2Deppon(HttpServletRequest request) throws Exception {
        String productCode = ((Product)ContextUtils.getBean("product")).getProductCode();
        String cost="";

        JSONObject jsonObj = (JSONObject)JSONObject.parse(request.getParameter("json").toString());
        String jsonString = jsonObj.toJSONString();
        String weight = request.getParameter("weight");
        if(jsonString ==null && "".equals(jsonString)){
            return "the args is necessary";
        }
        String apiKey = "deppontest";
        long timestamp = System.currentTimeMillis();
        String digest = jsonString + apiKey + timestamp;
        System.out.println("param+apikey+timestamp:  " + digest);
        digest = md5(digest);
        System.out.println("MD5(param+apikey+timestamp):  " + digest);
        digest = new String(Base64.encodeBase64(digest.getBytes()));
        System.out.println("base64(MD5(param+apikey+timestamp)):  " + digest);

        HttpClient httpClient = new DefaultHttpClient();
        String url = "http://58.40.17.67/dop/order/queryPrice.action";
        HttpPost httppost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity("companyCode=EWBHUAYUNN&params=" + jsonString + "&digest=" + digest+"&timestamp="+timestamp,"utf-8");
        stringEntity.setContentType("application/x-www-form-urlencoded");
        httppost.setEntity(stringEntity);
        System.out.println("url:  " + url);
        byte [] b = new byte[(int)stringEntity.getContentLength()];
        stringEntity.getContent().read(b);
        System.out.println("报文:" + new String(b,"utf-8"));

        HttpResponse response = httpClient.execute(httppost);
        HttpEntity entity = response.getEntity();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        System.out.println(stringBuilder.toString());
        JSONObject jasonObject = (JSONObject) JSONObject.parse(stringBuilder.toString());
        JSONObject jasonObject2 = jasonObject.getJSONObject("responseParam");
        JSONArray jsonArray =  jasonObject2.getJSONArray("priceInfo");

        if (!jsonArray.isEmpty()){
            for (int i=0;i<jsonArray.size();i++){
                if (productCode.equals(jsonArray.getJSONObject(i).getString("productCode"))){
                    cost = calculateFreight(jsonArray.getJSONObject(i),weight);
                    break;

                }

            }
        }

        return cost;
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

    private String calculateFreight(JSONObject jsonArray,String weight)throws Exception{
        String freight="";
        Double wt = new Double(weight);
        BigDecimal groundPrice = new BigDecimal(jsonArray.getString("groundPrice"));
        Double upperGround = jsonArray.getDouble("upperGround");
        Double lowerGround = jsonArray.getDouble("lowerGround");

        BigDecimal rateOfStage1 = new BigDecimal(jsonArray.getInteger("rateOfStage1"));
        Double lowerOfStage1 = jsonArray.getDouble("lowerOfStage1");
        Double upperOfStage1 = jsonArray.getDouble("upperOfStage1");

        BigDecimal rateOfStage2 = new BigDecimal(jsonArray.getInteger("rateOfStage2"));
        Double lowerOfStage2 = jsonArray.getDouble("lowerOfStage2");
        Double upperOfStage2 = jsonArray.getDouble("upperOfStage2");

        if (lowerGround<=wt && wt<=upperGround){
            freight = groundPrice.toString();
        }else if (lowerOfStage1<wt && wt<=upperOfStage1){
            freight = groundPrice.add(rateOfStage1.multiply(new BigDecimal(wt-lowerOfStage1))).toString();
        }else if(lowerOfStage2<wt && wt<=upperOfStage2){
            freight = groundPrice.add(
                    rateOfStage1.multiply(new BigDecimal(upperOfStage1-lowerOfStage1))
                            .add(rateOfStage2.multiply(new BigDecimal(wt - lowerOfStage2)))).toString();
        }

        return freight;
    }

    public static void main(String[] args) throws Exception {

        String jsonString = "{\"data\":{\"destCity\": \"北京市\",\"destDistrict\": \"\"," +
                "\"destProvince\": \"北京\",\"logisticCompanyID\": \"DEPPON\"," +
                "\"originalCity\": \"上海市\",\"originalDistrict\": \"\",\"originalProvince\": \"上海\"},\"weight\"}";

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
        String url = "http://58.40.17.67/dop/order/queryPrice.action";
        HttpPost httppost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity("companyCode=EWBHUAYUNN&params=" + jsonString + "&digest=" + digest+"&timestamp="+timestamp,"utf-8");
        stringEntity.setContentType("application/x-www-form-urlencoded");
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

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        JSONObject jasonObject = (JSONObject) JSONObject.parse(stringBuilder.toString());
        JSONObject jasonObject2 = jasonObject.getJSONObject("responseParam");
        JSONArray jsonArray =  jasonObject2.getJSONArray("priceInfo");
        Object[]  objects= jsonArray.toArray();
        if (!jsonArray.isEmpty()){
            for (Object obj : objects){
                System.out.println(obj.toString());

            }
        }
    }
}
