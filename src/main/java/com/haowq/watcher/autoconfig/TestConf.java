package com.haowq.watcher.autoconfig;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 15:52
 * @Description:
 */
public class TestConf {
    ZooKeeper zk;

    @Before
    public void conn(){
        zk = ZKUtils.getZK();
    }

    @After
    public void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getConf(){

        WatcherCallBack watcherCallBack = new WatcherCallBack();
        watcherCallBack.zk = zk;
        MyConfig myConf = new MyConfig();
        watcherCallBack.myConf = myConf;

        watcherCallBack.await();

        while(true){
            if(myConf.config.equals("")){
                System.out.println("conf diu le ....");
                watcherCallBack.await();
            }
            else{
                System.out.println(myConf.config);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
