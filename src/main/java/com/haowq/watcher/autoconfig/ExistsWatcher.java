package com.haowq.watcher.autoconfig;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 16:02
 * @Description:
 */
public class ExistsWatcher implements Watcher , AsyncCallback.StatCallback {
    public void process(WatchedEvent event) {

    }

    public void processResult(int rc, String path, Object ctx, Stat stat) {

    }
}
