package com.efeiyi.pal.system.tenantProductSeries.controller;

import com.efeiyi.pal.product.model.TenantProductSeries;
import com.efeiyi.pal.product.model.TenantProductSeriesImg;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.p.service.AliOssUploadManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/24.
 */

@Controller
@RequestMapping("/tenantProductSeriesImg")
public class TenantProductSeriesImgController {

    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");
    private AliOssUploadManager aliOssUploadManager = (AliOssUploadManager) ApplicationContextUtil.getApplicationContext().getBean("aliOssUploadManagerImpl");

    @RequestMapping("/saveTenantProductSeriesImg.do")
    public ModelAndView saveTenantProductSeriesImg(HttpServletRequest request, MultipartRequest multipartRequest) throws Exception{

        TenantProductSeriesImg tenantProductSeriesImg = new TenantProductSeriesImg();

        String tenantProductSeriesId = request.getParameter("tenantProductSeriesId");
        if (tenantProductSeriesId == null || tenantProductSeriesId.trim().equals("")){
            throw new Exception("class com.efeiyi.pal.system.tenantProductSeries.controller.TenantProductSeriesImgController saveTenantProductSeriesImg method: tenantProductSeriesId is null !");
        }
        TenantProductSeries tenantProductSeries = (TenantProductSeries)baseManager.getObject(TenantProductSeries.class.getName(), tenantProductSeriesId);
        tenantProductSeriesImg.setTenantProductSeries(tenantProductSeries);
        tenantProductSeriesImg.setStatus("1");
        tenantProductSeriesImg = upLoadImg(multipartRequest, tenantProductSeriesImg);
        baseManager.saveOrUpdate(TenantProductSeriesImg.class.getName(), tenantProductSeriesImg);

        String resultPage = "redirect:/basic/xm.do?qm=viewTenantProductSeries&id=" + tenantProductSeries.getId();

        return new ModelAndView(resultPage);
    }

    @RequestMapping("/removeTenantProductSeriesImg.do")
    public ModelAndView removeTenantProductSeriesImg(HttpServletRequest request) throws Exception{
        String tenantProductSeriesId = request.getParameter("tenantProductSeriesId");
        String ImgId = request.getParameter("ImgId");
        if (tenantProductSeriesId == null || tenantProductSeriesId.trim().equals("")){
            throw new Exception("class com.efeiyi.pal.system.tenantProductSeries.controller.TenantProductSeriesImgController removeTenantProductSeriesImg method: tenantProductSeriesId is null !");
        }
        if (ImgId == null || ImgId.trim().equals("")){
            throw new Exception("class com.efeiyi.pal.system.tenantProductSeries.controller.TenantProductSeriesImgController removeTenantProductSeriesImg method: ImgId is null !");
        }
        TenantProductSeries tenantProductSeries = (TenantProductSeries)baseManager.getObject(TenantProductSeries.class.getName(), tenantProductSeriesId);
        TenantProductSeriesImg tenantProductSeriesImg = (TenantProductSeriesImg) baseManager.getObject(TenantProductSeriesImg.class.getName(), ImgId);
        tenantProductSeriesImg.setStatus("0");
        baseManager.saveOrUpdate(TenantProductSeriesImg.class.getName(), tenantProductSeriesImg);

        String resultPage = "redirect:/basic/xm.do?qm=viewTenantProductSeries&id=" + tenantProductSeries.getId();

        return new ModelAndView(resultPage);
    }

    private TenantProductSeriesImg upLoadImg(MultipartRequest multipartRequest, TenantProductSeriesImg tenantProductSeriesImg) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String identify = sdf.format(new Date());
        String url = "tenantSource/" + identify + ".jpg";

        if (!multipartRequest.getFile("Img").getOriginalFilename().equals("")) {
            aliOssUploadManager.uploadFile(multipartRequest.getFile("Img"), "315pal", url);
            tenantProductSeriesImg.setImgUrl(url);
        }

        return tenantProductSeriesImg;
    }

}