package com.haowq.watcher.lock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 19:28
 * @Description:
 */
public class DefaultWatch implements Watcher {
    CountDownLatch init;

    public CountDownLatch getInit() {
        return init;
    }

    public void setInit(CountDownLatch init) {
        this.init = init;
    }

    public void process(WatchedEvent event) {
        switch (event.getState()) {
            case Disconnected:
                System.out.println("Disconnected ....c...new....");
                init = new CountDownLatch(1);
                break;
            case SyncConnected:
                System.out.println("Connected ....c...ok....");
                init.countDown();
                break;
        }
    }
}
