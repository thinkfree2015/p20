package com.efeiyi.pal.system.label.controller;

import com.efeiyi.pal.label.model.LabelBatch;
import com.efeiyi.pal.system.label.labelService.LabelBuildService;
import com.ming800.core.base.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2015/7/28.
 * 标签处理 Controller
 */

@Controller
@RequestMapping("/Label")
public class LabelController {

    @Autowired
    private LabelBuildService labelBuildService;

    @Autowired
    private BaseManager baseManager;

    @RequestMapping("/saveLabelList.do")
    public ModelAndView saveLabelList(HttpServletRequest request) throws Exception {

        String labelBatchId = request.getParameter("labelBatchId");
        if (labelBatchId == null || labelBatchId.trim().equals("")) {
            throw new Exception("labelBatchId不能为空");
        }
        LabelBatch labelBatch = (LabelBatch) baseManager.getObject(LabelBatch.class.getName(), labelBatchId);
        String resultPage = labelListSave(labelBatch, labelBatchId);

        return new ModelAndView(resultPage);
    }

    @RequestMapping("/downLabelTxt.do")
    public void downloadLabelTxt(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String labelBatchId = request.getParameter("labelBatchId");
        String path = this.getClass().getClassLoader().getResource("/").getPath();
        File clazzDir = new File(path);
        String fileName = clazzDir.getParent() + "/file/" + labelBatchId + ".txt";
        File file = new File(fileName);
//        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentLength((int) file.length());
        BufferedReader bis = null;
        try {
            if (!file.exists())
                throw new NullPointerException("指定文件" + fileName + "不存在");
            bis = new BufferedReader(new FileReader(new File(fileName)));
            int i;
            while ((i = bis.read()) != -1) {
                response.getWriter().write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String labelListSave(LabelBatch labelBatch, String labelBatchId) throws Exception{

        if (labelBatch == null) {
            throw new Exception("Id为" + labelBatchId + "的标签批次不存在!");
        }
        labelBuildService.buildLabelSetByLabelBatch(labelBatch);//批量生成防伪标签

        labelBatch.setStatus("2");
        baseManager.saveOrUpdate(labelBatch.getClass().getName(), labelBatch);
        return "redirect:/basic/xm.do?qm=plistLabel_batchLabel&conditions=labelBatch.serial:" + labelBatch.getSerial();
    }

}
