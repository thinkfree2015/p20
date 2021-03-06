package com.efeiyi.ec.courier.grasp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.efeiyi.ec.courier.grasp.util.FreightConstant;
import com.efeiyi.ec.courier.model.Product;
import com.efeiyi.ec.courier.organization.util.ContextUtils;
import com.ming800.core.util.DESEncryptUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URLEncoder;
import java.util.Map.Entry;
/**
 * Created by Administrator on 2015/12/15.
 *
 */
@Controller
@RequestMapping("/freight")
public class SeachFreightFromDepponController {

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


    @RequestMapping(value ="/getAddress.do")
    @ResponseBody
    public Map getAddressFromIp(HttpServletRequest request) throws Exception {
        Map map = new LinkedHashMap<String, String>();
        String URI = FreightConstant.SERVER_LOCATION_API_URI;
        String ak = FreightConstant.SERVER_AK;
        //String sn = getSignature();
        //JSONObject json = readJsonFromUrl(URI+"?ak="+ak+"&sn="+sn+"&ip=124.127.112.226");
        JSONObject json = readJsonFromUrl(URI+"?ak="+ak+"&coor=bd09ll");
        System.out.println(json.toString());
        System.out.println(((JSONObject) json.get("content")).get("address"));
        map.put("province",((JSONObject)((JSONObject) json.get("content")).get("address_detail")).get("province"));
        map.put("city",((JSONObject)((JSONObject) json.get("content")).get("address_detail")).get("city"));
        return map;
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

     /*   String jsonString = "{\"data\":{\"destCity\": \"北京市\",\"destDistrict\": \"\"," +
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
        }*/

      /*  String URI = FreightConstant.SERVER_LOCATION_API_URI;
        String ak = FreightConstant.SERVER_AK;
        //String sn = getSignature();
        //JSONObject json = readJsonFromUrl(URI+"?ak="+ak+"&sn="+sn+"&ip=124.127.112.226");
        JSONObject json = readJsonFromUrl(URI+"?ak="+ak+"&ip=202.198.16.3&coor=bd09ll");
        System.out.println(json.toString());
        System.out.println(((JSONObject) json.get("content")).get("address"));
        System.out.println(((JSONObject)((JSONObject) json.get("content")).get("address_detail")).get("province"));
        System.out.println(((JSONObject)((JSONObject) json.get("content")).get("address_detail")).get("city"));*/





        String key = "i am key,let me encrypt you! 1234haha";
        String src = "efyWikiAppServer";

        System.out.println("密钥:" + key);
        System.out.println("明文:" + src);

        String strEnc = DESEncryptUtil.encrypt(src, key);
        System.out.println("加密�?,密文:" + strEnc);

        String strDes = DESEncryptUtil.decrypt(strEnc, key);
        System.out.println("解密�?,明文:" + strDes);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    private static String getSignature() throws  Exception{
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("ak", FreightConstant.SERVER_AK);
        String paramsStr = toQueryString(paramsMap);
        String wholeStr = new String("/location/ip/?" + paramsStr + FreightConstant.SERVER_AK);
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        System.out.println(MD5(tempStr));
        return MD5(tempStr);
    }
    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    // 对Map内所有value作utf8编码，拼接返回结果
    private static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }
}
