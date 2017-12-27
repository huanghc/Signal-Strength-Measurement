import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.Component;

public class MainWindow {
	
	private JFrame mainFrame;
	public JTable infTable;
	private int req_port = 6105;
	private ServerRspThread rsp_thr;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainWindow window = new MainWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Server");
		mainFrame.setBounds(100, 100, 450, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel btnPanel = new JPanel();//�������ð�ť
		mainFrame.getContentPane().add(btnPanel, BorderLayout.EAST);

		rsp_thr = new ServerRspThread(req_port);//��ʼ��һ���߳�
		

		JButton runBtn = new JButton("Run Server");//��ť
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
					new Thread(rsp_thr, "Request responsing Thread").start();
			}
		});
		btnPanel.add(runBtn);//����ť��ӵ�btnPanel��

		JScrollPane scrollPanel = new JScrollPane();//�������ñ��
		mainFrame.getContentPane().add(scrollPanel, BorderLayout.CENTER);//�������������λ��

		Object[][] tableData = {};
		Object[] columnTitle = { "User Name", "Password", "Is PU", "Packet Number","Download Address" };
		infTable = new JTable(tableData, columnTitle);//���ɱ��
		scrollPanel.setViewportView(infTable);//��������scrollPanel


	}

	
}
