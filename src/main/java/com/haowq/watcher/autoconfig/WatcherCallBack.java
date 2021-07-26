package com.haowq.watcher.autoconfig;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 16:06
 * @Description:
 */
public class WatcherCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {
    ZooKeeper zk;

    CountDownLatch cc = new CountDownLatch(1);
    MyConfig myConf = new MyConfig();

    //业务方法
    public void await() {
        zk.exists("/AppConf", this, this, "业务方法，判断节点是否存在");
        //阻塞，不然线程直接结束了
        try {
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if(stat != null){   //如果节点存在
            zk.getData("/AppConf",this,this,"dataCallback");
        }
    }

    //Watcher方法
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated: //刚开始无节点
                zk.getData("/AppConf",this,this,"dataCallback");
                break;
            case NodeDeleted:     //当节点被删除了，默认处理，提高容忍性
                myConf.config = "";
                cc.countDown();
                break;
            case NodeDataChanged:   //节点数据改变，获取数据
                zk.getData("/AppConf",this,this,"dataCallback");
                break;
            case NodeChildrenChanged:
                break;
            default:
                break;
        }
    }

    //dataCallback
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        if(data != null){
            String conf = new String(data);
            myConf.config = conf;
            cc.countDown();
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public CountDownLatch getCc() {
        return cc;
    }

    public void setCc(CountDownLatch cc) {
        this.cc = cc;
    }

    public MyConfig getMyConf() {
        return myConf;
    }

    public void setMyConf(MyConfig myConf) {
        this.myConf = myConf;
    }
}
