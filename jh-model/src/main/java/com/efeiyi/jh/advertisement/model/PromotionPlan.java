package com.efeiyi.jh.advertisement.model;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
@Entity
@Table(name = "promotion_plan")
public class PromotionPlan {

    private String id;
    private String identifier;
    private String name;
    private String url;
    private String urlMark;
    private String urlDescription;
    private Date createDatetime;
    private String status;
    private Integer rdDays;
    private List<PromotionPurchaseRecord> promotionPurchaseRecordList;
    private List<PromotionUserRecord> promotionUserRecordList;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "identifier")
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "url")
    public String getUrl() {
        return url+"?pch="+id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "url_mark")
    public String getUrlMark() {
        return urlMark;
    }

    public void setUrlMark(String urlMark) {
        this.urlMark = urlMark;
    }

    @Column(name = "url_description")
    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    @Column(name = "create_datetime")
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotionPlan")
    public List<PromotionPurchaseRecord> getPromotionPurchaseRecordList() {
        return promotionPurchaseRecordList;
    }

    public void setPromotionPurchaseRecordList(List<PromotionPurchaseRecord> promotionPurchaseRecordList) {
        this.promotionPurchaseRecordList = promotionPurchaseRecordList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotionPlan")
    public List<PromotionUserRecord> getPromotionUserRecordList() {
        return promotionUserRecordList;
    }

    public void setPromotionUserRecordList(List<PromotionUserRecord> promotionUserRecordList) {
        this.promotionUserRecordList = promotionUserRecordList;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "rd_days")
    public Integer getRdDays() {
        return rdDays;
    }

    public void setRdDays(Integer rdDays) {
        this.rdDays = rdDays;
    }
}
