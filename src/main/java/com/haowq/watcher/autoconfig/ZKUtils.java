package com.haowq.watcher.autoconfig;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 15:08
 * @Description:
 */
public class ZKUtils {
    private static final String address = "192.168.150.132:2181/autoConfig";
    private static ZooKeeper zk;

    private static DefaultWatch wacth = new DefaultWatch();
    private static CountDownLatch cc = new CountDownLatch(1);

    public static ZooKeeper getZK(){

        try {
            zk = new ZooKeeper(address,1000,wacth);
            wacth.setCC(cc);
            cc.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zk;
    }
}
