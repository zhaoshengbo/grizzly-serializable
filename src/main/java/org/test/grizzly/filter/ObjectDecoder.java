package org.test.grizzly.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class ObjectDecoder extends AbstractTransformer<Buffer, Serializable> {

	@Override
	public String getName() {
		return "object decoder";
	}

	@Override
	public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
		return (input != null) && input.hasRemaining();
	}

	@Override
	protected TransformationResult<Buffer, Serializable> transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
		Integer objectSize = (Integer) storage.getAttributes().getAttribute("object.size");
		if (objectSize == null) {
			if (input.remaining() < 4) {
				return TransformationResult.createIncompletedResult(input);
			}
			objectSize = input.getInt();
			storage.getAttributes().setAttribute("object.size", objectSize);
		}
		if (input.remaining() < objectSize) {
			return TransformationResult.createIncompletedResult(input);
		}
		int limit = input.limit();
		input.limit(input.position() + objectSize);
		Serializable object = this.transfer(input);
		input.position(input.limit());
		input.limit(limit);

		storage.getAttributes().removeAttribute("object.size");
		return TransformationResult.createCompletedResult(object, input);
	}

	private Serializable transfer(Buffer input) {
		int remaining = input.remaining();
		byte[] datas = new byte[remaining];
		input.get(datas);
		ByteArrayInputStream bais = new ByteArrayInputStream(datas);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable readObject = (Serializable) ois.readObject();

			return readObject;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
