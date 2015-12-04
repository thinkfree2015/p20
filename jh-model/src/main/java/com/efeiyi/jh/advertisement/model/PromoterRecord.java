package com.efeiyi.jh.advertisement.model;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/12/3.
 */
@Entity
@Table(name = "promoter_record")
public class PromoterRecord {
    private String id;
    private PurchaseOrder purchaseOrder;
    private PromoterPlan promoterPlan;

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
    @JoinColumn(name = "purchaset_order_id")
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promoter_id")
    public PromoterPlan getPromoterPlan() {
        return promoterPlan;
    }

    public void setPromoterPlan(PromoterPlan promoterPlan) {
        this.promoterPlan = promoterPlan;
    }
}
