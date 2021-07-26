package com.haowq.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 14:12
 * @Description: 监听子节点变化的Watcher
 */
public class ChildrenWatcher implements Watcher {

    public void process(WatchedEvent event) {
        System.out.println("==================ChildrenWatcher start===============");
        System.out.println("ChildrenWatcher state :" + event.getState().name());
        System.out.println("ChildrenWatcher type :" + event.getType().name());
        System.out.println("ChildrenWatcher path :" + event.getPath());
        System.out.println("==================ChildrenWatcher end===============");
    }
}
