package com.haowq.watcher.lock;

import com.haowq.watcher.autoconfig.WatcherCallBack;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 19:32
 * @Description:
 */
public class TestLock {
    ZooKeeper zk;
    ZKConf zkConf;
    DefaultWatch defaultWatch;

    @Before
    public void conn() {
        zkConf = new ZKConf();
        zkConf.setAddress("192.168.150.132:2181/testLock");
        zkConf.setSessionTime(1000);
        defaultWatch = new DefaultWatch();
        ZKUtils.setConf(zkConf);
        ZKUtils.setWatch(defaultWatch);
        zk = ZKUtils.getZK();
    }

    @After
    public void close() {
        ZKUtils.closeZK();
    }


    @Test
    public void testLock() {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    WatchCallback watchCallback = new WatchCallback();
                    watchCallback.setZk(zk);
                    String name = Thread.currentThread().getName();
                    watchCallback.setThreadName(name);

                    try {
                        //tryLock
                        watchCallback.tryLock();
                        System.out.println(name + " at work");
                        watchCallback.getRootData();
                        //Thread.sleep(1000);
                        //unLock
                        watchCallback.unLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        //不加，程序会直接结束
        while (true){}

    }
}
