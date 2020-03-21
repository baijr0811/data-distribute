package com.baijr.common.utils;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class TestWatcher implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("TaskWatcher event " + watchedEvent.getPath() + " " + watchedEvent.getType() + " " + watchedEvent.getState());
    }
}
