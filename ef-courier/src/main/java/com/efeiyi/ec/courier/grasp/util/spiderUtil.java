package com.efeiyi.ec.courier.grasp.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
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
    public List<Map<String,String>> parserHtml(String url)throws  Exception{
        Document doc = jsoup.connect(url).get();
        Elements elements = doc.select("tr[bgcolor=#FFFFFF]");
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        if(elements!=null&& !elements.isEmpty()){

            for (Element element : elements) {
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
            }

        }

        return list;
    }

}
