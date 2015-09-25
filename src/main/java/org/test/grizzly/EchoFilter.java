package org.test.grizzly;

import java.io.IOException;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class EchoFilter extends BaseFilter {

	@Override
	public NextAction handleRead(FilterChainContext ctx) throws IOException {
		final Object address = ctx.getAddress();
		final Object message = ctx.getMessage();

		ctx.write(address, message, null);

		return ctx.getStopAction();
	}

}
