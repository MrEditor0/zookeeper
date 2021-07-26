package com.haowq.watcher.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: haowq
 * @Date: 2021/7/19 19:39
 * @Description:
 */
public class WatchCallback implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {
    ZooKeeper zk;
    String threadName;
    String lockName;
    CountDownLatch cc = new CountDownLatch(1);
    String pathName;

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public CountDownLatch getCc() {
        return cc;
    }

    public void setCc(CountDownLatch cc) {
        this.cc = cc;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    //创建临时有序节点
    public void tryLock() {
        try {
            zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, threadName);
            cc.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getRootData() throws KeeperException, InterruptedException {
        byte[] data = zk.getData("/", false, new Stat());
        System.out.println(new String(data));
    }

    public void unLock() {
        try {
            zk.delete("/" + lockName, -1);
            System.out.println(lockName + " delete......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    //child callback
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        //获取根目录下的序列，排序，查看自己的index,然后watch前一个序列节点
        if (children == null) {
            System.out.println(ctx.toString() + "list null");
        } else {
            try {
                Collections.sort(children);
                int i = children.indexOf(lockName);
                if (i < 1) {
                    System.out.println(threadName + " i am first...");
                    zk.setData("/", threadName.getBytes(), -1);
                    cc.countDown();
                } else {
                    System.out.println(threadName + " watch " + children.get(i - 1));
                    //上一个节点是否存在，不存在则出发NodeDeleted 事件，重新getChildren
                    zk.exists("/"+children.get(i - 1), this);
                }

            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //create callback
    public void processResult(int rc, String path, Object ctx, String name) {

        System.out.println(ctx.toString() + " create path: " + path + "-" + name);
        lockName = name.substring(1);
        //不能直接在路径下加锁，会造成死锁，每个节点应该watch前一个序列
        zk.getChildren("/", false, this, ctx);
    }

    public void process(WatchedEvent event) {
        Event.EventType type = event.getType();
        switch (type) {
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren("/", false, this, "dsf");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            default:
                break;
        }

    }


    //exist callback
    public void processResult(int rc, String path, Object ctx, Stat stat) {


    }
}
