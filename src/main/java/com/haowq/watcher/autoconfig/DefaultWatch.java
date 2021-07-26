package com.haowq.watcher.autoconfig;

import com.haowq.watcher.WatcherTest;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 15:10
 * @Description:
 */
public class DefaultWatch implements Watcher {
    CountDownLatch cc;
    public void setCC(CountDownLatch cc){
        this.cc = cc;
    }

    //判断连接状态  连接成功才放行
    public void process(WatchedEvent event) {
        switch (event.getState()) {
            case AuthFailed:
                break;
            case Expired:
                break;
            case Disconnected:
                break;
            case SyncConnected:
                cc.countDown();
                break;
            case ConnectedReadOnly:
                break;
            case SaslAuthenticated:
                break;
            case Unknown:
                break;
            case NoSyncConnected:
                break;
            default:
                break;
        }

    }
}
