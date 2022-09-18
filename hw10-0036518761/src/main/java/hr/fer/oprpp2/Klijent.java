package hr.fer.oprpp2;

import java.awt.BorderLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import hr.fer.oprpp2.poruke.AckMessage;
import hr.fer.oprpp2.poruke.ByeMessage;
import hr.fer.oprpp2.poruke.HelloMessage;
import hr.fer.oprpp2.poruke.InMessage;
import hr.fer.oprpp2.poruke.Message;
import hr.fer.oprpp2.poruke.OutMessage;

public class Klijent extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static JTextArea input, screen;
	private JScrollPane scrollPane;
	private static DatagramSocket socket;
	private static int ordinal, port;
	private static long random, uuid;
	private static InetAddress adresa = null;
	private static LinkedBlockingQueue<Message> ackQueue;
	private static LinkedBlockingQueue<InMessage> received;
	
	public Klijent(String name) {
		super("Chat client: "+name);
		ackQueue = new LinkedBlockingQueue<>();
		received = new LinkedBlockingQueue<>();
		this.ordinal = 0;
		this.random = new Random().nextLong();
		this.input = new JTextArea();
		this.screen = new JTextArea();
		this.scrollPane = new JScrollPane(screen);
		initGUI();
		byte[] buf = HelloMessage.helloToByte(new HelloMessage(name, ordinal, random));
		send(new DatagramPacket(buf, buf.length), ordinal++);
	}
	
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				byte[] buf = ByeMessage.byeToBytes(new ByeMessage(ordinal, uuid));
				send(new DatagramPacket(buf, buf.length), ordinal++);
				socket.close();
			}
		});
		
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyTyped(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String text = input.getText();
					input.setText(null);
					byte[] buf = OutMessage.outToBytes(new OutMessage(ordinal, uuid, text));
					send(new DatagramPacket(buf, buf.length), ordinal++);
				}
			}
		});
		
		input.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.getContentPane().add(input, BorderLayout.PAGE_START);
		this.screen.setEditable(false);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(300, 200);
	}
	
	private void send(DatagramPacket packet, long ordinal) {
		int failCounter = 0;
		packet.setAddress(adresa);
		packet.setPort(port);
		
		while(failCounter < 10) {
			
			try {
				socket.send(packet);
			} catch(IOException e) {
				System.out.println("Upit se ne može izvršiti");
				break;
			}
			
			while(true) {
				try {
					System.out.println("cekam na ack");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					continue;
				}
				break;
			}
			
			if(wasSent(ordinal)) {
				System.out.println("poruka broj: "+ordinal+" je uspjesno poslana");
				return;
			} else {
				failCounter++;
				continue;
			}
		}
		
		dispose();
		socket.close();
		System.exit(0);
	}
		
	private boolean wasSent(long ordinal2) {
		for(Message s : ackQueue) {
			if(ordinal2 == s.getOrdinal()) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		
		if(args.length != 3) {
			System.out.println("Dragi korisniče, morate unijeti IP adresu, port i ime!");
			return;
		}
		String name = args[2];
		port = Integer.parseInt(args[1]);
		try {
			adresa = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Ne može se razriješiti: "+args[0]);
			return;
		}
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Ne mogu otvoriti pristupnu točku");
			return;
		}
		
//		try {
//			socket.setSoTimeout(5000);
//		} catch (SocketException e) {
//		}
		
		Klijent klijent = new Klijent(name);
		
		SwingUtilities.invokeLater(() -> {
			klijent.setVisible(true);
		});
		
		Thread receiving = new Thread(() -> {
			byte[] buf = new byte[4000];//zasad
			DatagramPacket packet2 = new DatagramPacket(buf, buf.length);
			while(true) {
				try {
					socket.receive(packet2);
				} catch(IOException e) {
					break;
				}
				if(buf[0] == 2) {
					while(true) {
						try {
							ackQueue.put(AckMessage.bytesToAck(buf));
						} catch (InterruptedException e) {
						}
						break;
					}
				} else {
					InMessage msg = InMessage.bytesToIn(buf, 16);
					while(true) {
						try {
							received.put(msg);
						} catch (InterruptedException e) {
							continue;
						}
						break;
					}
				}
			}
		});
		receiving.start();
		
		System.out.println("sad cekamo na poruke");
		
		while(true) {
			InMessage msg = null;
			try {
				msg = received.take();
			} catch(InterruptedException e) {
				continue;
			}
			String text = screen.getText()+"\n"+"[/"+adresa.toString()+":"+port+
					"] Poruka od korisnika: "+msg.getName()+"\n"+msg.getText();
			screen.setText(text);
		}

	}
}