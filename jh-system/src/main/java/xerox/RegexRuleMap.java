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
    private boolean isExtraKey;
    private boolean isExtraValue;

    public Map<String, T> getNestedRuleRegexMap() {
        return nestedRuleRegexMap;
    }

    public void setNestedRuleRegexMap(LinkedHashMap<String, T> nestedRuleRegexMap) {
        this.nestedRuleRegexMap = nestedRuleRegexMap;
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

    public boolean isExtraKey() {
        return isExtraKey;
    }

    public void setIsExtraKey(boolean isExtraKey) {
        this.isExtraKey = isExtraKey;
    }

    public boolean isExtraValue() {
        return isExtraValue;
    }

    public void setIsExtraValue(boolean isExtraValue) {
        this.isExtraValue = isExtraValue;
    }

    public T put(String key, T value){
       return nestedRuleRegexMap.put(key,value);
    }
    public T get(String key){
        return nestedRuleRegexMap.get(key);
    }
}
