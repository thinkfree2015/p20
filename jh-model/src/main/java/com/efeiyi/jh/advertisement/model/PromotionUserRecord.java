package com.efeiyi.jh.advertisement.model;

import com.efeiyi.ec.organization.model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/4.
 */
@Entity
@Table(name = "promotion_user_record")
public class PromotionUserRecord {

    private String id;
    private PromotionPlan latestPromotionPlan;//最后一次通过营销渠道进入的plan
    private PromotionPlan promotionPlan;//最初注册或首次通过营销渠道进入时的plan
    private User user;
    private Date rdEndDate;

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "rd_end_date")
    public Date getRdEndDate() {
        return rdEndDate;
    }

    public void setRdEndDate(Date rdEndDate) {
        this.rdEndDate = rdEndDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_promotion_plan_id")
    public PromotionPlan getLatestPromotionPlan() {
        return latestPromotionPlan;
    }

    public void setLatestPromotionPlan(PromotionPlan latestPromotionPlan) {
        this.latestPromotionPlan = latestPromotionPlan;
    }
}
