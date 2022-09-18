package hr.fer.oprpp2.poruke;

public class Message {
	
	private long ordinal;
	private byte type;
	
	public Message(byte type, long ordinal) {
		this.type = type;
		this.ordinal = ordinal;
	}
	
	public long getOrdinal() {
		return ordinal;
	}
	
	public byte getMessageType() {
		return type;
	}
}
