package com.efeiyi.jh.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
@Entity
@Table(name = "virtual_order_plan")
public class VirtualOrderPlan extends VirtualPlan{

    private Integer orderCountLimitFloor;
    private Integer orderCountLimitCeil;
    private List<VirtualProductModel> virtualProductModelList;
    private VirtualUserPlan virtualUserPlan;
    private Date peakTime;//均值小时
    private Integer standardDeviation; //标准差小时
    private List<VirtualPurchaseOrder> vitualPurchaseOrderList;

    @Column(name = "count_limit_floor")
    public Integer getOrderCountLimitFloor() {
        return orderCountLimitFloor;
    }

    public void setOrderCountLimitFloor(Integer orderCountLimitFloor) {
        this.orderCountLimitFloor = orderCountLimitFloor;
    }
    @Column(name = "count_limit_ceil")
    public Integer getOrderCountLimitCeil() {
        return orderCountLimitCeil;
    }

    public void setOrderCountLimitCeil(Integer orderCountLimitCeil) {
        this.orderCountLimitCeil = orderCountLimitCeil;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "virtualOrderPlan")
    public List<VirtualProductModel> getVirtualProductModelList() {
        return virtualProductModelList;
    }

    public void setVirtualProductModelList(List<VirtualProductModel> virtualProductModelList) {
        this.virtualProductModelList = virtualProductModelList;
    }

    @Column(name = "peak_time")
    public Date getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(Date peakTime) {
        this.peakTime = peakTime;
    }

    @Column(name = "standard_deviation")
    public Integer getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Integer standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_user_plan_id")
    public VirtualUserPlan getVirtualUserPlan() {
        return virtualUserPlan;
    }

    public void setVirtualUserPlan(VirtualUserPlan virtualUserPlan) {
        this.virtualUserPlan = virtualUserPlan;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "virtualOrderPlan")
    public List<VirtualPurchaseOrder> getVitualPurchaseOrderList() {
        return vitualPurchaseOrderList;
    }

    public void setVitualPurchaseOrderList(List<VirtualPurchaseOrder> vitualPurchaseOrderList) {
        this.vitualPurchaseOrderList = vitualPurchaseOrderList;
    }
}
