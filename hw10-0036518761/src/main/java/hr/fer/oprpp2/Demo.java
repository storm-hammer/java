package hr.fer.oprpp2;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import hr.fer.oprpp2.poruke.AckMessage;
import hr.fer.oprpp2.poruke.ByeMessage;
import hr.fer.oprpp2.poruke.HelloMessage;
import hr.fer.oprpp2.poruke.InMessage;
import hr.fer.oprpp2.poruke.OutMessage;

public class Demo {
	public static void main(String[] args) {
		
//		HelloMessage msg = new HelloMessage("mislav", 567, 34567);
//		HelloMessage msg2 = HelloMessage.byteToHello(HelloMessage.helloToByte(msg));
//		System.out.println(msg2.getCode() +", "+msg2.getName()+", "+msg2.getOrdinalNumber()+", "+msg2.getRandomKey());
		
//		ByeMessage msg = new ByeMessage(12, 84684);
//		ByeMessage msg2 = ByeMessage.bytesToBye(ByeMessage.byeToBytes(msg));
//		System.out.println(msg2.getCode()+" "+msg2.getOrdinal()+" "+msg2.getUuid());
		
//		AckMessage msg = new AckMessage(12, 84684);
//		AckMessage msg2 = AckMessage.bytesToAck(AckMessage.ackToBytes(msg));
//		System.out.println(msg2.getCode()+" "+msg2.getOrdinal()+" "+msg2.getUuid());
		
//		OutMessage msg = new OutMessage(12, 145364, "evo me tu sam");
//		OutMessage msg2 = OutMessage.bytesToOut(OutMessage.outToBytes(msg));
//		System.out.println(msg2.getCode()+" "+msg2.getOrdinal()+" "+msg2.getUuid()+" "+msg2.getText());
		
//		String name = "mislav";
//		InMessage msg = new InMessage(12, name, "evo me tu sam");
//		InMessage msg2 = InMessage.bytesToIn(InMessage.inToBytes(msg), 9+name.length());
//		System.out.println(msg2.getCode()+" "+msg2.getOrdinal()+" "+msg2.getName()+" "+msg2.getText());
		


	}
}
