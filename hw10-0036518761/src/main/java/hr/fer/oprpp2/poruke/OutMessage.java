package hr.fer.oprpp2.poruke;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class OutMessage extends Message {
	
	private long uuid;
	private String text;
	
	public OutMessage(long ordinal, long uuid, String text) {
		super((byte)4, ordinal);
		this.uuid = uuid;
		this.text = text;
	}
	
	public static byte[] outToBytes(OutMessage msg) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(msg.getMessageType());
			dos.writeLong(msg.getOrdinal());
			dos.writeLong(msg.getUuid());
			dos.writeUTF(msg.getText());
			dos.close();
		} catch(IOException e) {	
		}
		return bos.toByteArray(); 
	}
	
	public static OutMessage bytesToOut(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(Arrays.copyOfRange(bytes, 9, 17));
		buffer.flip();
		long uuid = buffer.getLong();
		buffer.clear();
		buffer.put(Arrays.copyOfRange(bytes, 1, 9));
		buffer.flip();
		long ordinal = buffer.getLong();
		String text = new String(Arrays.copyOfRange(bytes, 19, bytes.length));//takoder umjesto 17 je 19 zasto to
		return new OutMessage(ordinal, uuid, text);
	}

	public long getUuid() {
		return uuid;
	}
	
	public String getText() {
		return text;
	}
}
