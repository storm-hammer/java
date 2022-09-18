package hr.fer.oprpp2.poruke;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class AckMessage extends Message {
	
	private long uuid;
	
	public AckMessage(long ordinal, long uuid) {
		super((byte)2, ordinal);
		this.uuid = uuid;
	}
	
	public static byte[] ackToBytes(AckMessage msg) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(msg.getMessageType());
			dos.writeLong(msg.getOrdinal());
			dos.writeLong(msg.getUuid());
			dos.close();
		} catch(IOException e) {	
		}
		return bos.toByteArray(); 
	}
	
	public static AckMessage bytesToAck(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(Arrays.copyOfRange(bytes, bytes.length-8, bytes.length));
		buffer.flip();
		long uuid = buffer.getLong();
		buffer.clear();
		buffer.put(Arrays.copyOfRange(bytes, 1, 9));
		buffer.flip();
		long ordinal = buffer.getLong();
		return new AckMessage(ordinal, uuid);
	}
	
	public long getUuid() {
		return uuid;
	}
}
