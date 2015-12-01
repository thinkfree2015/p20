package test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/12/1.
 */
public class Test {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new Task();
        Stopper stopper = new Stopper(timerTask,timer);
        timer.schedule(timerTask, 10000);
        timer.schedule(stopper, 5000);

    }
}


class Task extends TimerTask {

    @Override
    public void run() {
        System.out.println("task executing.....");
    }

    @Override
    public boolean cancel() {
        System.out.println("task canceling...");
        return super.cancel();
    }
}

class Stopper extends TimerTask {

    TimerTask timerTask;
    Timer timer;

    public Stopper(TimerTask timerTask,Timer timer) {
        this.timerTask = timerTask;
        this.timer = timer;
    }

    @Override
    public void run() {
        boolean b1 = this.timerTask.cancel();
        boolean b2 = this.cancel();
        timer.cancel();
        System.out.println(b1 + "--" + b2);
    }

    @Override
    public boolean cancel() {
        System.out.println("stopper canceling.......");
        return super.cancel();
    }
}