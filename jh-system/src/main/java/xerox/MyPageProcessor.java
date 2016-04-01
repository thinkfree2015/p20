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
            //详情页分页抓取
            else if (regexRuleMap.isHasMoreExtra() && page.getUrl().regex(regexRuleMap.getMatchingUrl()).match()) {
                detailPageLink = putLinks(page.getHtml(), regexRuleMap).all();
                List<String> morePagesList = (List<String>) page.getRequest().getExtras().get("morePagesList");
                //有翻页

                if (detailPageLink.size() > 0) {
                    //有分页但未开始抓取分页
                    if (morePagesList == null) {
                        page.getRequest().putExtra("morePagesList", detailPageLink);
                        putAll2Extra(page, detailPageLink.remove(0));
                    }
                    //有分页且已抓取部分
                    else if (morePagesList.size() > 0) {
//                        morePagesList.remove(page.getUrl().get());
                        if(morePagesList.size() > 0) {
                            putAll2Extra(page, morePagesList.remove(0));
                        }
                    }
                    //分页都抓完
                    else{
                        putExtra2All(page);
                        System.out.println("pause");
                    }
                }
            }
            //列表页处理
            else if (!regexRuleMap.isDetailPage() && page.getUrl().regex(regexRuleMap.getMatchingUrl()).match()) {
                //带给下个页面的带数据的数据抓取规则，没有要带的即为null
                if (regexRuleMap.isExtraDetail()) {
                    extraInfo = putExtraValue(regexRuleMap, page);
                }
                //给下个页面带数据的链接抓取规则
                else if (regexRuleMap.isExtraUrl()) {
                    detailPageLink = putLinks(page.getHtml(), regexRuleMap).all();
                }
                //其他列表页的抓取规则
                else {
                    Selectable nextPageLinkSelector = putLinks(page.getHtml(), regexRuleMap);
                    List<String> nextLinks = nextPageLinkSelector.all();
                    page.addTargetRequests(nextLinks);
                }
                page.setSkip(true);
            }
        }
        //如需给下一页带数据，构造全新的request，尼玛这也能行
        if (detailPageLink != null && !detailPageLink.isEmpty() && extraInfo != null && !extraInfo.isEmpty()) {
            detailPageLink = (List) removeDuplicated(detailPageLink);
            for (int x = 0; x < detailPageLink.size(); x++) {
                Request request = new Request(detailPageLink.get(x));
                for (Map.Entry<String, List<String>> extraEntry : extraInfo.entrySet()) {
                    if (!(detailPageLink.size() == extraEntry.getValue().size())) {
                        break;
                    }
                    request.putExtra(extraEntry.getKey(), extraEntry.getValue().get(x));
                }
                for (Map.Entry<String, ?> extraEntry : page.getRequest().getExtras().entrySet()) {
                    request.putExtra(extraEntry.getKey(), extraEntry.getValue().toString());
                }
                page.addTargetRequest(request);
            }
        }
    }

    /**
     * 把extraData存入field，便于同一字段的各个值集中输出
     * @param page
     */
    private void putExtra2All(Page page){
        for (Map.Entry<String, Object> extraEntry : page.getRequest().getExtras().entrySet()) {
            if(page.getResultItems().get(extraEntry.getKey()) == null) {
                page.putField(extraEntry.getKey(), extraEntry.getValue());
            }else{
                ((List)page.getResultItems().get(extraEntry.getKey())).add(extraEntry.getValue().toString());
            }
        }
        page.getRequest().getExtras().clear();
    }

    /**
     * 把detailDate存入extra，方便带往下个详情翻页
     * @param page 当前页
     * @param url 下个详情页
     */
    private void putAll2Extra(Page page,String url) {
        Request request = new Request(url);
        for (Map.Entry<String, Object> extraEntry : page.getRequest().getExtras().entrySet()) {
            request.putExtra(extraEntry.getKey(), extraEntry.getValue());
        }
        for (Map.Entry<String, Object> dataEntry : page.getResultItems().getAll().entrySet()) {
            if(request.getExtra(dataEntry.getKey()) == null) {
                request.putExtra(dataEntry.getKey(), dataEntry.getValue());
            }else{
                ((List)request.getExtra(dataEntry.getKey())).add(dataEntry.getValue().toString());
            }
        }
        page.getResultItems().getAll().clear();
        page.addTargetRequest(request);
        page.setSkip(true);
    }

    /**
     * 去重集合里的对象
     * @param sourceCollection
     * @return
     */
    private Collection removeDuplicated(Collection sourceCollection) {
        Set<String> noDuplicatedDetailPageLink = new LinkedHashSet();
        noDuplicatedDetailPageLink.addAll(sourceCollection);
        sourceCollection.clear();
        sourceCollection.addAll(noDuplicatedDetailPageLink);
        return sourceCollection;
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
                .setRetryTimes(4)
                .setCycleRetryTimes(4)
                .setTimeOut(20000)
//                .setSleepTime(1)
                ;
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
//        fetchChinacultureProject();
//        fetchFeiyichengProject();
        fetchFeiyichengMaster();
    }

    private static void fetchFeiyichengProject() throws IOException, JMException {
        String url = "http://feiyicheng.com/cms/index.php?act=article&op=directory&curpage=1ge=1";
        String domain = "www.feiyicheng.com";
        String charSet = "utf-8";

        MyPageProcessor myPageProcessor = new MyPageProcessor();
        myPageProcessor.setSite(domain, url, charSet);
        List<RegexRuleMap> regexRuleList = new ArrayList<>();
        myPageProcessor.setRegexRuleList(regexRuleList);

        RegexRuleMap<String> regexRuleMap = new RegexRuleMap();
        regexRuleMap.put("//ul[@class='minglus']//h2//a/@href", "xpath");
        regexRuleMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=directory*");
        regexRuleMap.setIsExtraUrl(true);
        regexRuleList.add(regexRuleMap);

        RegexRuleMap<String> nextRegexRuleMap = new RegexRuleMap();
        nextRegexRuleMap.put("//div[@class='pagination']//a/@href", "xpath");
        nextRegexRuleMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=directory*");
        regexRuleList.add(nextRegexRuleMap);

        RegexRuleMap<RegexRuleMap<String>> extraRegexMap = new RegexRuleMap<>();
        extraRegexMap.setIsExtraDetail(true);
        extraRegexMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=directory*");
        RegexRuleMap<String> internalExtraRegexMap1 = new RegexRuleMap<>();
        internalExtraRegexMap1.put("//ul[@class='minglus']//img[@class='image_lazy_load']/@data-src", "xpath");
        extraRegexMap.put("picture_url", internalExtraRegexMap1);
//        extraRegexMap.put("picture_wap_url", internalExtraRegexMap1);
//        extraRegexMap.put("picture_pc_url", internalExtraRegexMap1);
        RegexRuleMap<String> internalExtraRegexMap2 = new RegexRuleMap<>();
        internalExtraRegexMap2.put("//ul[@class='minglus']//h2//a/allText()", "xpath");
        extraRegexMap.put("name", internalExtraRegexMap2);
        RegexRuleMap<String> internalExtraRegexMap3 = new RegexRuleMap<>();
        internalExtraRegexMap3.put("//ul[@class='minglus']//li//p[1]/allText()", "xpath");
        extraRegexMap.put("level", internalExtraRegexMap3);
        RegexRuleMap<String> internalExtraRegexMap4 = new RegexRuleMap<>();
        internalExtraRegexMap4.put("//ul[@class='minglus']//li//p[3]/allText()", "xpath");
        extraRegexMap.put("location", internalExtraRegexMap4);
        RegexRuleMap<String> internalExtraRegexMap5 = new RegexRuleMap<>();
        internalExtraRegexMap5.put("//ul[@class='minglus']//li//p[2]/allText()", "xpath");
        extraRegexMap.put("type", internalExtraRegexMap5);
        regexRuleList.add(extraRegexMap);

        RegexRuleMap<RegexRuleMap<String>> detailRegexMap = new RegexRuleMap();
        detailRegexMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=article_directory*");
        detailRegexMap.setIsDetailPage(true);
        RegexRuleMap<String> locationRegexMap = new RegexRuleMap<>();
        locationRegexMap.put("//section[@class='article-body']//html()", "xpath");
        detailRegexMap.put("description", locationRegexMap);
        regexRuleList.add(detailRegexMap);


        FilePersistPipeline myPipeline = new FilePersistPipeline("C:/Users/Administrator/Desktop/feiyichengProject.csv", "::", ",,");
//        DBPersistPipeline dbPipeline = new DBPersistPipeline(Project.class);
        Spider spider = Spider.create(myPageProcessor).scheduler(new PriorityScheduler()).pipeline(myPipeline).pipeline(new ConsolePipeline());
        SpiderMonitor.instance().register(spider);
        spider.start();
    }

    private static void fetchFeiyichengMaster() throws IOException, JMException {
        String url = "http://www.feiyicheng.com/cms/index.php?act=article&op=inherit&curpage=1";
        String domain = "www.feiyicheng.com";
        String charSet = "utf-8";

        MyPageProcessor myPageProcessor = new MyPageProcessor();
        myPageProcessor.setSite(domain, url, charSet);
        java.util.List<RegexRuleMap> regexRuleList = new ArrayList<>();
        myPageProcessor.setRegexRuleList(regexRuleList);

        RegexRuleMap<String> regexRuleMap = new RegexRuleMap();
        regexRuleMap.put("//table[@width='100%']//a/@href", "xpath");
        regexRuleMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=inherit*");
        regexRuleMap.setIsExtraUrl(true);
        regexRuleList.add(regexRuleMap);

        RegexRuleMap<String> nextRegexRuleMap = new RegexRuleMap();
        nextRegexRuleMap.put("//div[@class='pagination']//a/@href", "xpath");
        nextRegexRuleMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=inherit*");
        regexRuleList.add(nextRegexRuleMap);

        RegexRuleMap<RegexRuleMap<String>> extraRegexMap = new RegexRuleMap<>();
        extraRegexMap.setIsExtraDetail(true);
        extraRegexMap.setMatchingUrl("www.feiyicheng.com/cms/index.php[?]act=article[&]op=inherit*");
        RegexRuleMap<String> internalExtraRegexMap2 = new RegexRuleMap<>();
        internalExtraRegexMap2.put("//div[@class='ccrlist']//td[2]/allText()", "xpath");
        extraRegexMap.put("name", internalExtraRegexMap2);
        RegexRuleMap<String> internalExtraRegexMap4 = new RegexRuleMap<>();
        internalExtraRegexMap4.put("//div[@class='ccrlist']//td[4]/allText()", "xpath");
        extraRegexMap.put("project", internalExtraRegexMap4);
        RegexRuleMap<String> internalExtraRegexMap5 = new RegexRuleMap<>();
        internalExtraRegexMap5.put("//div[@class='ccrlist']//td[5]/allText()", "xpath");
        extraRegexMap.put("type", internalExtraRegexMap5);
        RegexRuleMap<String> internalExtraRegexMap6 = new RegexRuleMap<>();
        internalExtraRegexMap6.put("//div[@class='ccrlist']//td[6]/allText()", "xpath");
        extraRegexMap.put("level", internalExtraRegexMap6);
        RegexRuleMap<String> internalExtraRegexMap7 = new RegexRuleMap<>();
        internalExtraRegexMap7.put("//div[@class='ccrlist']//td[7]/allText()", "xpath");
        extraRegexMap.put("location", internalExtraRegexMap7);
        regexRuleList.add(extraRegexMap);

        RegexRuleMap<RegexRuleMap<String>> detailRegexMap = new RegexRuleMap();
        detailRegexMap.setMatchingUrl("http://www.feiyicheng.com/cms/index.php[?]act=article[&]op=article_inherit*");
        detailRegexMap.setIsDetailPage(true);
        RegexRuleMap<String> titleRegexMap = new RegexRuleMap<>();
        titleRegexMap.put("//div[@class='ccrinfo']//h3/allText()", "xpath");
        detailRegexMap.put("title", titleRegexMap);
        RegexRuleMap<String> descriptionRegexMap = new RegexRuleMap<>();
        descriptionRegexMap.put("//div[@class='ccrcon']//html()", "xpath");
        detailRegexMap.put("description", descriptionRegexMap);
        RegexRuleMap<String> headimgRegexMap = new RegexRuleMap<>();
        headimgRegexMap.put("//div[@class='ccrinfo']//img/@src", "xpath");
        detailRegexMap.put("picture_url", headimgRegexMap);
        regexRuleList.add(detailRegexMap);


        FilePersistPipeline myPipeline = new FilePersistPipeline("C:/Users/Administrator/Desktop/feiyichengMaster.csv", "@", "@");
//        DBPersistPipeline dbPipeline = new DBPersistPipeline(Project.class);
        Spider spider = Spider.create(myPageProcessor).scheduler(new PriorityScheduler()).pipeline(myPipeline).pipeline(new ConsolePipeline());
        SpiderMonitor.instance().register(spider);
        spider.start();
    }

    private static void fetchChinacultureProject() throws IOException, JMException {
        String url = "http://www.chinaculture.org/gb/cn_whyc/node_3041.htm";
        String domain = "www.chinaculture.org";
        String charSet = "gbk";

        MyPageProcessor myPageProcessor = new MyPageProcessor();
        myPageProcessor.setSite(domain, url, charSet);
        List<RegexRuleMap> regexRuleList = new ArrayList<>();
        myPageProcessor.setRegexRuleList(regexRuleList);

        RegexRuleMap<String> regexRuleMap = new RegexRuleMap();
        regexRuleMap.put("//table[@width='129']//td[@height='20']//a/@href", "xpath");
        regexRuleMap.setMatchingUrl("http://www.chinaculture.org/gb/cn_whyc/node_3041.htm");
        regexRuleList.add(regexRuleMap);

        RegexRuleMap<String> regexRuleMap2 = new RegexRuleMap();
        regexRuleMap2.put("//div[@align='center']//a/@href", "xpath");
        regexRuleMap2.setMatchingUrl("http://www.chinaculture.org/gb/cn_whyc/node*");
        regexRuleMap2.setIsExtraUrl(true);
        regexRuleList.add(regexRuleMap2);

        RegexRuleMap<RegexRuleMap<String>> extraRegexRuleMap = new RegexRuleMap();
        extraRegexRuleMap.setMatchingUrl("http://www.chinaculture.org/gb/cn_whyc/node*");
        extraRegexRuleMap.setIsExtraDetail(true);
        RegexRuleMap<String> internalExtraRegexMap = new RegexRuleMap<>();
        internalExtraRegexMap.put("//td[@height='25']//allText()", "xpath");
        extraRegexRuleMap.put("sequence", internalExtraRegexMap);
        regexRuleList.add(extraRegexRuleMap);

        RegexRuleMap<RegexRuleMap<String>> detailRegexRuleMap = new RegexRuleMap();
        detailRegexRuleMap.setMatchingUrl("http://www.chinaculture.org/gb/cn_whyc/20*");
        detailRegexRuleMap.setIsDetailPage(true);
        RegexRuleMap<String> locationRegexMap = new RegexRuleMap<>();
        locationRegexMap.put("//p[5]//allText()", "xpath");
        detailRegexRuleMap.put("location", locationRegexMap);
        RegexRuleMap<String> nameRegexMap = new RegexRuleMap<>();
        nameRegexMap.put("//table[@width='691']//b//allText()", "xpath");
        detailRegexRuleMap.put("name", nameRegexMap);
        RegexRuleMap<String> typeRegexMap = new RegexRuleMap<>();
        typeRegexMap.put("//p[4]//allText()", "xpath");
        detailRegexRuleMap.put("type", typeRegexMap);
        RegexRuleMap<String> introRegexMap = new RegexRuleMap<>();
        introRegexMap.put("//div[@id='content']//html()", "xpath");
        detailRegexRuleMap.put("intro", introRegexMap);
        RegexRuleMap<String> pictureRegexMap = new RegexRuleMap<>();
        pictureRegexMap.put("//object[@name='founderflashobject']//embed/@src", "xpath");
        detailRegexRuleMap.put("picture_url", pictureRegexMap);
        RegexRuleMap<String> timeRegexMap = new RegexRuleMap<>();
        timeRegexMap.put("//p[3]//allText()", "xpath");
        detailRegexRuleMap.put("time", timeRegexMap);
        RegexRuleMap<String> codeRegexMap = new RegexRuleMap<>();
        codeRegexMap.put("//p[6]//allText()", "xpath");
        detailRegexRuleMap.put("code", codeRegexMap);
        RegexRuleMap<String> senderRegexMap = new RegexRuleMap<>();
        senderRegexMap.put("//p[7]//allText()", "xpath");
        detailRegexRuleMap.put("senderRegexMap", senderRegexMap);
        regexRuleList.add(detailRegexRuleMap);

        RegexRuleMap<String> nextDetailRegexRuleMap = new RegexRuleMap();
        nextDetailRegexRuleMap.setMatchingUrl("http://www.chinaculture.org/gb/cn_whyc/20*");
        nextDetailRegexRuleMap.put("//div[@id='content']//a[@class='apage']/@href", "xpath");
        nextDetailRegexRuleMap.setHasMoreExtra(true);
        regexRuleList.add(nextDetailRegexRuleMap);


        FilePersistPipeline myPipeline = new FilePersistPipeline("C:/Users/Administrator/Desktop/shujukuxitong.txt", "@", "@");
        Spider spider = Spider.create(myPageProcessor).scheduler(new PriorityScheduler()).pipeline(myPipeline).pipeline(new ConsolePipeline());
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


        FilePersistPipeline myPipeline = new FilePersistPipeline("C:/Users/Administrator/Desktop/shujukuxitong.txt", "::", ",,");
        Spider spider = Spider.create(myPageProcessor).scheduler(new PriorityScheduler()).pipeline(myPipeline).pipeline(new ConsolePipeline());
        SpiderMonitor.instance().register(spider);
        spider.start();
    }

}
