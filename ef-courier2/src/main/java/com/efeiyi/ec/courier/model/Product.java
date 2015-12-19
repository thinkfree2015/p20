package com.efeiyi.ec.courier.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Component
public class Product {
    @Value("#{configProperties['productCode']}")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
