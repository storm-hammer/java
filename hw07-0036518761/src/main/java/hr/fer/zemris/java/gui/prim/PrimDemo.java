package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Prim brojevi");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
	
	static class PrimListModel implements ListModel<Integer> {
		private List<Integer> elements = new ArrayList<>();
		private List<ListDataListener> observers = new ArrayList<>();
		
		public PrimListModel() {
			elements.add(1);
		}

		@Override
		public int getSize() {
			return elements.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return elements.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			observers.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			observers.add(l);
		}

		public int next() {
			int current = (int) elements.get(elements.size()-1);
			boolean isPrime;
			do {
				isPrime = true;
				current++;
				for(int i = 2; i < current; i++) {
					if(current % i == 0) {
						isPrime = false;
						break;
					}
				}
			} while(!isPrime);
			return current;
		}
		
		public void add() {
			int pos = elements.size();
			elements.add(next());
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener l : observers) {
				l.intervalAdded(event);
			}
		}
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JButton sljedeci = new JButton("Sljedeći");
				
		sljedeci.addActionListener(e -> {
			model.add();
		});
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(sljedeci, BorderLayout.PAGE_END);
	}
}
