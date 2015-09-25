package org.test.grizzly;

import java.io.IOException;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class ClientFilter extends BaseFilter {

	@Override
	public NextAction handleRead(FilterChainContext ctx) throws IOException {
		final Object serverResponse = ctx.getMessage();
		System.out.println("server echo:" + serverResponse);

		return ctx.getStopAction();
	}

}
