package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

import util.HBaseUtility;

public class HBaseBlaster implements Observer {

	private JFrame frame;
	private HBaseUtility caot;
	private	JProgressBar progressBar;
private JProgressBar progressBar_1;
private JButton btnTruncateTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HBaseBlaster window = new HBaseBlaster();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HBaseBlaster() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTextPane txtStatus = new JTextPane();
		txtStatus.setBounds(12, 12, 422, 183);
		frame.getContentPane().add(txtStatus);

		JButton btnPerformPuts = new JButton("Put");
		btnPerformPuts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar_1.setMaximum(10000);
				progressBar_1.setValue(0);
				caot.blast("testTable", 10000);
			}
		});
		btnPerformPuts.setBounds(319, 346, 107, 25);
		frame.getContentPane().add(btnPerformPuts);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(true);
		frame.getContentPane().add(progressBar);
		
		progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(12, 223, 422, 14);
		frame.getContentPane().add(progressBar_1);
		
		JButton btnPerformCheckAnd = new JButton("Perform Check and Put");
		btnPerformCheckAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				caot.checkAndPut("testTable");
			}
		});
		btnPerformCheckAnd.setBounds(12, 346, 107, 25);
		frame.getContentPane().add(btnPerformCheckAnd);
		
		btnTruncateTable = new JButton("Truncate Table");
		btnTruncateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				caot.truncateTable("testTable");
			}
		});
		btnTruncateTable.setBounds(12, 259, 107, 25);
		frame.getContentPane().add(btnTruncateTable);

		caot = new HBaseUtility();
		caot.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {

		Integer prog = (Integer) arg;
		this.frame.getContentPane().update(this.frame.getGraphics());
		
		progressBar_1.setValue(prog);
		
		
	}
}
