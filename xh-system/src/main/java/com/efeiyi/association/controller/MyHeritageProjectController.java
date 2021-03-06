package com.efeiyi.association.controller;

import com.efeiyi.association.service.MyDocumentManager;
import com.efeiyi.association.util.SavePicsOfDocUtil;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.base.service.XdoManager;
import com.ming800.core.base.service.XdoSupportManager;
import com.ming800.core.does.model.Do;
import com.ming800.core.does.model.DoQuery;
import com.ming800.core.does.model.PageInfo;
import com.ming800.core.does.service.DoManager;
import com.ming800.core.p.model.Document;
import com.ming800.core.p.model.DocumentAttachment;
import com.ming800.core.p.service.AutoSerialManager;
import com.ming800.core.taglib.PageEntity;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 * 非遗保护 Controller
 */

@Controller
@RequestMapping("/myHeritageProject")
public class MyHeritageProjectController {

    @Autowired
    private BaseManager baseManager;
    @Autowired
    @Qualifier("autoSerialManager")
    private AutoSerialManager autoSerialManager;
    @Autowired
    @Qualifier("myDocumentManagerImpl")
    private MyDocumentManager myDocumentManager;
    @Autowired
    private XdoManager xdoManager;
    @Autowired
    private XdoSupportManager xdoSupportManager;
    @Autowired
    private DoManager doManager;

    @RequestMapping("/heritageProject.do")
    public List<Document> getHeritageProjectByGroupId(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String qm = request.getParameter("qm");
        if (qm.split("_").length < 2) {
            throw new Exception("qm:" + qm + "的具体查询部分没有定义即'_'的后半部分没有定义");
        }
        //先找到配置文件里的entity
        Do tempDo = doManager.getDoByQueryModel(qm.split("_")[0]);
        //再从中找到query的信息
        DoQuery tempDoQuery = tempDo.getDoQueryByName(qm.split("_")[1]);

        PageEntity pageEntity = new PageEntity();
        String pageIndex = request.getParameter("pageEntity.index");
        String pageSize = request.getParameter("pageEntity.size");
        if (pageIndex != null) {
            pageEntity.setIndex(Integer.parseInt(pageIndex));
            pageEntity.setSize(Integer.parseInt(pageSize));
        }
        modelMap.put("tabTitle", tempDoQuery.getLabel());
        PageInfo pageInfo = xdoManager.listPage(tempDo, tempDoQuery, null, pageEntity);
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("pageEntity", pageInfo.getPageEntity());

        if (tempDo.getExecute() != null && !tempDo.getExecute().equals("")) {
            modelMap = xdoSupportManager.execute(tempDo, modelMap, request);
        }
        modelMap.put("qm", qm);
        modelMap.put("group", tempDo.getData());
        return pageInfo.getList();
    }

    @RequestMapping("/heritageProjectForm.do")
    public ModelAndView HeritageProjectForm(ModelMap model, HttpServletRequest request, Document document) throws Exception {
        String qm = request.getParameter("qm");
        if (qm.split("_").length < 2) {
            throw new Exception("qm:" + qm + "的具体查询部分没有定义即'_'的后半部分没有定义");
        }
        //先找到配置文件里的entity
        Do tempDo = doManager.getDoByQueryModel(qm.split("_")[0]);
        //设置保存后的返回页面
        model.addAttribute("qm", request.getParameter("resultPage"));
        if (document.getId() != null && !"".equals(document.getId())) {
            document = (Document) baseManager.getObject(document.getClass().getName(), document.getId());
            model.addAttribute("object", document);
        }
        model.addAttribute("group", document.getGroup());
        return new ModelAndView(/*request.getContextPath() +*/ tempDo.getResult());
    }

    @RequestMapping({"/saveHeritageProjectForm.do","/saveHeritageLaw.do"})
    @ResponseBody
    public ModelAndView saveHeritageProject(HttpServletRequest request, Document document) throws Exception {

        String path = request.getParameter("qm");
        document.getDocumentContent().setDocument(document);

        if (document.getId() == null || "".equals(document.getId())) {
            document.setId(null);
            document.getDocumentContent().setId(null);
            document.setStatus("1");
            document.setDocumentOrder(Integer.parseInt(autoSerialManager.nextSerial("documentOrder")));
            document.setPublishDate(new Date());
        }else{
            myDocumentManager.deleteDocument(document);
            document.setId(null);
        }

        SavePicsOfDocUtil.savePicsOfDoc(document);

        baseManager.saveOrUpdate(document.getDocumentContent().getClass().getName(), document.getDocumentContent());
        myDocumentManager.saveDocument(document);

        return new ModelAndView("redirect:"/* + request.getContextPath()*/ + path);
    }

    @RequestMapping("/removeHeritageProject.do")
    @ResponseBody
    public ModelAndView removeHeritageProject(HttpServletRequest request, Document document) throws Exception {
        String path = /*request.getContextPath() +*/ request.getParameter("resultPage");
        myDocumentManager.removeDocument(document);
        return new ModelAndView("redirect:" /*+ request.getContextPath()*/ + path);
    }

    @RequestMapping("/heritageLaw.do")
    public Document getHeritageLawByGroupId(ModelMap modelMap, HttpServletRequest request) throws Exception {
        String qm = request.getParameter("qm");
        if (qm.split("_").length < 2) {
            throw new Exception("qm:" + qm + "的具体查询部分没有定义即'_'的后半部分没有定义");
        }
        //先找到配置文件里的entity
        Do tempDo = doManager.getDoByQueryModel(qm.split("_")[0]);
        //再从中找到query的信息
        DoQuery tempDoQuery = tempDo.getDoQueryByName(qm.split("_")[1]);

        PageEntity pageEntity = new PageEntity();
        String pageIndex = request.getParameter("pageEntity.index");
        String pageSize = request.getParameter("pageEntity.size");
        if (pageIndex != null) {
            pageEntity.setIndex(Integer.parseInt(pageIndex));
            pageEntity.setSize(Integer.parseInt(pageSize));
        }
        modelMap.put("tabTitle", tempDoQuery.getLabel());
        PageInfo pageInfo = xdoManager.listPage(tempDo, tempDoQuery, null, pageEntity);
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("pageEntity", pageInfo.getPageEntity());

        if (tempDo.getExecute() != null && !tempDo.getExecute().equals("")) {
            modelMap = xdoSupportManager.execute(tempDo, modelMap, request);
        }
        modelMap.put("qm", qm);
        modelMap.put("group", tempDo.getData());
        modelMap.put("resultPage", request.getParameter("resultPage"));
        Document document = new Document();
        if (pageInfo.getList() != null && pageInfo.getList().size() >0){
            document = (Document) pageInfo.getList().get(0);
        }
        return document;
    }
}
