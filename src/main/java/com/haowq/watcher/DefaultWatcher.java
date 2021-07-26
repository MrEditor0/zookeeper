package com.haowq.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 14:07
 * @Description: 默认watcher
 */
public class DefaultWatcher implements Watcher {

    public void process(WatchedEvent event) {
        System.out.println("==================DefaultWatcher start===============");
        System.out.println("DefaultWatcher state :" + event.getState().name());
        System.out.println("DefaultWatcher type :" + event.getType().name());
        System.out.println("DefaultWatcher path :" + event.getPath());
        System.out.println("==================DefaultWatcher end===============");

    }
}
