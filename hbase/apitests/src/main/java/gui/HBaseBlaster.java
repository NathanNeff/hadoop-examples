package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import util.HBaseUtility;

public class HBaseBlaster {

	private JFrame frame;
	private HBaseUtility caot;

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
				caot.blast("testTable", 10000);
			}
		});
		btnPerformPuts.setBounds(319, 346, 107, 25);
		frame.getContentPane().add(btnPerformPuts);

		caot = new HBaseUtility();
	}
}
