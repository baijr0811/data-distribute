package com.baijr.common.utils;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;


public class ZookeeperUtil {

    private static volatile ZooKeeper zk;
    static String hosts = "112.124.116.226:2181,112.124.116.226:2182,112.124.116.226:2183";
    private static int sessionTimeout = 50000;

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

}
