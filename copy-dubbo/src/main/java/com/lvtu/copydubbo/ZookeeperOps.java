package com.lvtu.copydubbo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

public class ZookeeperOps {

	private static final int SESSION_TIMEOUT = 30000;

	ZooKeeper sourceZk;

	ZooKeeper destZk;

	FilterRule filterRule;

	public ZookeeperOps(FilterRule filterRule) {
		this.filterRule = filterRule;
	}

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		if (args.length < 2) {
			System.out.println("input help:");
			System.out.println("args[0]:sourceZkConnection,IP:PORT");
			System.out.println("args[1]:destZkConnection,IP:PORT");
			return;
		}
		String sourceZkCnn = args[0]; // ip:port地址
		String destZkCnn = args[1];

		/* 过滤的关键字 */
		FilterRule filter = new FilterRule();
		filter.setBlackListAppNames(Arrays.copyOfRange(args, 2, args.length));
		filter.addBlackLists("consumers");
		filter.addBlackLists("routers");
		filter.addBlackLists("configurators");

		ZookeeperOps zops = new ZookeeperOps(filter);
		zops.createZKInstance(sourceZkCnn, destZkCnn);

		zops.copyNodes(null);

		zops.closeZk();
	}

	/**
	 * 创建源和目的zkserver client实例
	 * 
	 * @param sourceZkCnn
	 * @param destZkCnn
	 * @throws IOException
	 */
	public void createZKInstance(String sourceZkCnn, String destZkCnn) throws IOException {

		sourceZk = new ZooKeeper(sourceZkCnn, SESSION_TIMEOUT, new Watcher() { // zkclient
																				// 事件监听
					public void process(org.apache.zookeeper.WatchedEvent event) {
						// System.out.println("sourceZk:"
						// +event.getType().toString());
					}
				});

		destZk = new ZooKeeper(destZkCnn, SESSION_TIMEOUT, new Watcher() {
			public void process(org.apache.zookeeper.WatchedEvent event) {
				// System.out.println("destZk:" + event.getType().toString());
			}
		});
	}

	@Test
	public void testDump() throws KeeperException, InterruptedException {
		System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		sourceZk.create("/zoo2/zoo/zo/zo", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		/*
		 * System.out.println("\n2. 查看是否创建成功： "); System.out.println(new
		 * String(zk.getData("/zoo2/zoo/zo/zo", false, null)));
		 * System.out.println("\n3. 修改节点数据 "); zk.setData("/zoo2",
		 * "shenlan211314".getBytes(), -1);
		 * System.out.println("\n4. 查看是否修改成功： "); System.out.println(new
		 * String(zk.getData("/zoo2", false, null)));
		 * System.out.println("\n5. 删除节点 "); zk.delete("/zoo2", -1);
		 * System.out.println("\n6. 查看节点是否被删除： "); System.out.println(" 节点状态： ["
		 * + zk.exists("/zoo2", false) + "]");
		 */
	}

	public void testDumpDubbo() throws KeeperException, InterruptedException {
		List<String> services = sourceZk.getChildren("/dubbo", null);
		List<String> retData = new ArrayList<String>();
		for (String dir : services) {
			List<String> providers = sourceZk.getChildren("/dubbo/" + dir, null);
			if (null != providers && providers.size() > 0) {
				for (String provider : providers) {
					List<String> targets = sourceZk.getChildren("/dubbo/" + dir + "/" + provider, null);
					for (String target : targets) {
						retData.add("/dubbo/" + dir + "/" + provider + "/" + target);
					}
				}
			}
		}
		System.out.println("size:" + retData.size());
	}

	/**
	 * 将源zkserver dubbo节点数据 拷贝到目标zkServer中
	 * 
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void copyDubbo() throws KeeperException, InterruptedException {
		List<String> services = sourceZk.getChildren("/dubbo", null);
		// destZk.delete("/dubbo", -1);
		destZk.create("/dubbo", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("create /dubbo");
		List<String> retData = new ArrayList<String>();
		for (String dir : services) {
			List<String> providers = sourceZk.getChildren("/dubbo/" + dir, null);
			destZk.create("/dubbo/" + dir, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create " + "/dubbo/" + dir);
			if (null != providers && providers.size() > 0) {
				for (String provider : providers) {
					List<String> targets = sourceZk.getChildren("/dubbo/" + dir + "/" + provider, null);
					destZk.create("/dubbo/" + dir + "/" + provider, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					System.out.println("/dubbo/" + dir + "/" + provider);
					for (String target : targets) {
						retData.add("/dubbo/" + dir + "/" + provider + "/" + target);
						destZk.create("/dubbo/" + dir + "/" + provider + "/" + target, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
						System.out.println("/dubbo/" + dir + "/" + provider + "/" + target);
					}
				}
			}
		}
		/*
		 * for (String target : retData) { System.out.println(target); }
		 */
		System.out.println("copy server size:" + retData.size());
	}

	public void closeZk() throws InterruptedException {
		sourceZk.close();
		destZk.close();
	}

	public void copyNodes(List<String> nodes) throws InterruptedException, KeeperException {
		List<String> interfaces = getDubbo(this.sourceZk); // 获取dubbo下子节点，即接口
		String dubboNode = createNode(this.destZk, "/dubbo");
	}

	/**
	 * 创建zk节点
	 * 
	 * @param zk
	 * @param node
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	private String createNode(ZooKeeper zk, String node) throws KeeperException, InterruptedException {
		System.err.println(node);
		return zk.create(node, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	private List<String> getDubbo(ZooKeeper zk) throws KeeperException, InterruptedException {
		return zk.getChildren("/dubbo", null);
	}

}
