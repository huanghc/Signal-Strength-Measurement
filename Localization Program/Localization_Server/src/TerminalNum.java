import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



// 记录任务（一个包）的类
public class TerminalNum {

	
	private Map<String, Integer> Phonelist=new ConcurrentHashMap<String, Integer>();
	private Map<String, SmartphoneThread> Phonelist2=new ConcurrentHashMap<String, SmartphoneThread>();
	
	public TerminalNum(int ID, int Testtime,SmartphoneThread spt){
		System.out.println(" "+ID+Testtime+"    "+new Integer(ID).toString());	
		
		Phonelist.put(new Integer(ID).toString(), Testtime);
		Phonelist2.put(new Integer(ID).toString(), spt);
	}
	
	public TerminalNum(){
	}

	
	public void addterminal(int ID, int Testtime, SmartphoneThread spt){
		//System.out.println("收到添加设备要求");
		Phonelist.put(new Integer(ID).toString(), Testtime);
		Phonelist2.put(new Integer(ID).toString(), spt);
	}
	
	public void deleteterminal(int ID){
		Phonelist.remove(new Integer(ID).toString());
		Phonelist2.remove(new Integer(ID).toString());
	}
	
	public boolean isempty(){
		if (Phonelist.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public void setTesttime(int ID, int Testtime){
		Phonelist.put(new Integer(ID).toString(), Testtime);
	}
	

	
	public int getTesttime(int ID){
		return Phonelist.get(new Integer(ID).toString());
	}
	
	
	public SmartphoneThread getSmartphoneThread(int ID){
		return Phonelist2.get(new Integer(ID).toString());
	}
	
	public void cmd2test(String APname, int APState){
		//在这里遍历，发布dotest命令
		Set<String> set =Phonelist2.keySet();//       
		Iterator<String> it=set.iterator();//       
		while(it.hasNext()){//          
			System.out.println("???????????????????");
			String s= (String) it.next();// 
			Phonelist2.get(s).dotest(APname, APState);
		}
	}
	
	
	
	
	public void refresh(){
		Set<String> set =Phonelist.keySet();//       
		Iterator<String> it=set.iterator();//       
		while(it.hasNext()){//           
			String s= (String) it.next();// 
			if(Phonelist.get(s).equals(1)){
				Phonelist.remove(s);
				Phonelist2.remove(s);
			}else{
				Phonelist.put(s, Phonelist.get(s)+1);
			}		
		}

	}
	
	
	
	
}

