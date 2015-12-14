package com.efeiyi.ec.courier.grasp.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Service
public class spiderUtil {
    private Jsoup jsoup;



    public List<Object>  parserHtml(String url)throws  Exception{
        Document doc = jsoup.connect(url).timeout(5000).get();
        Elements elements = doc.select("tr[bgcolor=#FFFFFF]");
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        Map<String,String> size = new HashMap<String,String>();
        List<Object> dataList = new ArrayList<Object>();
        int count =0;
        if(elements!=null&& !elements.isEmpty()){

            for (Element element : elements) {
                if(count>=elements.size()-1){
                    Elements childrenElements =element.select("a[href]");
                    if(childrenElements!=null&& !childrenElements.isEmpty()){
                        size.put("size",childrenElements.eq(0).text()!=null&& childrenElements.eq(0).text()!=""?childrenElements.eq(0).text():"");
                        dataList.add(size);
                    }
                    break;
                }
                Map<String,String> map = new HashMap<String,String>();
                Elements childrenElements =element.select("a[href]");
                if(childrenElements!=null&& !childrenElements.isEmpty()){
                    map.put("name",childrenElements.eq(0).text()!=null&& childrenElements.eq(0).text()!=""?childrenElements.eq(0).text():"");
                }
                String price = element.getElementsByTag("td").eq(1).text();
                if(price!= null && !"".equals(price)){
                    map.put("price", price);
                }else{
                    map.put("price", "");
                }

                String times = element.getElementsByTag("td").eq(2).text();
                if(price!= null && !"".equals(price)){
                    map.put("times", times);
                }else{
                    map.put("times", "");
                }
                list.add(map);
                count++;
            }

        }
        dataList.add(list);
       /* for (Object obj: dataList) {//解析返回数据
            if(obj instanceof Map){
                System.out.println(((Map)obj).get("size").toString());
            }else if(obj instanceof List){
                for( Map<String,String> map :(List<Map<String,String>>)obj){
                    System.out.println(map.get("name")+"  "+map.get("price")+"   "+map.get("times"));
                }
            }
        }*/
        return dataList;
    }
    public Jsoup getJsoup() {
        return jsoup;
    }

    public void setJsoup(Jsoup jsoup) {
        this.jsoup = jsoup;
    }
}
