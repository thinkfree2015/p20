package com.efeiyi.ec.courier.grasp;

import com.alibaba.fastjson.JSONObject;
import com.efeiyi.ec.courier.model.CompanyFreight;
import com.efeiyi.ec.product.model.Product;
import com.ming800.core.base.service.BaseManager;
import com.ming800.core.does.model.XQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 *
 */
@Controller
public class FreightSelectController {

    @Autowired
    private BaseManager baseManager;
    @RequestMapping(value = "/courier/search.do", method = RequestMethod.POST)
    @ResponseBody
    public List<CompanyFreight>  getfreightofcompanies(HttpServletRequest request,@RequestBody JSONObject jsonObj) throws Exception{
        List<CompanyFreight> list = new ArrayList<CompanyFreight>();
        if (jsonObj!=null){
            String weight = jsonObj.getString("weight");
            String from = jsonObj.getString("from");
            String to = jsonObj.getString("to");
            XQuery query = new XQuery("listCompanyFreight_default", request);
            query.put("weight", weight);
            query.put("from",from);
            query.put("to", to);
             list = baseManager.listObject(query);
        }
      return list;
    }
}
