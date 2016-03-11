package xerox;

import com.efeiyi.ec.project.model.Project;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.PriorityScheduler;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2016/3/2.
 */
public class MyPageProcessor implements PageProcessor {

    private Site site;
    private List<RegexRuleMap> regexRuleList;

    public void setRegexRuleList(List<RegexRuleMap> regexRuleList) {
        this.regexRuleList = regexRuleList;
    }

    @Override
    public void process(Page page) {
        List<String> detailPageLink = null;//需要带给下个页面的前一页链接
        Map<String, List<String>> extraInfo = null;//带个下个页面的前一页的数据
        //遍历各项抓取规则
        for (RegexRuleMap regexRuleMap : regexRuleList) {
            //详情页处理
            if (regexRuleMap.isDetailPage() && page.getUrl().regex(regexRuleMap.getMatchingUrl()).match()) {
                putDetail(regexRuleMap, page);
            }
            //列表页处理
            else if (!regexRuleMap.isDetailPage() && page.getUrl().regex(regexRuleMap.getMatchingUrl()).match()) {
                //带给下个页面的带数据的数据抓取规则，没有要带的即为null
                if (regexRuleMap.isExtraDetail()) {
                    extraInfo = putExtraValue(regexRuleMap, page);
                }
                //给下个页面带数据的链接抓取规则
                else if (regexRuleMap.isExtraUrl()) {
                    Selectable nextPageLinkSelector = putLinks(page.getHtml(), regexRuleMap);
                    detailPageLink = nextPageLinkSelector.all();
                }
                //其他列表页的抓取规则
                else {
                    Selectable nextPageLinkSelector = putLinks(page.getHtml(), regexRuleMap);
                    List<String> nextLinks = nextPageLinkSelector.all();
                    page.addTargetRequests(nextLinks);
                    page.setSkip(true);
                }
            }
        }
        //如需给下一页带数据，构造全新的request，尼玛这也能行
        if (detailPageLink != null && !detailPageLink.isEmpty() && extraInfo != null && !extraInfo.isEmpty()) {
            for (int x = 0; x < detailPageLink.size(); x++) {
                Request request = new Request(detailPageLink.get(x));
                for (Map.Entry<String, List<String>> extraEntry : extraInfo.entrySet()) {
                    request.putExtra(extraEntry.getKey(), extraEntry.getValue().get(x));
                }
                page.addTargetRequest(request);
            }
        }
    }


    /**
     * 保存要传递到下个页面的数据
     *
     * @param regexRuleMap 抓取规则
     * @param page         待抓页面
     * @return
     */
    private Map<String, List<String>> putExtraValue(RegexRuleMap<RegexRuleMap<String>> regexRuleMap, Page page) {
        Map<String, List<String>> extraFields = new HashMap<>();
        for (Map.Entry<String, RegexRuleMap<String>> regexEntry : regexRuleMap.getNestedRuleRegexMap().entrySet()) {
            extraFields.put(regexEntry.getKey(), putLinks(page.getHtml(), regexEntry.getValue()).all());
        }
        return extraFields;
    }

    /**
     * 保存从详情页抓取的数据
     *
     * @param regexRuleMap 抓取规则
     * @param page         待抓页面
     */
    private void putDetail(RegexRuleMap<RegexRuleMap<String>> regexRuleMap, Page page) {
        for (Map.Entry<String, RegexRuleMap<String>> regexEntry : regexRuleMap.getNestedRuleRegexMap().entrySet()) {
            page.putField(regexEntry.getKey(), putLinks(page.getHtml(), regexEntry.getValue()).all());
        }
    }

    public Site getSite() {
        return site;
    }

    public void setSite(String domain, String url, String charSet) {
        this.site = Site.me().setDomain(domain)
                .addStartUrl(url)
                .setCharset(charSet)
                .setRetryTimes(3)
                .setCycleRetryTimes(3)
                .setTimeOut(10000);
    }

    /**
     * 保存待抓取的列表页
     *
     * @param linksSelector 待抓页面
     * @param regexRuleMap  抓取规则
     * @return
     */
    private Selectable putLinks(Selectable linksSelector, RegexRuleMap<String> regexRuleMap) {
        for (Map.Entry<String, ?> linksRegexEntry : regexRuleMap.getNestedRuleRegexMap().entrySet()) {
            linksSelector = findSelector(linksSelector, linksRegexEntry.getKey(), linksRegexEntry.getValue());
        }
        return linksSelector;
    }

