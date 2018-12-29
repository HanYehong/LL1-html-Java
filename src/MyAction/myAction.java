package MyAction;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MyClass.BeginTest;
import MyClass.BeginTest_if_then;
import MyClass.YuceTable;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class myAction extends ActionSupport {
	private String NT[];
	private String T[];
	private String yucebiao[][];
	private String noLeftG[];  //没有左递归的文法G
	private String first[];
	private String follow[];
	private String myG;
	private String myTest;
	private boolean result;
	private String match[][];
	private int matchLen;
	private String G[];
	private String path_filename;
	private String chooseWF;
	
	public String execute() throws Exception{
		Map session=ActionContext.getContext().getSession(); 
		
		int count = 0;
		for ( int i = 0; i < this.myG.length(); i++ ){
			if ( this.myG.charAt(i) == '@'){
				count++;
			}
		}
		System.out.println(count+"个@");
		this.G = new String[ count ];
		
		int beginIndex = 0;
		int endIndex = 0;
		int i;
		for ( i = 0; i < count; i++){
			endIndex = this.myG.indexOf("@" , beginIndex);
			if ( endIndex != -1 ){
				this.G[i] = this.myG.substring(beginIndex, endIndex);
				beginIndex = endIndex + 1;
			}
		}
//		System.out.println(this.myTest);
//		System.out.println(this.myG);
		
		int choose = Integer.parseInt(this.chooseWF);
		if ( choose == 1 ){
			BeginTest beginTest = new BeginTest(this.G);
			this.first = beginTest.getFirst();
			this.follow = beginTest.getFollow();
			this.NT = beginTest.getNT();
			this.T = beginTest.getT();
			this.noLeftG = beginTest.getNoLeftG();
			this.yucebiao = beginTest.getYucebiao();
		}else{
			BeginTest_if_then beginTest = new BeginTest_if_then(this.G);
			this.first = beginTest.getFirst();
			this.follow = beginTest.getFollow();
			this.NT = beginTest.getNT();
			this.T = beginTest.getT();
			this.noLeftG = beginTest.getNoLeftG();
			this.yucebiao = beginTest.getYucebiao();
		}
		
		List<String[]> list1 = new ArrayList<String[]>();
		list1.add(first);
		list1.add(follow);
		list1.add(NT);
		list1.add(T);
		list1.add(noLeftG);
		list1.add(G);
		List<String[][]> list2 = new ArrayList<String[][]>();
		list2.add(yucebiao);
		
		session.put("list1", list1);
		session.put("list2", list2);
		
		return SUCCESS;
		
	}
	
	public String beginTest() throws Exception{
		
		Map session=ActionContext.getContext().getSession(); 
		
		int count = 0;
		for ( int i = 0; i < this.myG.length(); i++ ){
			if ( this.myG.charAt(i) == '@'){
				count++;
			}
		}
		System.out.println(count+"个@");
		this.G = new String[ count ];
		
		int beginIndex = 0;
		int endIndex = 0;
		int i;
		for ( i = 0; i < count; i++){
			endIndex = this.myG.indexOf("@" , beginIndex);
			if ( endIndex != -1 ){
				this.G[i] = this.myG.substring(beginIndex, endIndex);
				beginIndex = endIndex + 1;
			}
		}
//		System.out.println(this.myTest);
//		System.out.println(this.myG);
		int choose = Integer.parseInt(this.chooseWF);
		if ( choose == 1 ){
			BeginTest beginTest = new BeginTest(this.G);
			beginTest.beginTest(this.myTest);
			this.first = beginTest.getFirst();
			this.follow = beginTest.getFollow();
			this.NT = beginTest.getNT();
			this.T = beginTest.getT();
			this.noLeftG = beginTest.getNoLeftG();
			this.yucebiao = beginTest.getYucebiao();
			this.result = beginTest.isResult();
			this.match = beginTest.getMatchProcedure();
			this.matchLen = beginTest.getMatchProcedureLen();
		}else{
			BeginTest_if_then beginTest = new BeginTest_if_then(this.G);
			beginTest.beginTest(this.myTest);
			this.first = beginTest.getFirst();
			this.follow = beginTest.getFollow();
			this.NT = beginTest.getNT();
			this.T = beginTest.getT();
			this.noLeftG = beginTest.getNoLeftG();
			this.yucebiao = beginTest.getYucebiao();
			this.result = beginTest.isResult();
			this.match = beginTest.getMatchProcedure();
			this.matchLen = beginTest.getMatchProcedureLen();
		}

		session.put( "myTest", this.myTest );
		
		List<String[]> list1 = new ArrayList<String[]>();
		list1.add(first);
		list1.add(follow);
		list1.add(NT);
		list1.add(T);
		list1.add(noLeftG);
		list1.add(G);
		List<String[][]> list2 = new ArrayList<String[][]>();
		list2.add(yucebiao);
		list2.add(match);
		
		session.put("list1", list1);
		session.put("list2", list2);
		session.put("matchLen", this.matchLen);
		
		if (this.result){
			return SUCCESS;
		}else{
			return ERROR;
		}
		
	}
	
	public String saveAsTXT() throws Exception{
		
		Map session=ActionContext.getContext().getSession(); 
		
	//	String path_filename = (String) session.get("path_filename");
		
		List<String[]> list1 = (List<String[]>) session.get( "list1" );
		List<String[][]> list2 = (List<String[][]>) session.get( "list2" );
		String NT[] = list1.get(2);
		String T[] = list1.get(3);
		String noLeftG[] = list1.get(4);
		String first[] = list1.get(0);
		String follow[] = list1.get(1);
		String G[] = list1.get(5);
		String yucebiao[][] = list2.get(0);
		String match[][] = list2.get(1);
		int matchLen = (int) session.get("matchLen");
		String myTest = (String) session.get("myTest");
		
		FileWriter fileWriter=new FileWriter(this.path_filename);
		fileWriter.write("原文法：\r\n");
		for ( int i = 0; i < G.length; i++ ){
			fileWriter.write( G[i] + "\r\n" );
		}
		fileWriter.write( "\r\n" );
		fileWriter.write("消去左递归后的文法：\r\n");
		for ( int i = 0; i < noLeftG.length; i++ ){
			fileWriter.write( noLeftG[i] + "\r\n" );
		}
		fileWriter.write( "\r\n" );
		fileWriter.write("First Follow集：\r\n");
		fileWriter.write("grammar\tfirst\tfollow\r\n");
		for ( int i = 0; i < NT.length; i++ ){
			fileWriter.write( NT[i] + "\t" + first[i] + "\t" + follow[i] + "\r\n");
		}
		fileWriter.write( "\r\n" );
		fileWriter.write( "预测表：\r\n" );
		fileWriter.write( "\t" );
		for ( int i = 0; i < T.length; i++ ){
			fileWriter.write( T[i] + "\t" );
		}
		fileWriter.write( "\r\n" );
		for ( int i = 0; i < NT.length; i++ ){
			fileWriter.write( NT[i] + "\t" );
			for ( int j = 0; j < yucebiao[i].length; j++ ){
				if ( !yucebiao[i][j].equals(" ") ){
					fileWriter.write( NT[i]+"::="+yucebiao[i][j] + "\t" );
				}else{
					fileWriter.write( "\t" );
				}
			}
			fileWriter.write( "\r\n" );
		}
		fileWriter.write( "\r\n" );
		fileWriter.write( "待测试句子：\r\n" );
		fileWriter.write( myTest + "\r\n" );
		fileWriter.write( "\r\n" );
		fileWriter.write( "分析过程：\r\n" );
		for ( int i = 0; i < matchLen; i++ ){
			for ( int j = 0; j < 4; j++ ){
				fileWriter.write( match[i][j] + "\t" );
			}
			fileWriter.write( "\r\n" );
		}
		fileWriter.write( "\r\n" );
		fileWriter.write( "分析结果：SUCCESS!\r\n" );
		fileWriter.flush();
		fileWriter.close();
		
		return SUCCESS;
		
	}

	public String getMyG() {
		return myG;
	}

	public void setMyG(String myG) {
		this.myG = myG;
	}

	public String getMyTest() {
		return myTest;
	}

	public void setMyTest(String myTest) {
		this.myTest = myTest;
	}

	public String getPath_filename() {
		return path_filename;
	}

	public void setPath_filename(String path_filename) {
		this.path_filename = path_filename;
	}

	public String getChooseWF() {
		return chooseWF;
	}

	public void setChooseWF(String chooseWF) {
		this.chooseWF = chooseWF;
	}

}
