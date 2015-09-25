package org.test.grizzly;

import java.io.IOException;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.test.grizzly.filter.ObjectFilter;

public class EchoServer {

	public static void main(String[] args) {
		FilterChainBuilder filterChainBuilder = FilterChainBuilder.stateless();
		filterChainBuilder.add(new TransportFilter());

		filterChainBuilder.add(new ObjectFilter());
		filterChainBuilder.add(new EchoFilter());

		TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance().build();
		transport.setProcessor(filterChainBuilder.build());

		try {
			transport.bind(9999);
			transport.start();
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			transport.shutdown();
		}

	}
}
