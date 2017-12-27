import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import javax.swing.table.DefaultTableModel;

import java.awt.Component;

public class MainWindow {
	//public static HashMap<String, Client> clients; // Key: WifiIP Value: client
	private JFrame mainFrame;
	public JTable infTable;
	private int req_port = 6101;
	private ServerRspThread rsp_thr;
	private ReceiveResult rsp_result;
	//public Map<String, Position> PreviousResult = new ConcurrentHashMap<String, Position>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//clients = new HashMap<String, Client>();
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
		System.out.println("��������");
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
		System.out.println("��������");
		rsp_thr = new ServerRspThread(req_port, this);//��ʼ��һ���߳�
		
		rsp_result=new ReceiveResult(req_port+1, this);
		
		
		//System.out.println("��������");

		JButton runBtn = new JButton("Run Server");//��ť
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
					new Thread(rsp_thr, "Request responsing Thread").start();
					new Thread(rsp_result, "Result receiving Thread").start();
			}
		});
		btnPanel.add(runBtn);//����ť��ӵ�btnPanel��

		JScrollPane scrollPanel = new JScrollPane();//�������ñ��
		mainFrame.getContentPane().add(scrollPanel, BorderLayout.CENTER);//�������������λ��

		Object[][] tableData = {};
		Object[] columnTitle = {"RSS","Localization_Step","Localization_No_Step","Localization_Normal_AP"};
		infTable = new JTable(tableData, columnTitle);//���ɱ��
		scrollPanel.setViewportView(infTable);//��������scrollPanel
		
		DefaultTableModel tableModel = new DefaultTableModel();
		Object[][] tableData2 = {};
		Object[] columnTitle2 = {"RSS","Localization_Step","Localization_No_Step","Localization_Normal_AP"};
		tableModel.setDataVector(tableData2, columnTitle2);
		infTable.setModel(tableModel);
		


	}

	
}
