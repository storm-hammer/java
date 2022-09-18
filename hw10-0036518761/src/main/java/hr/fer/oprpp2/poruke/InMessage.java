package hr.fer.oprpp2.poruke;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class InMessage extends Message {
	
	private String text, name;
	
	public InMessage(long ordinal, String name, String text) {
		super((byte)5, ordinal);
		this.name = name;
		this.text = text;
	}
	
	public static byte[] inToBytes(InMessage msg) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(msg.getMessageType());
			dos.writeLong(msg.getOrdinal());
			dos.writeUTF(msg.getName());
			dos.writeUTF(msg.getText());
			dos.close();
		} catch(IOException e) {	
		}
		return bos.toByteArray(); 
	}
	
	public static InMessage bytesToIn(byte[] bytes, int indexOfText) {//index of text je index prvog znaka poruke
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(Arrays.copyOfRange(bytes, 1, 9));
		buffer.flip();
		long ordinal = buffer.getLong();
		int offset = 2;//zbog nekog razloga
		String name = new String(Arrays.copyOfRange(bytes, 9+offset, indexOfText+offset));//cudni brojevi
		String text = new String(Arrays.copyOfRange(bytes, indexOfText+offset+2, bytes.length));
		return new InMessage(ordinal, name, text);
	}

	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
}
