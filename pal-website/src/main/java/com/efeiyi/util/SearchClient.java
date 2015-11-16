package com.efeiyi.util;

import com.ming800.core.taglib.PageEntity;
import com.ming800.core.util.JsonUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Administrator on 2015/11/5.
 */
public class SearchClient implements Runnable {

    public static List<String> searchList = new LinkedList<String>();
    public static List<HttpServletRequest> postList = new LinkedList<HttpServletRequest>();
    public static Map resultMap = new LinkedHashMap<>();
    public static Map<HttpServletRequest, Map<String, Object>> responseMap = new LinkedHashMap<HttpServletRequest, Map<String, Object>>();
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
                Map resultMap = postQuery2Solr(request);
                SearchClient.responseMap.put(request, resultMap);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (request) {
                    request.notifyAll();
                }
            }
        }
    }

    private Map<String, Object> postQuery2Solr(HttpServletRequest request) throws Exception {

        //完整查询q
        String queryString = request.getAttribute("q").toString();
        SolrQuery solrQuery = new SolrQuery(queryString.toString());

        String pageIndex = request.getParameter("pageEntity.index");
        String pageSize = request.getParameter("pageEntity.size");
        Object pageEntityObj = request.getAttribute("pageEntity");
        PageEntity pageEntity = (PageEntity) pageEntityObj;
        String facetFields = request.getParameter("facetFields");

        //分页
        solrQuery.setStart(pageIndex == null ? 0 : (pageEntity.getIndex() - 1) * pageEntity.getSize())
                .setRows(pageSize == null ? 10 : Integer.parseInt(pageSize))
                        //高亮
                .addHighlightField("product_name")
                .addHighlightField("specification")
                .setHighlight(true)
                .setHighlightSimplePre("<font color='red'>")
                .setHighlightSimplePost("</font>");

        //分配权重
        solrQuery.set("defType","edismax")
        .set("qf","product_name^5 sub_name^3 specification^3 project_name^4 master_name^2");

        //排序
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        if(sortField != null && !sortField.equals("")){
            solrQuery.setSort(sortField,sortOrder == null || sortOrder.equals("") ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc);
        }

        //分組
        String[] facetFieldsArray = null;
        if (facetFields != null && !facetFields.equals("")) {
            facetFieldsArray = facetFields.split(",");
            solrQuery.setFacet(true)
                    .setFacetMissing(false);
            for (String facetField : facetFieldsArray) {
                solrQuery.addFacetField(facetField)
                ;
            }
        }

        QueryResponse response = solrClient.query(solrQuery);
        Map<String, Object> queryMap = new HashMap<String, Object>();

        //分組結果
        if (facetFields != null && !facetFields.equals("")) {
            Map<String, Map<String,Long>> facetFieldsMap = new HashMap<String, Map<String,Long>>();
            for (String facetField : facetFieldsArray) {
                FacetField facetResult = response.getFacetField(facetField);
                if (facetResult != null) {
                    List<FacetField.Count> facetFieldList = facetResult.getValues();
                    //facet分组的facetFieldList不能转换成json,故抽出所需数据name和条数count另存为facetFieldMap2
                    Map<String,Long> facetFieldMap2 = new HashMap<String,Long>(facetFieldList.size());
                    for(FacetField.Count count : facetFieldList){
                        if(count.getCount() > 0){
                            facetFieldMap2.put(count.getName(), count.getCount());
                        }
                    }
                    facetFieldsMap.put(facetField, facetFieldMap2);
                }
            }
            queryMap.put("facetFieldsMap", facetFieldsMap);
        }

        //檢索結果
        SolrDocumentList searchResultList = response.getResults();
        Map<String, Map<String, List<String>>> highLightingMap = response.getHighlighting();

        pageEntity.setCount((int) searchResultList.getNumFound());

        if (searchResultList != null) {
            for (Object obj : searchResultList) {
                Map docMap = (Map) obj;
                String id = (String) docMap.get("id");
                Map subHighLightingMap = (Map) highLightingMap.get(id);
                for (Object subObj : subHighLightingMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) subObj;
                    if (subHighLightingMap.get(entry.getKey()) instanceof List) {
                        docMap.put(entry.getKey(), ((List) subHighLightingMap.get(entry.getKey())).get(0));
                        continue;
                    }
                    docMap.put(entry.getKey(), subHighLightingMap.get(entry.getKey()));
                }
            }
        }
        queryMap.put("searchResultList", searchResultList);
        return queryMap;

    }

}
