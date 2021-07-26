package com.haowq.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 14:13
 * @Description: 监听节点变化的Watcher
 */
public class DataWatcher implements Watcher {
    public void process(WatchedEvent event) {
        System.out.println("==================DataWatcher start===============");
        System.out.println("DataWatcher state :" + event.getState().name());
        System.out.println("DataWatcher type :" + event.getType().name());
        System.out.println("DataWatcher path :" + event.getPath());
        System.out.println("==================DataWatcher end===============");
    }
}
