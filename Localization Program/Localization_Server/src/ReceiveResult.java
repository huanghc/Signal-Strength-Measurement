import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.table.DefaultTableModel;

import com.app.main.CalculateLocalization;
import com.app.util.Coordinate;

public class ReceiveResult implements Runnable {
	private int req_port;
	private ServerSocket req_server;
	private Socket req_s;
	private BufferedReader req_in;
	//private PrintWriter req_out;	
	private MainWindow parent;
	boolean end_flag;	
	public APmanager apm;
	private Map<String, Position> PreviousResult = new ConcurrentHashMap<String, Position>();
	private CalculateLocalization calcul;
	private Coordinate position_step,position_no_step,position_normal_AP;
	public ReceiveResult(int port,MainWindow mw) {
		this.req_port=port;
		parent=mw;
		calcul=new CalculateLocalization();
		
	}

	@Override
	public void run() {
		System.out.println("Socket建立前");		
		try {
			req_server = new ServerSocket(req_port);
			while(true){
				System.out.println("开始监听结果");	
				req_s = req_server.accept();//开始监听
				req_in = new BufferedReader(new InputStreamReader(req_s.getInputStream()));
				//req_out = new PrintWriter(req_s.getOutputStream());
				
				String line = req_in.readLine();
				System.out.println("手机IP: "+req_s.getInetAddress()+" 收到测量结果" + line);
				
				String[] result = line.split(" ");
				//result[result.length-1]为前进步数
				String ChangableAP="";
				String AllAP="";
				String NormalAP="";
				for(int i=0;i<=result.length-1;i++){
					if(result[i].equals("IWCTAP1")||result[i].equals("IWCTAP2")||result[i].equals("IWCTAP3")||result[i].equals("IWCTAP4")||result[i].equals("IWCTAP5")){
						//System.out.println(result[i]);
						if(ChangableAP.isEmpty()){
							ChangableAP=ChangableAP+"APNAME: ";
						}else{
							ChangableAP=ChangableAP+" APNAME: ";
						}
						ChangableAP=ChangableAP+result[i]+" STATE1: "+result[i+2]+" RSS:";
						int p=0;
						for(int j=i+4;j<=result.length-1;j++){
							if(result[j].equals("STATE2:")){p=j;break;}else{
							ChangableAP=ChangableAP+" "+result[j];
							//System.out.println("!!!"+result[j]);
							}
						}
						ChangableAP=ChangableAP+" STATE2: "+result[p+1]+" RSS:";
					
						for(int j=p+3;j<=result.length-1;j++){
							if(result[j].equals("Step")||result[j].equals("APNAME:")){break;}else{
							ChangableAP=ChangableAP+" "+result[j];
							//System.out.println("!!!"+result[j]);
							}
						}			
					}
				}
				System.out.println(ChangableAP);
				//command = "APNAME: hehe STATE1: 0 RSS: -41 -41 STATE2: 1 RSS: -41 -41";
		
//				for(int k=0;k<=result.length-3;k++){
//					if(AllAP.isEmpty()){
//						AllAP=AllAP+result[k];
//					}else{
//						AllAP=AllAP+" "+result[k];
//					}
//				}
//				System.out.println(AllAP);
				
				for(int q=1;q<=5;q++){
					if(q==1){
						NormalAP=NormalAP+"APNAME: IWCTAP"+q+" STATE1: 0 RSS:";
					}else{
						NormalAP=NormalAP+" APNAME: IWCTAP"+q+" STATE1: 0 RSS:";
					}
					for(int i=0;i<=result.length-1;i++){
						if(result[i].equals("IWCTAP"+(q*2-1))){
							for(int j=i+4;j<=result.length-1;j++){
								if(result[j].equals("STATE2:")){
									break;
								}else{
									NormalAP=NormalAP+" "+result[j];
								}
							}	
						}
					}
					NormalAP=NormalAP+" STATE2: 1 RSS:";
					
					for(int i=0;i<=result.length-1;i++){
						if(result[i].equals("IWCTAP"+q*2)){
							for(int j=i+4;j<=result.length-1;j++){
								if(result[j].equals("STATE2:")){
									break;
								}else{
									NormalAP=NormalAP+" "+result[j];
								}
							}	
						}
					}	
				
				}
				System.out.println(NormalAP);
				

				
				
				
				//测试时恢复这部分的注释！！
//				if(!PreviousResult.containsKey(req_s.getInetAddress().getHostAddress())){
//					//计算位置
//					position_step=calcul.GetPosition(ChangableAP);
//					position_no_step=position_step;
//					position_normal_AP=calcul.GetPosition(NormalAP);
//					System.out.println("第一次定位结果" + position_step.getX()+" "+position_step.getY());
//					System.out.println("普通AP定位结果" + position_normal_AP.getX()+" "+position_normal_AP.getY());
//					
//					PreviousResult.put(req_s.getInetAddress().getHostAddress(), new Position(position_step.getX(),position_step.getY()));
//					refreshInfTable(line,position_step,position_no_step,position_normal_AP);
//				}else{
//					Coordinate temp=new Coordinate(PreviousResult.get(req_s.getInetAddress().getHostAddress()).getx(),PreviousResult.get(req_s.getInetAddress().getHostAddress()).gety());
//					//PreviousResult.get(req_s.getInetAddress().getHostAddress());//前一次定位的位置作为参数进行下一次定位运算
//					position_no_step=calcul.GetPosition(ChangableAP);
//					System.out.println("不利用脚步定位结果" + position_no_step.getX()+" "+position_no_step.getY());
//					
//					position_step=calcul.GetPosition_step(temp, Integer.parseInt(result[result.length-1]), ChangableAP);
//					System.out.println("利用脚步定位结果" + position_step.getX()+" "+position_step.getY());
//					
//					position_normal_AP=calcul.GetPosition(NormalAP);
//					System.out.println("普通AP定位结果" + position_normal_AP.getX()+" "+position_normal_AP.getY());
//					
//					PreviousResult.put(req_s.getInetAddress().getHostAddress(), new Position(position_step.getX(),position_step.getY()));
//					refreshInfTable(line,position_step,position_no_step,position_normal_AP);
//					//System.out.println(PreviousResult.get(req_s.getInetAddress().getHostAddress()).getx()+" "+PreviousResult.get(req_s.getInetAddress().getHostAddress()).gety());
//				}
				


			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//建立监听socket
	}
	
	private void refreshInfTable(String line, Coordinate position_step,Coordinate position_no_step, Coordinate position_normal_AP) {//刷新表格
		// clear table
		//System.out.println("clearTable begin");
		try{
//			DefaultTableModel tableModel = new DefaultTableModel();
//			Object[][] tableData = {};
//			Object[] columnTitle = {"Result"};
//			tableModel.setDataVector(tableData, columnTitle);
//			parent.infTable.setModel(tableModel);
		}catch (Exception e){
			e.printStackTrace();
		}
		//System.out.println("clearTable end");
		//refill table
		DefaultTableModel tableModel = (DefaultTableModel) parent.infTable.getModel();
		
		String[] row=new String[4];
		row[0]=line;
		row[1]="X:"+position_step.getX()+" Y:"+position_step.getY();
		row[2]="X:"+position_no_step.getX()+" Y:"+position_no_step.getY();
		row[3]="X:"+position_normal_AP.getX()+" Y:"+position_normal_AP.getY();
		tableModel.addRow(row);
		
//		for (Client client : MainWindow.clients.values()){
//			row[0] = client.getName();
//			row[1] = client.getpsw();
//			tableModel.addRow(row);
//		}
	}
	
	
	
}

