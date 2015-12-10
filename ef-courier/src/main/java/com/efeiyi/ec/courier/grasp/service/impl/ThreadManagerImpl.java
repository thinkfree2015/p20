package com.efeiyi.ec.courier.grasp.service.impl;

import com.efeiyi.ec.courier.grasp.service.ThreadManager;
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
        ExecutorService pool = Executors.newFixedThreadPool(OrganizationConst.THREAD_POOL_CORE_COUNT);
        for(int i = 1; i <= OrganizationConst.THREAD_POOL_CORE_COUNT; i++) {
            //pool.execute();
        }
        pool.shutdown();
    }
}
