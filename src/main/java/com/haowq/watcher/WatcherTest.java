package com.haowq.watcher;

import javafx.scene.chart.PieChart;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 14:05
 * @Description:
 */
public class WatcherTest {
    private static final String CONNECT_STRING = "192.168.150.132:2181";

    public static void main(String[] args) {
        DefaultWatcher defaultWatcher = new DefaultWatcher();
        ChildrenWatcher childrenWatcher = new ChildrenWatcher();
        DataWatcher dataWatcher = new DataWatcher();

        try {
            ZooKeeper zk = new ZooKeeper(CONNECT_STRING, 100000, defaultWatcher);
            Stat exists = zk.exists("/testWatcher", false);
            if(null != exists){
                zk.getChildren("/testWatcher",childrenWatcher);
                zk.getData("/testWatcher",dataWatcher,null);
                TimeUnit.SECONDS.sleep(1000000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
