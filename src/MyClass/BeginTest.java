package MyClass;
import java.util.Scanner;
import java.util.Stack;

public class BeginTest {
	private String NT[];
	private String T[];
	private String testString;
	private Stack<String> stack;
	private int index;
	private String yucebiao[][];
	private YuceTable yuceTable;
	private String noLeftG[];  //没有左递归的文法G
	private String first[];
	private String follow[];
	private boolean result;
	private String matchProcedure[][];
	private int matchProcedureLen;
	
	public BeginTest( String G[] ) {
		this.yuceTable = new YuceTable( G );
		this.yucebiao = yuceTable.getTable();
		this.NT = yuceTable.getNT();
		this.T = yuceTable.getT();
		this.noLeftG = yuceTable.getNoLeftG();
		this.first = yuceTable.getFirst();
		this.follow = yuceTable.getFollow();
		this.stack=new Stack<String>();
		this.stack.push("#");
		this.index=0;
		this.matchProcedure = new String[50][4];
	}
	
	public boolean beginTest( String testString ) {
		int count = 0;//
		
		this.testString = testString;
		this.testString += "#";
		this.stack.push( this.NT[0] );
		String temp="";
		int len = this.testString.length();
		temp=this.stack.pop();
		while(!temp.equals("#")) {
			this.matchProcedure[count][0] = count + "";//
			String str[] = new String [ this.stack.size() ];
			String str2 = "";
			int ind = 0;
			while ( !this.stack.empty() ){
				String pop = this.stack.pop();
				str[ ind++ ] = pop;
				str2 = pop + str2;
			}
			str2 += temp;
			for ( int i = ind-1; i >= 0; i-- ){
				this.stack.push( str[i] );
			}
			this.matchProcedure[count][1] = str2;  //
			this.matchProcedure[count][2] = this.testString.substring( index );//
			
			if(temp.equals(this.testString.charAt(index)+"")) {
				index++;
				this.matchProcedure[count][3] = " ";
			}
			else {
				int x=0,y=0;
				for(int i=0;i<NT.length;i++) {
					if(NT[i].equals(temp)) {
						x=i;
						break;
					}
				}
				for(int i=0;i<T.length;i++) {
					if(T[i].equals(this.testString.charAt(index)+"")) {
						y=i;
						break;
					}
				}
				if(!yucebiao[x][y].equals(" ")) {
					if(!yucebiao[x][y].equals("ε")) {
						this.matchProcedure[count][3] =  NT[x] + "::=" + this.yucebiao[x][y];
						char ch[]=yucebiao[x][y].toCharArray();
						for(int i=ch.length-1;i>=0;i--) {
							this.stack.push(ch[i]+"");
						}
					}
					else {
						this.matchProcedure[count][3] = NT[x] + "::=" + "ε";
						;
					}
				}
				else {
					this.matchProcedureLen = count;
					this.result = false;
					return false;
				}
			}
			count++;
			temp=this.stack.pop();
		}
		this.matchProcedureLen = count;
		if ( index == len-1 ){
			this.result = true;
		}else{
			this.result = false;
		}
		return true;
	}

	public String[] getNT() {
		return NT;
	}

	public void setNT(String[] nT) {
		NT = nT;
	}

	public String[] getT() {
		return T;
	}

	public void setT(String[] t) {
		T = t;
	}

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	public Stack<String> getStack() {
		return stack;
	}

	public void setStack(Stack<String> stack) {
		this.stack = stack;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String[][] getYucebiao() {
		return yucebiao;
	}

	public void setYucebiao(String[][] yucebiao) {
		this.yucebiao = yucebiao;
	}

	public YuceTable getYuceTable() {
		return yuceTable;
	}

	public void setYuceTable(YuceTable yuceTable) {
		this.yuceTable = yuceTable;
	}

	public String[] getNoLeftG() {
		return noLeftG;
	}

	public void setNoLeftG(String[] noLeftG) {
		this.noLeftG = noLeftG;
	}

	public String[] getFirst() {
		return first;
	}

	public void setFirst(String[] first) {
		this.first = first;
	}

	public String[] getFollow() {
		return follow;
	}

	public void setFollow(String[] follow) {
		this.follow = follow;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String[][] getMatchProcedure() {
		return matchProcedure;
	}

	public void setMatchProcedure(String[][] matchProcedure) {
		this.matchProcedure = matchProcedure;
	}

	public int getMatchProcedureLen() {
		return matchProcedureLen;
	}

	public void setMatchProcedureLen(int matchProcedureLen) {
		this.matchProcedureLen = matchProcedureLen;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner( System.in );
	//	int choose = input.nextInt()
		String str[];
		String testString = "";
		String temp[] = new String[50];
		int index = 0;
		System.out.println("请输入文法（按$结束）：");
		boolean isEND = false;
		do {
			String s = input.nextLine();
			if( !s.equals("$") ) {
				temp[ index++ ] = s;
			}else {
				isEND = true;
			}
		}while( !isEND );
		str = new String[ index ];
		for ( int i = 0; temp[i] != null; i++ ) {
			str[i] = temp[i];
		}
		System.out.print("请输入你要分析的文法句子：");
		testString = input.nextLine();
		BeginTest bt = new BeginTest( str );
		boolean result = bt.beginTest( testString );
		String pipeiguocheng[][] = bt.getMatchProcedure();
		int len = bt.getMatchProcedureLen();
		System.out.println();
		if(result) {
			System.out.print("恭喜你，匹配成功！");
			System.out.println();
			for( int i = 0; i < len; i++ ){
				for ( int j = 0; j < 4; j++ ){
					if ( pipeiguocheng[i][j] != null){
						System.out.print(pipeiguocheng[i][j] + "\t");
					}
				}
				System.out.println();
			}
		}
		else {
			System.out.print("不是该文法的句子 ！");
		}
	}
	
}
