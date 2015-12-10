package com.efeiyi.ec.courier.grasp.thread;


/**
 * Created by Administrator on 2015/12/10.
 *
 */
public class Helper implements  Runnable {
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

    }
}
