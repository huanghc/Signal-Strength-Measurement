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

		JPanel btnPanel = new JPanel();//用来放置按钮
		mainFrame.getContentPane().add(btnPanel, BorderLayout.EAST);

		rsp_thr = new ServerRspThread(req_port);//初始化一个线程
		

		JButton runBtn = new JButton("Run Server");//按钮
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
					new Thread(rsp_thr, "Request responsing Thread").start();
			}
		});
		btnPanel.add(runBtn);//将按钮添加到btnPanel中

		JScrollPane scrollPanel = new JScrollPane();//用来放置表格
		mainFrame.getContentPane().add(scrollPanel, BorderLayout.CENTER);//表格设置在中央位置

		Object[][] tableData = {};
		Object[] columnTitle = { "User Name", "Password", "Is PU", "Packet Number","Download Address" };
		infTable = new JTable(tableData, columnTitle);//生成表格
		scrollPanel.setViewportView(infTable);//将表格放入scrollPanel


	}

	
}
