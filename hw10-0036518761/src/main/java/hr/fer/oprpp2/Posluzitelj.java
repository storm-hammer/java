package hr.fer.oprpp2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import hr.fer.oprpp2.poruke.AckMessage;
import hr.fer.oprpp2.poruke.HelloMessage;
import hr.fer.oprpp2.poruke.Message;

public class Posluzitelj {
	
	private List<Thread> dretve;
	private static int port;
	private static Map<Long, Client> klijenti;
	
	public Posluzitelj() {
		dretve = new ArrayList<>();
		klijenti = new HashMap<>();
	}
	
	class Client {
		
		private InetAddress adresa;
		private long randomKey, uuid;
		private Queue<Message> slanje;
		private Queue<AckMessage> potvrde;
		
		public Client(long randomKey) {
			super();
			this.randomKey = randomKey;
			this.slanje = new LinkedList<>();
			this.potvrde = new LinkedList<>();
		}
	}
	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Dragi korisniče, trebali ste unijeti port!");
			return;
		}
		
		port = Integer.parseInt(args[0]);
		
		Posluzitelj server = new Posluzitelj();
		
		if(port < 1 || port > 65535) {
			System.out.println("Dragi korisniče port mora biti između 1 i 65535.");
			return;
		}
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port);
		} catch(SocketException e) {
			System.out.println("Nije moguće otvoriti pristupnu točku, detaljnija poruka: "+e.getMessage());
		}
		
		while(true) {
			byte[] buf = new byte[4000];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			try {
				socket.receive(packet);
			} catch(IOException e) {
				System.out.println("Dogodila se greška: "+e.getMessage());
				return;
			}
			
			byte[] buf2 = packet.getData();
			if(buf[2] == 1) {//hello
				HelloMessage msg = HelloMessage.byteToHello(buf2);
				if(klijenti.containsKey(msg.getRandomKey())) {
					
				}
			}
		}
		
	}
}
