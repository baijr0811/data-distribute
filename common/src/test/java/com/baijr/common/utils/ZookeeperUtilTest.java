package com.baijr.common.utils;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;

public class ZookeeperUtilTest {

    @Test
    public void TestCreateNode() throws KeeperException, InterruptedException {

        if (ZookeeperUtil.getZookeeper().exists("/node", false) == null) {

            /**
             * 参数一：路径地址
             * 参数二：想要保存的数据，需要转换成字节数组
             * 参数三：ACL访问控制列表（Access control list）,
             *      参数类型为ArrayList<ACL>，Ids接口提供了一些默认的值可以调用。
             *      OPEN_ACL_UNSAFE     This is a completely open ACL
             *                          这是一个完全开放的ACL，不安全
             *      CREATOR_ALL_ACL     This ACL gives the
             *                           creators authentication id's all permissions.
             *                          这个ACL赋予那些授权了的用户具备权限
             *      READ_ACL_UNSAFE     This ACL gives the world the ability to read.
             *                          这个ACL赋予用户读的权限，也就是获取数据之类的权限。
             * 参数四：创建的节点类型。枚举值CreateMode
             *      PERSISTENT (0, false, false)
             *      PERSISTENT_SEQUENTIAL (2, false, true)
             *          这两个类型创建的都是持久型类型节点，回话结束之后不会自动删除。
             *          区别在于，第二个类型所创建的节点名后会有一个单调递增的数值
             *      EPHEMERAL (1, true, false)
             *      EPHEMERAL_SEQUENTIAL (3, true, true)
             *          这两个类型所创建的是临时型类型节点，在回话结束之后，自动删除。
             *          区别在于，第二个类型所创建的临时型节点名后面会有一个单调递增的数值。
             * 最后create()方法的返回值是创建的节点的实际路径
             */
            ZookeeperUtil.getZookeeper().create("/node", "node".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);


            /*  查看/node节点数据，这里应该输出"conan"
             *  参数一：获取节点的路径
             *  参数二：说明是否需要观察该节点，设置为true，则设定共享默认的观察器
             *  参数三：stat类，保存节点的信息。例如数据版本信息，创建时间，修改时间等信息
             */
            System.out.println("查看/node节点数据:get /node => "
                    + new String(ZookeeperUtil.getZookeeper().getData("/node", false, null)));


            /**
             * 查看根节点
             * 在此查看根节点的值，这里应该输出上面所创建的/node节点
             */
            System.out.println("查看根节点:ls / => " + ZookeeperUtil.getZookeeper().getChildren("/", true));
        }


    }


    @Test
    public void TestChildNode() throws KeeperException, InterruptedException {
        // 创建一个子目录节点
        if (ZookeeperUtil.getZookeeper().exists("/node/sub1", true) == null) {
            ZookeeperUtil.getZookeeper().create("/node/sub1", "sub1".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("创建一个子目录节点:create /node/sub1 sub1");
            // 查看node节点
            System.out.println("查看node节点:ls /node => "
                    + ZookeeperUtil.getZookeeper().getChildren("/node", true));
        }
    }


    @Test
    public void TestUpdateNode() throws KeeperException, InterruptedException {
        // 创建一个子目录节点
        if (ZookeeperUtil.getZookeeper().exists("/node/sub1", true) == null) {
            ZookeeperUtil.getZookeeper().create("/node/sub1", "sub1".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("创建一个子目录节点:create /node/sub1 sub1");
            // 查看node节点
            System.out.println("查看node节点:ls /node => "
                    + ZookeeperUtil.getZookeeper().getChildren("/node", true));
        }
    }


}



