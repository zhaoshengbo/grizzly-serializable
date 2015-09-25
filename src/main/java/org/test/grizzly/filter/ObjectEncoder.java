package org.test.grizzly.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.MemoryManager;

public class ObjectEncoder extends AbstractTransformer<Serializable, Buffer> {

	@Override
	public String getName() {
		return "object encoder";
	}

	@Override
	public boolean hasInputRemaining(AttributeStorage storage, Serializable input) {
		return input != null;
	}

	@Override
	protected TransformationResult<Serializable, Buffer> transformImpl(AttributeStorage storage, Serializable input) throws TransformationException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(input);
			oos.flush();
			byte[] datas = baos.toByteArray();
			Buffer buffer = this.getMemoryManager(storage).allocate(datas.length + 4);
			buffer.putInt(datas.length);
			buffer.put(datas);

			buffer.flip();
			buffer.allowBufferDispose(true);

			return TransformationResult.createCompletedResult(buffer, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private MemoryManager getMemoryManager(AttributeStorage storage) {
		if (storage instanceof Connection) {
			Connection connection = (Connection) storage;
			return connection.getMemoryManager();
		}
		return MemoryManager.DEFAULT_MEMORY_MANAGER;
	}

}
