package xerox;

import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Administrator on 2016/4/19.
 */
@ThreadSafe
public class MyHttpClientDownloader extends AbstractDownloader {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, CloseableHttpClient> httpClients = new HashMap();
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();
    private Header[] header;
    private HttpClient httpClient ;

    public MyHttpClientDownloader(String header) throws UnsupportedEncodingException {
        httpClient = new DefaultHttpClient();
    }

    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return this.httpClientGenerator.getClient((Site) null);
        } else {
            String domain = site.getDomain();
            CloseableHttpClient httpClient = (CloseableHttpClient) this.httpClients.get(domain);
            if (httpClient == null) {
                synchronized (this) {
                    httpClient = (CloseableHttpClient) this.httpClients.get(domain);
                    if (httpClient == null) {
                        httpClient = this.httpClientGenerator.getClient(site);
                        this.httpClients.put(domain, httpClient);
                    }
                }
            }

            return httpClient;
        }
    }

    public Page download(Request request, Task task) {
        Site site = null;
        if (task != null) {
            site = task.getSite();
        }

        String charset = null;
        Map headers = null;
        Object acceptStatCode;
        if (site != null) {
            acceptStatCode = site.getAcceptStatCode();
            charset = site.getCharset();
            headers = site.getHeaders();
        } else {
            acceptStatCode = Sets.newHashSet(new Integer[]{Integer.valueOf(200)});
        }

        this.logger.info("downloading page {}", request.getUrl());
//        CloseableHttpResponse httpResponse = null;
        HttpResponse response = null;
        int statusCode = 0;

        Page page;
        try {
            try {
//                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(request.getUrl());
                if(header != null){
                    httppost.setHeaders(header);
                }
//                httppost.setHeader("cookie", "bid=\"Jv5lIetUA7E\"; viewed=\"1035801\"; gr_user_id=0ffafada-3afd-40d5-9484-f2aac41cbc08; ll=\"108288\"; ps=y; ct=y; ap=1; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1461141012%2C%22https%3A%2F%2Faccounts.douban.com%2Fregister_success%22%5D; ue=\"liyang@efeiyi.com\"; dbcl2=\"144800746:x1droRcqTfQ\"; ck=\"CCgW\"; push_noty_num=0; push_doumail_num=0; _pk_id.100001.8cb4=87a9d16264d3e12f.1457342477.11.1461141893.1461126482.; _pk_ses.100001.8cb4=*; __utma=30149280.958307834.1457342478.1461124505.1461141012.12; __utmb=30149280.140.10.1461141012; __utmc=30149280; __utmz=30149280.1461124505.11.5.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/register_success; __utmv=30149280.14480");
                response = httpClient.execute(httppost);
                statusCode = response.getStatusLine().getStatusCode();
                if (200 != (statusCode)) {
                    response = retrieveResponse(request.getUrl());
                }
                request.putExtra("statusCode", Integer.valueOf(statusCode));
                if (this.statusAccept((Set) acceptStatCode, statusCode)) {
                    page = this.handleResponse(request, charset, response, task);
                    this.onSuccess(request);
                    Page e1 = page;
                    return e1;
                }

                this.logger.warn("code error " + statusCode + "\t" + request.getUrl());
                page = null;
                return page;
            } catch (IOException var23) {
                this.logger.warn("download page " + request.getUrl() + " error", var23);
                if (site.getCycleRetryTimes() <= 0) {
                    this.onError(request);
                    page = null;
                    return page;
                }
            } catch (InterruptedException e) {
                this.logger.warn("download page " + request.getUrl() + " error", e);
                if (site.getCycleRetryTimes() <= 0) {
                    this.onError(request);
                    page = null;
                    return page;
                }
            }

            page = this.addToCycleRetry(request, site);
        } finally {
            request.putExtra("statusCode", Integer.valueOf(statusCode));

            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (IOException var22) {
                this.logger.warn("close response fail", var22);
            }

        }

        return page;
    }

    private HttpResponse retrieveResponse(String url) throws IOException, InterruptedException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost loginHttppost = new HttpPost("https://www.douban.com/accounts/login");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("form_email", "liyang@efeiyi.com"));
        params.add(new BasicNameValuePair("form_password", "abcd1234"));
        loginHttppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = null;
        try {
            response = httpClient.execute(loginHttppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        header = response.getHeaders("cookie");
        HttpPost httppost = new HttpPost(url);
        httppost.setHeaders(header);
        response = httpClient.execute(httppost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (200 != (statusCode)) {
            response = retrieveResponse(url);
        }
        return response;
    }

    public void setThread(int thread) {
        this.httpClientGenerator.setPoolSize(thread);
    }

    protected boolean statusAccept(Set<Integer> acceptStatCode, int statusCode) {
        return acceptStatCode.contains(Integer.valueOf(statusCode));
    }

    protected HttpUriRequest getHttpUriRequest(Request request, Site site, Map<String, String> headers) {
        RequestBuilder requestBuilder = this.selectRequestMethod(request).setUri(request.getUrl());
        if (headers != null) {
            Iterator requestConfigBuilder = headers.entrySet().iterator();

            while (requestConfigBuilder.hasNext()) {
                Map.Entry host = (Map.Entry) requestConfigBuilder.next();
                requestBuilder.addHeader((String) host.getKey(), (String) host.getValue());
            }
        }

        RequestConfig.Builder requestConfigBuilder1 = RequestConfig.custom().setConnectionRequestTimeout(site.getTimeOut()).setSocketTimeout(site.getTimeOut()).setConnectTimeout(site.getTimeOut()).setCookieSpec("best-match");
        if (site.getHttpProxyPool().isEnable()) {
            HttpHost host1 = site.getHttpProxyFromPool();
            requestConfigBuilder1.setProxy(host1);
            request.putExtra("proxy", host1);
        }

        requestBuilder.setConfig(requestConfigBuilder1.build());
        return requestBuilder.build();
    }

    protected RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method != null && !method.equalsIgnoreCase("GET")) {
            if (method.equalsIgnoreCase("POST")) {
                RequestBuilder requestBuilder = RequestBuilder.post();
                NameValuePair[] nameValuePair = (NameValuePair[]) ((NameValuePair[]) request.getExtra("nameValuePair"));
                if (nameValuePair.length > 0) {
                    requestBuilder.addParameters(nameValuePair);
                }

                return requestBuilder;
            } else if (method.equalsIgnoreCase("HEAD")) {
                return RequestBuilder.head();
            } else if (method.equalsIgnoreCase("PUT")) {
                return RequestBuilder.put();
            } else if (method.equalsIgnoreCase("DELETE")) {
                return RequestBuilder.delete();
            } else if (method.equalsIgnoreCase("TRACE")) {
                return RequestBuilder.trace();
            } else {
                throw new IllegalArgumentException("Illegal HTTP Method " + method);
            }
        } else {
            return RequestBuilder.get();
        }
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
        String content = this.getContent(charset, httpResponse);
        Page page = new Page();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        return page;
    }

    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = this.getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                this.logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

    protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String value = httpResponse.getEntity().getContentType().getValue();
        String charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            this.logger.debug("Auto get charset: {}", charset);
            return charset;
        } else {
            Charset defaultCharset = Charset.defaultCharset();
            String content = new String(contentBytes, defaultCharset.name());
            if (StringUtils.isNotEmpty(content)) {
                Document document = Jsoup.parse(content);
                Elements links = document.select("meta");
                Iterator i$ = links.iterator();

                while (i$.hasNext()) {
                    Element link = (Element) i$.next();
                    String metaContent = link.attr("content");
                    String metaCharset = link.attr("charset");
                    if (metaContent.indexOf("charset") != -1) {
                        metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                        charset = metaContent.split("=")[1];
                        break;
                    }

                    if (StringUtils.isNotEmpty(metaCharset)) {
                        charset = metaCharset;
                        break;
                    }
                }
            }

            this.logger.debug("Auto get charset: {}", charset);
            return charset;
        }
    }
}
