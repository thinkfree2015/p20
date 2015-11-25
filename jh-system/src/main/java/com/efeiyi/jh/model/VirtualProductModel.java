package com.efeiyi.jh.model;

import com.efeiyi.ec.product.model.ProductModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/11/25.
 */
@Entity
@Table(name = "virtual_product_model")
public class VirtualProductModel {

    private String id;
    private ProductModel productModel;
    private VirtualOrderPlan virtualOrderPlan;

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
    @JoinColumn(name = "product_model_id")
    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_order_plan_id")
    public VirtualOrderPlan getVirtualOrderPlan() {
        return virtualOrderPlan;
    }

    public void setVirtualOrderPlan(VirtualOrderPlan virtualOrderPlan) {
        this.virtualOrderPlan = virtualOrderPlan;
    }
}
