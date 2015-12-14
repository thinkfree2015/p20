package com.efeiyi.jh.util;

import com.efeiyi.ec.purchase.model.PurchaseOrder;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Reactor implements Runnable {
    private PurchaseOrder purchaseOrder;
    private String status;
    private SessionFactory sessionFactory = ((SessionFactory) ApplicationContextUtil.getApplicationContext().getBean("scheduleSessionFactory"));
    private CountDownLatch countDownLatch;

    public Reactor(PurchaseOrder purchaseOrder, String status,CountDownLatch countDownLatch) {
        this.purchaseOrder = purchaseOrder;
        this.status = status;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        post2Kuaidi100();

        Session session = sessionFactory.openSession();
        session.setCacheMode(CacheMode.IGNORE);
        PurchaseOrder purchaseOrder = (PurchaseOrder)session.get(PurchaseOrder.class,this.purchaseOrder.getId());

        countDownLatch.countDown();
    }

    private void post2Kuaidi100() {
    }

}
