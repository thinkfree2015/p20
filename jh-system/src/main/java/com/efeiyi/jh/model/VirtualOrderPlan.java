package com.efeiyi.jh.model;

import com.efeiyi.ec.product.model.ProductModel;

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
    private Date peakTime;
    private Integer standardDeviation; //标准差

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
    public List<VirtualProductModel> getProductModelList() {
        return virtualProductModelList;
    }

    public void setProductModelList(List<VirtualProductModel> virtualProductModelList) {
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
}