    /**
     * 按照传入规则抓取指定数据
     *
     * @param linksSelector
     * @param key           规则
     * @param value         调用webmagic的函数，一般是xpath或regex
     * @return
     */
    private Selectable findSelector(Selectable linksSelector, String key, Object value) {
        Method method;
        try {
            method = Selectable.class.getDeclaredMethod((String) value, String.class);
            linksSelector = (Selectable) method.invoke(linksSelector, key);
        } catch (NoSuchMethodException e) {
            System.err.println("invalid method name in Selector: " + value + " ignored!");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("invalid method in Selector: " + linksSelector.getClass().getName() + " ignored!");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("insufficient privilege in target Selector:" + value + "  ignored!");
            e.printStackTrace();
        }
        return linksSelector;
    }


    public static void main(String[] a) throws IOException, JMException {
//        fetchProject();
        fetchMaster();
    }

    private static void fetchMaster() throws IOException, JMException {
        String url = "http://www.feiyicheng.com/cms/index.php?act=article&op=directory&level_id=0&batch_id=0&area_id=&type_id=11&keyword=&button=%E7%A1%AE%E5%AE%9A&curpage=1";
        String domain = "www.feiyicheng.com";
        String charSet = "utf-8";

        MyPageProcessor myPageProcessor = new MyPageProcessor();
        myPageProcessor.setSite(domain, url, charSet);
        List<RegexRuleMap> regexRuleList = new ArrayList<>();
        myPageProcessor.setRegexRuleList(regexRuleList);

        RegexRuleMap<String> regexRuleMap = new RegexRuleMap();
        regexRuleMap.put("//ul[@class='minglus']//h2//a/@href", "xpath");
        regexRuleMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=directory[&]level_id=0[&]batch_id=0[&]area_id=[&]type_id=11[&]keyword*");
        regexRuleMap.setIsExtraUrl(true);
        regexRuleList.add(regexRuleMap);

        RegexRuleMap<String> nextRegexRuleMap = new RegexRuleMap();
        nextRegexRuleMap.put("//div[@class='pagination']//a/@href", "xpath");
        nextRegexRuleMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=directory[&]level_id=0[&]batch_id=0[&]area_id=[&]type_id=11[&]keyword*");
        regexRuleList.add(nextRegexRuleMap);

        RegexRuleMap<RegexRuleMap<String>> extraRegexMap = new RegexRuleMap<>();
        extraRegexMap.setIsExtraDetail(true);
        extraRegexMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=directory[&]level_id=0[&]batch_id=0[&]area_id=[&]type_id=11[&]keyword*");
        RegexRuleMap<String> internalExtraRegexMap1 = new RegexRuleMap<>();
        internalExtraRegexMap1.put("//ul[@class='minglus']//img[@class='image_lazy_load']/@data-src", "xpath");
        extraRegexMap.put("picture_url", internalExtraRegexMap1);
        extraRegexMap.put("picture_wap_url", internalExtraRegexMap1);
        extraRegexMap.put("picture_pc_url", internalExtraRegexMap1);
        RegexRuleMap<String> internalExtraRegexMap2 = new RegexRuleMap<>();
        internalExtraRegexMap2.put("//ul[@class='minglus']//h2//a/allText()", "xpath");
        extraRegexMap.put("name", internalExtraRegexMap2);
        RegexRuleMap<String> internalExtraRegexMap3 = new RegexRuleMap<>();
        internalExtraRegexMap3.put("//ul[@class='minglus']//li//p[1]/allText()", "xpath");
        extraRegexMap.put("level", internalExtraRegexMap3);
        RegexRuleMap<String> internalExtraRegexMap4 = new RegexRuleMap<>();
        internalExtraRegexMap4.put("//ul[@class='minglus']//p[3]/allText()", "xpath");
        extraRegexMap.put("type", internalExtraRegexMap4);
        regexRuleList.add(extraRegexMap);

        RegexRuleMap<RegexRuleMap<String>> detailRegexMap = new RegexRuleMap();
        detailRegexMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=article_directory*");
        detailRegexMap.setIsDetailPage(true);
        RegexRuleMap<String> locationRegexMap = new RegexRuleMap<>();
        locationRegexMap.put("//section[@class='article-body']//html()", "xpath");
        detailRegexMap.put("description", locationRegexMap);
        regexRuleList.add(detailRegexMap);


        FilePersistPipeline myPipeline = new FilePersistPipeline("C:/Users/Administrator/Desktop/feiyicheng.txt");
        DBPersistPipeline dbPipeline = new DBPersistPipeline(Project.class);
        Spider spider = Spider.create(myPageProcessor).scheduler(new PriorityScheduler()).pipeline(dbPipeline).pipeline(new ConsolePipeline());
        SpiderMonitor.instance().register(spider);
        spider.start();
    }

