package com.baijr.common.registry.zookeeper;

import com.baijr.common.registry.ServiceDiscovery;
import com.baijr.common.utils.ZookeeperUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 基于 ZooKeeper 的服务发现接口实现
 */
public class ZooKeeperServiceDiscovery implements ServiceDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);


    @Override
    public String discover(String name) {
        try {
            // 获取 service 节点
            String servicePath = Constant.ZK_REGISTRY_PATH + "/" + name;
            if (null == ZookeeperUtil.getZookeeper().exists(servicePath, false)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }
            List<String> addressList = ZookeeperUtil.getZookeeper().getChildren(servicePath, false);
            if (CollectionUtils.isEmpty(addressList)) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }
            // 获取 address 节点
            String address;
            int size = addressList.size();
            if (size == 1) {
                // 若只有一个地址，则获取该地址
                address = addressList.get(0);
                LOGGER.debug("get only address node: {}", address);
            } else {
                // 若存在多个地址，则随机获取一个地址
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("get random address node: {}", address);
            }
            // 获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            return new String(ZookeeperUtil.getZookeeper().getData(addressPath, false, null));
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        } finally {
            ZookeeperUtil.closeConnect();
        }
        return null;
    }

}