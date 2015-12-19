package com.efeiyi.ec.courier.grasp.service.impl;

import com.efeiyi.ec.courier.grasp.service.ThreadManager;
import com.efeiyi.ec.courier.grasp.thread.Helper;
import com.efeiyi.ec.courier.organization.util.OrganizationConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Service
public class ThreadManagerImpl implements ThreadManager {
    private static Logger log = Logger.getLogger(ThreadManagerImpl.class);

    public void startWork() throws Exception{
        log.info("开始创建线程池");
        /*ExecutorService pool = Executors.newFixedThreadPool(OrganizationConst.THREAD_POOL_CORE_COUNT);
        int beginNum =1,endNum =40;
        for(int i = 1; i <= OrganizationConst.THREAD_POOL_CORE_COUNT; i++) {
            pool.execute(new Thread(new Helper(beginNum,endNum)));
            beginNum = endNum+1;
            if (i== OrganizationConst.THREAD_POOL_CORE_COUNT-1){
                endNum=373;
            }else {
                endNum = beginNum+40;
            }

        }*/
      /*  ExecutorService pool = Executors.newFixedThreadPool(OrganizationConst.THREAD_POOL_CORE_COUNT);
        int beginNum =1,endNum =10;
        for(int i = 1; i <= OrganizationConst.THREAD_POOL_CORE_COUNT; i++) {
            pool.execute(new Thread(new Helper(beginNum,endNum)));
            beginNum = endNum+1;
            endNum = beginNum+10;
        }*/


        ExecutorService pool = Executors.newFixedThreadPool(1);
        int beginNum =101,endNum =200;
        pool.execute(new Thread(new Helper(beginNum,endNum)));
        log.info("创建线程池完成");
        pool.shutdown();
        log.info("关闭线程池");
    }
}
