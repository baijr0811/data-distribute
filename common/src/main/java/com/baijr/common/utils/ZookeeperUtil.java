package com.baijr.common.utils;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;


public class ZookeeperUtil {

    private static volatile ZooKeeper zk;
    static String hosts = "127.0.0.1:2181";
    private static int sessionTimeout = 10000;

    public static void init(TestWatcher watcher) {
        if (zk == null) {
            synchronized (ZookeeperUtil.class) {
                if (zk == null) {
                    try {
                        zk = new ZooKeeper(hosts, sessionTimeout, watcher);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ZooKeeper getZookeeper() {
        return zk;
    }

    public static void closeConnect() {
        try {
            if (zk != null) {
                zk.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
