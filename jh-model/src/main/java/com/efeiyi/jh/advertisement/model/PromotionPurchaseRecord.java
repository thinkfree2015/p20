package com.efeiyi.jh.advertisement.model;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/12/3.
 */
@Entity
@Table(name = "promotion_purchase_record")
public class PromotionPurchaseRecord {
    private String id;
    private PurchaseOrder purchaseOrder;
    private PromotionPlan promotionPlan;
    private PromotionUserRecord promotionUserRecord;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_plan_id")
    public PromotionPlan getPromotionPlan() {
        return promotionPlan;
    }

    public void setPromotionPlan(PromotionPlan promotionPlan) {
        this.promotionPlan = promotionPlan;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_user_record_id")
    public PromotionUserRecord getPromotionUserRecord() {
        return promotionUserRecord;
    }

    public void setPromotionUserRecord(PromotionUserRecord promotionUserRecord) {
        this.promotionUserRecord = promotionUserRecord;
    }
}
