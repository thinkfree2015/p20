package xerox;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4.
 */
public class RegexRuleMap <T>{

    private Map<String, T> nestedRuleRegexMap = new LinkedHashMap<>();
    private String matchingUrl;//当前规则匹配的页面地址
    private boolean isDetailPage;//当前规则是否为获取详情页的规则
    private boolean isExtraUrl;//
    private boolean isExtraDetail;//当前规则是给详情页补充来自其他页面数据的规则
    private boolean hasMoreExtra;

    public boolean isHasMoreExtra() {
        return hasMoreExtra;
    }

    public void setHasMoreExtra(boolean hasMoreExtra) {
        this.hasMoreExtra = hasMoreExtra;
    }

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