    private static void fetchProject() throws IOException, JMException {
        String url = "http://fy.folkw.com/Sort.Asp?page=1&Sort_Id=8";
        String domain = "fy.folkw.com";
        String charSet = "gbk";

        MyPageProcessor myPageProcessor = new MyPageProcessor();
        myPageProcessor.setSite(domain, url, charSet);
        List<RegexRuleMap> regexRuleList = new ArrayList<>();
        myPageProcessor.setRegexRuleList(regexRuleList);

        RegexRuleMap<String> regexRuleMap = new RegexRuleMap();
        regexRuleMap.put("//table[@bgcolor='#D4CAA8']//a[@class='font13']", "xpath");
        regexRuleMap.setMatchingUrl("http://fy.folkw.com/Sort.Asp*");
        regexRuleMap.setIsExtraUrl(true);
        regexRuleList.add(regexRuleMap);

        RegexRuleMap<String> nextRegexRuleMap = new RegexRuleMap();
        nextRegexRuleMap.put("//td[@class='font14']//a[@class='font13']", "xpath");
        nextRegexRuleMap.setMatchingUrl("http://fy.folkw.com/Sort.Asp*");
        regexRuleList.add(nextRegexRuleMap);

        RegexRuleMap<RegexRuleMap<String>> extraRegexMap = new RegexRuleMap<>();
        extraRegexMap.setIsExtraDetail(true);
        extraRegexMap.setMatchingUrl("http://fy.folkw.com/Sort.Asp*");
        RegexRuleMap<String> internalExtraRegexMap = new RegexRuleMap<>();
        internalExtraRegexMap.put("//table[@bgcolor='#D4CAA8']//td[@height='26']//allText()", "xpath");
        extraRegexMap.put("code", internalExtraRegexMap);
        regexRuleList.add(extraRegexMap);

        RegexRuleMap<RegexRuleMap<String>> detailRegexMap = new RegexRuleMap();
        detailRegexMap.setMatchingUrl("http://fy.folkw.com/view.asp*");
        detailRegexMap.setIsDetailPage(true);
        RegexRuleMap<String> locationRegexMap = new RegexRuleMap<>();
        locationRegexMap.put("//table[@bgcolor='#D4CAA8']//tr[1]//td[1]//allText()", "xpath");
        detailRegexMap.put("location", locationRegexMap);
        RegexRuleMap<String> nameRegexMap = new RegexRuleMap<>();
        nameRegexMap.put("//table[@bgcolor='#D4CAA8']//tr[1]//td[2]//allText()", "xpath");
        detailRegexMap.put("name", nameRegexMap);
        RegexRuleMap<String> typeRegexMap = new RegexRuleMap<>();
        typeRegexMap.put("//table[@bgcolor='#D4CAA8']//tr[1]//td[4]//allText()", "xpath");
        detailRegexMap.put("type", typeRegexMap);
        RegexRuleMap<String> levelRegexMap = new RegexRuleMap<>();
        levelRegexMap.put("//table[@bgcolor='#D4CAA8']//tr[2]//td[3]//allText()", "xpath");
        detailRegexMap.put("level", levelRegexMap);
        RegexRuleMap<String> introRegexMap = new RegexRuleMap<>();
        introRegexMap.put("//td[@class='b142 h26']/html()", "xpath");
        detailRegexMap.put("intro", introRegexMap);
        regexRuleList.add(detailRegexMap);


        FilePersistPipeline myPipeline = new FilePersistPipeline("C:/Users/Administrator/Desktop/shujukuxitong.txt");
        Spider spider = Spider.create(myPageProcessor).scheduler(new PriorityScheduler()).pipeline(myPipeline).pipeline(new ConsolePipeline());
        SpiderMonitor.instance().register(spider);
        spider.start();
    }

}
