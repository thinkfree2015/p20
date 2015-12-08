package com.efeiyi.jh.dao.PromotionPlan.hibernate;

import com.efeiyi.ec.organization.model.User;
import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.efeiyi.jh.advertisement.model.PromotionPlan;
import com.efeiyi.jh.dao.PromotionPlan.PromotionPlanDao;
import com.ming800.core.taglib.PageEntity;
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

    @Override
    public List<User> getZCLInfomation(PromotionPlan promotionPlan, PageEntity pageEntity) throws Exception {
        String hql = "select u from User u, PromotionUserRecord pur where pur.user = u and pur.promotionPlan = :promotionPlan";
        Query query = this.getSession().createQuery(hql)
                .setParameter("promotionPlan", promotionPlan)
                .setFirstResult(pageEntity.getrIndex())
                .setMaxResults(pageEntity.getSize());
        return query.list();
    }

    @Override
    public List<PurchaseOrder> getDDLInfomation(PromotionPlan promotionPlan, PageEntity pageEntity) throws Exception {
        String hql = "select po from PurchaseOrder po, PromotionPurchaseRecord ppr where ppr.purchaseOrder = po and ppr.promotionPlan = :promotionPlan";
        Query query = this.getSession().createQuery(hql)
                .setParameter("promotionPlan", promotionPlan)
                .setFirstResult(pageEntity.getrIndex())
                .setMaxResults(pageEntity.getSize());
        return query.list();
    }

}
