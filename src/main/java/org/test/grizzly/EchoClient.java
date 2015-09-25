package org.test.grizzly;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.test.grizzly.entity.TestEntity;
import org.test.grizzly.filter.ObjectFilter;

public class EchoClient {

	public static void main(String[] args) {
		new EchoClient().start();
	}

	private void start() {
		Connection<String> connection = null;

		FilterChainBuilder filterChainBuilder = FilterChainBuilder.stateless();
		filterChainBuilder.add(new TransportFilter());
		filterChainBuilder.add(new ObjectFilter());
		filterChainBuilder.add(new ClientFilter());

		TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance().build();
		transport.setProcessor(filterChainBuilder.build());

		try {
			transport.start();
			GrizzlyFuture<Connection> future = transport.connect("127.0.0.1", 9999);
			connection = future.get(10, TimeUnit.SECONDS);
			assert connection != null;
			TestEntity testEntity = new TestEntity();
			testEntity.setA("a");
			testEntity.setB("b");
			testEntity.setIa(1);
			connection.write(testEntity);

			TestEntity testEntity1 = new TestEntity();
			testEntity.setA("aaaaa");
			testEntity.setB("bbbbb");
			testEntity.setIa(11111);
			connection.write(testEntity1);

			TestEntity testEntity2 = new TestEntity();
			testEntity.setA("aaaaa");
			testEntity.setB("bbbbb");
			testEntity.setIa(11111);
			Map<String, Integer> valueMap = new HashMap<String, Integer>();
			valueMap.put("1", Integer.valueOf(2));
			testEntity.setValueMap(valueMap);

			connection.write(testEntity2);

			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			transport.shutdown();
		}
	}
}
