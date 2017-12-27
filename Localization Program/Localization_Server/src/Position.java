import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



// 记录任务（一个包）的类
public class Position {

	
	private int x,y;
	
	public Position(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public Position(){
	}

	
	public void setx(int x){
		this.x=x;
	}
	public void sety(int y){
		this.y=y;
	}

	public int getx(){
		return x;
	}
	
	public int gety(){
		return y;
	}
	
	
	
}

