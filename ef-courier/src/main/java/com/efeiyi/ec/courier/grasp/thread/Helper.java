package com.efeiyi.ec.courier.grasp.thread;

import com.ming800.core.base.dao.XdoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
@Service
public class Helper implements  Runnable {
    private int beginNum;
    @Autowired
    private XdoDao xdoDao;
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
