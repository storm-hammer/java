package hr.fer.oprpp2.poruke;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class HelloMessage extends Message {
	
	private long randomKey;
	private String name;
	
	public HelloMessage(String name, long ordinalNumber, long randomKey) {
		super((byte)0, ordinalNumber);
		this.name = name;
		this.randomKey = randomKey;
	}
	
	public static byte[] helloToByte(HelloMessage msg) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(msg.getMessageType());
			dos.writeLong(msg.getOrdinal());
			dos.writeUTF(msg.getName());
			dos.writeLong(msg.getRandomKey());
			dos.close();
		} catch(IOException e) {	
		}
		return bos.toByteArray();
	}
	
	public static HelloMessage byteToHello(byte[] buf) {
		int indexOfRandomKey = buf.length - 8;
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(Arrays.copyOfRange(buf, indexOfRandomKey, buf.length));
		buffer.flip();
		long randomKey = buffer.getLong();
		buffer.clear();
		buffer.put(Arrays.copyOfRange(buf, 1, 9));
		buffer.flip();
		long ordinal = buffer.getLong();
		return new HelloMessage(new String(Arrays.copyOfRange(buf, 11, indexOfRandomKey)), ordinal, randomKey);
		//zasto 11, america exblain, nesto se pogresno stvori ali ne znam u kojem smjeru ili bajt u text ili obrnuto
	}

	public long getRandomKey() {
		return randomKey;
	}

	public String getName() {
		return name;
	}
	
}
