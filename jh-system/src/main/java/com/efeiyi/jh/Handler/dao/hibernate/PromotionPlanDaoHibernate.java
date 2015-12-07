package com.efeiyi.jh.Handler.dao.hibernate;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.jh.Handler.dao.PromotionPlanDao;
import com.efeiyi.jh.advertisement.model.PromotionPlan;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 * PromotionPlanDao 实现类
 */

@SuppressWarnings("JpaQlInspection")
@Repository
public class PromotionPlanDaoHibernate implements PromotionPlanDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public String getDDL(PromotionPlan promotionPlan) throws Exception {
        /*String hql = "select count(po.id) from PurchaseOrder po, PromotionPurchaseRecord ppr where ppr.purchaseOrder = po and ppr.promotionPlan = :promotionPlan and po.status = :status";
        Query query = this.getSession().createQuery(hql)
                .setParameter("promotionPlan", promotionPlan)
                .setString("status", PurchaseOrder.ORDER_STATUS_FINISHED);*/
        String hql = "select count(po.id) from PurchaseOrder po, PromotionPurchaseRecord ppr where ppr.purchaseOrder = po and ppr.promotionPlan = :promotionPlan";
        Query query = this.getSession().createQuery(hql)
                .setParameter("promotionPlan", promotionPlan);
        List list = query.list();
        if (!list.isEmpty()){
            return list.get(0).toString();
        }
        return null;
    }

    @Override
    public String getZFE(PromotionPlan promotionPlan) throws Exception {
        /*String hql = "select sum(po.originalPrice) from PurchaseOrder po, PromotionPurchaseRecord ppr where ppr.purchaseOrder = po and ppr.promotionPlan = :promotionPlan and po.status = :status";
        Query query = this.getSession().createQuery(hql)
                .setParameter("promotionPlan", promotionPlan)
                .setString("status", PurchaseOrder.ORDER_STATUS_FINISHED);*/
        String hql = "select sum(po.originalPrice) from PurchaseOrder po, PromotionPurchaseRecord ppr where ppr.purchaseOrder = po and ppr.promotionPlan = :promotionPlan";
        Query query = this.getSession().createQuery(hql)
                .setParameter("promotionPlan", promotionPlan);
        List list = query.list();
        if (!list.isEmpty()){
            return list.get(0).toString();
        }
        return null;
    }

}
