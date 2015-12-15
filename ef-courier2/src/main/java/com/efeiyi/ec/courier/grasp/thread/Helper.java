package com.efeiyi.ec.courier.grasp.thread;


import com.efeiyi.ec.courier.grasp.service.impl.ContentDataManagerImpl;
import com.efeiyi.ec.courier.organization.util.ContextUtils;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
public class Helper implements  Runnable {
    private static Logger log = Logger.getLogger(Helper.class);
    private int beginNum;
    private int endNum;

    public Helper(int beginNum, int endNum) {
        this.beginNum = beginNum;
        this.endNum = endNum;
    }

    public int getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(int beginNum) {
        this.beginNum = beginNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    @Override
    public void run() {
     System.out.println("beginNum:"+beginNum+"   "+"endNum:"+endNum);
        exeBatchInsert();
    }

    private void exeBatchInsert(){
        log.info("begin exeBatchInsert:"+beginNum+"-->"+endNum);
        try{
            ((ContentDataManagerImpl)ContextUtils.getBean("contentDataManagerImpl")).findCityList(beginNum, endNum);
        }catch(Exception e){
            log.error(e);
        }
        log.info("end exeBatchInsert:"+beginNum+"-->"+endNum);
    }
}
