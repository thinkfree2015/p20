package com.efeiyi.ec.courier.grasp.thread;


/**
 * Created by Administrator on 2015/12/10.
 *
 */
public class Helper implements  Runnable {
    private int beginNum;

    public int getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(int beginNum) {
        this.beginNum = beginNum;
    }

    public Helper(int beginNum) {
        this.beginNum=beginNum;
    }

    @Override
    public void run() {

    }
}
