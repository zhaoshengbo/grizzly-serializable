package org.test.grizzly.filter;

import java.io.Serializable;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.AbstractCodecFilter;

public class ObjectFilter extends AbstractCodecFilter<Buffer, Serializable> {

	public ObjectFilter() {
		super(new ObjectDecoder(), new ObjectEncoder());
	}

}
