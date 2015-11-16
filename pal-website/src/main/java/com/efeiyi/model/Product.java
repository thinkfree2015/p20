package com.efeiyi.model;

import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/11/13.
 */
public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    @Field("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Field("price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @Field("product_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
