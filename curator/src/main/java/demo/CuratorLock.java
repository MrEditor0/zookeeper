package demo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowq
 * @Date: 2021/7/26 15:57
 * @Description:
 */
public class CuratorLock {
    CuratorFramework client;
    String LOCK_PATH = "/ProcessMutex";
    @Before
    public void conn(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("192.168.150.132:2181/testLock", retryPolicy);
        client.start();
        System.out.println("zk 启动成功......");
    }

    @After
    public void close(){
        client.close();
        System.out.println("zk 连接关闭......");
    }

    Long maxWait = 10000L;


    @Test
    public void testLock() throws Exception {
        System.out.println("任务开始执行-----");
        //TODO: 具体业务代码
        InterProcessMutex lock = new InterProcessMutex(client, LOCK_PATH);

        //模拟多线程抢锁
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    //抢锁
                    if(lock.acquire(maxWait, TimeUnit.MILLISECONDS)){
                        try{
                            System.out.println(Thread.currentThread().getName()+"---抢到锁");
                            Thread.sleep(1000);
                        }finally {
                            lock.release();
                            System.out.println(Thread.currentThread().getName()+"---释放锁");
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }).start();
        }


        System.out.println("任务执行完毕----");

        while (true) {
        }
    }


    @Test
    public  void testCreate() {
        System.out.println("任务开始执行-----");
        try {
            String path = "/firstPath";
            client.create().forPath(path);
            System.out.println("create :" + path);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("任务执行完毕----");

        while (true) {
        }
    }

    @Test
    public void testDemo(){
        System.out.println("任务开始执行-----");
        //TODO: 具体业务代码

        System.out.println("任务执行完毕----");
        while (true) {
        }
    }
}
