package xerox;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4.
 */
public class RegexRuleMap <T>{

    private Map<String, T> nestedRuleRegexMap = new LinkedHashMap<>();
    private String matchingUrl;
    private boolean isDetailPage;
    private boolean isExtraUrl;
    private boolean isExtraDetail;

    public Map<String, T> getNestedRuleRegexMap() {
        return nestedRuleRegexMap;
    }

    public String getMatchingUrl() {
        return matchingUrl;
    }

    public void setMatchingUrl(String matchingUrl) {
        this.matchingUrl = matchingUrl;
    }

    public boolean isDetailPage() {
        return isDetailPage;
    }

    public void setIsDetailPage(boolean isDetailPage) {
        this.isDetailPage = isDetailPage;
    }

    public boolean isExtraUrl() {
        return isExtraUrl;
    }

    public void setIsExtraUrl(boolean isExtraUrl) {
        this.isExtraUrl = isExtraUrl;
    }

    public boolean isExtraDetail() {
        return isExtraDetail;
    }

    public void setIsExtraDetail(boolean isExtraDetail) {
        this.isExtraDetail = isExtraDetail;
    }

    public T put(String key, T value){
       return nestedRuleRegexMap.put(key,value);
    }
    public T get(String key){
        return nestedRuleRegexMap.get(key);
    }
}
