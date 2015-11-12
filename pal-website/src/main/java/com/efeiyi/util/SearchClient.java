package com.efeiyi.util;

import com.ming800.core.does.model.Page;
import com.ming800.core.taglib.PageEntity;
import com.ming800.core.util.JsonUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/5.
 */
public class SearchClient implements Runnable {

    public static List<String> searchList = new LinkedList<String>();
    public static List<HttpServletRequest> postList = new LinkedList<HttpServletRequest>();
    public static Map resultMap = new LinkedHashMap<>();
    public static Map<HttpServletRequest,List> responseMap = new LinkedHashMap<HttpServletRequest,List>();
    //    private static HttpClient httpclient = new HttpClient();
    public static HttpSolrClient solrClient = new HttpSolrClient("http://localhost:8080/solr-5.3.1/product");

//    public void run() {
//        while (true) {
//            if (SearchClient.searchList.isEmpty()) {
//                synchronized (SearchClient.searchList) {
//                    if (SearchClient.searchList.isEmpty()) {
//                        try {
//                            SearchClient.searchList.wait();
//                        } catch (InterruptedException e) {
//                            Thread.currentThread().interrupt();
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//            String query = SearchClient.searchList.remove(0);
//            try {
//                Map result = postQuery2Solr(query);
//                SearchClient.resultMap.put(query, result);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                synchronized (query) {
//                    query.notifyAll();
//                }
//            }
//        }
//    }

//    private Map postQuery2Solr(String query) throws Exception {
//        String url = PalConst.getInstance().solrUrl + query;
//        HttpMethod method = new GetMethod(url);
//        httpclient.executeMethod(method);
//        String json = method.getResponseBodyAsString();
//        System.out.println(method.getResponseBodyAsString());
//        method.releaseConnection();
//        Map<?, ?> map = JsonUtil.parseJsonStringToMap(json);
//        return map;
//    }


    public void run() {
        while (true) {
            if (SearchClient.postList.isEmpty()) {
                synchronized (SearchClient.postList) {
                    if (SearchClient.postList.isEmpty()) {
                        try {
                            SearchClient.postList.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                }
            }

            HttpServletRequest request = SearchClient.postList.remove(0);
            try {
                List docsList = postQuery2Solr(request);
                SearchClient.responseMap.put(request, docsList);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (request) {
                    request.notifyAll();
                }
            }
        }
    }

    private List postQuery2Solr(HttpServletRequest request) throws Exception {

        String queryString = request.getParameter("q");
        SolrQuery solrQuery = new SolrQuery(queryString);

        String pageIndex = request.getParameter("pageEntity.index");
        String pageSize = request.getParameter("pageEntity.size");
        Object pageEntityObj = request.getAttribute("pageEntity");
        PageEntity pageEntity = (PageEntity)pageEntityObj;

        solrQuery.setStart(pageIndex == null ? 0 : (pageEntity.getIndex()-1)*pageEntity.getSize())
                .setRows(pageSize == null ? 10 : Integer.parseInt(pageSize))
                .addHighlightField("product_name")
                .addHighlightField("master_name")
                .addHighlightField("tenant_name")
                .addHighlightField("sub_name")
                .setHighlight(true)
                .setHighlightSimplePre("<font color='red'>")
                .setHighlightSimplePost("</font>");
        QueryResponse response = solrClient.query(solrQuery);

        SolrDocumentList docsList = response.getResults();
        Map<String, Map<String, List<String>>> highLightingMap = response.getHighlighting();

        pageEntity.setCount((int)docsList.getNumFound());

        for(Object obj : docsList){
            Map docMap = (Map)obj;
            String id = (String)docMap.get("id");
            Map subHighLightingMap = (Map)highLightingMap.get(id);
            for(Object subObj : subHighLightingMap.entrySet()){
                Map.Entry entry = (Map.Entry)subObj;
                if (subHighLightingMap.get(entry.getKey()) instanceof  List){
                    docMap.put(entry.getKey(),((List)subHighLightingMap.get(entry.getKey())).get(0));
                    continue;
                }
                docMap.put(entry.getKey(),subHighLightingMap.get(entry.getKey()));
            }
        }
        return docsList;

    }

}
