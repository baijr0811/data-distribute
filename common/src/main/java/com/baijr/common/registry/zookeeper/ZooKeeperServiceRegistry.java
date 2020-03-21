package com.baijr.common.registry.zookeeper;

import com.baijr.common.registry.ServiceRegistry;
import com.baijr.common.utils.ZookeeperUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于 ZooKeeper 的服务注册接口实现
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);


    @Override
    public void register(String serviceName, String serviceAddress) {
        // 创建 registry 节点（持久）
        String registryPath = Constant.ZK_REGISTRY_PATH;
        try {
            if (null == ZookeeperUtil.getZookeeper().exists(registryPath, false)) {
                ZookeeperUtil.getZookeeper().create(registryPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                LOGGER.debug("create registry node: {}", registryPath);
            }
            // 创建 service 节点（持久）
            String servicePath = registryPath + "/" + serviceName;
            if (null == ZookeeperUtil.getZookeeper().exists(servicePath, false)) {
                ZookeeperUtil.getZookeeper().create(servicePath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                LOGGER.debug("create service node: {}", servicePath);
            }

            // 创建 address 节点（临时）
            String addressPath = servicePath + "/address-";
            ZookeeperUtil.getZookeeper().create(addressPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create address node: {}", addressPath);

        } catch (KeeperException e) {
            LOGGER.error("KeeperException", e);
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
        }

    }
}