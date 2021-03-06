package MyClass;
import java.util.Arrays;
import java.util.Scanner;

public class YuceTable_if_then {
	private String left[];  //左部集合（包括重复的左部）
	private String right[];  //右部集合
	private Model members[];  //文法成员对象
	private String T[];   //终结符集
	private String NT[];  //非终结符集
	private String table[][];  //生成的预测分析表
	private String noLeftG[];  //没有左递归的文法G
	private String first[];
	private String follow[];
	
	public YuceTable_if_then(String G[]) {
		String G_right[] = new String[ G.length ];
		String G_left[] =  new String[ G.length ];
		for ( int i = 0; i < G.length; i++ ) {  //得到左部和右部
			G_left[i] = G[i].charAt(0) + "";
			G_right[i] = G[i].substring(4);
		}
		
		G = removeLeftRecursion( G_left, G_right ); //消左递归
		this.noLeftG = G;
		
		System.out.println( "消去左递归后如下：" );
		for ( int i = 0; i < G.length; i++ ) {
			System.out.println( G[i] );
		}
		
		getLeftRight(G); //得到左右部分
		for ( int i = 0; i < this.left.length; i++ ) {
			this.members[i].setFirst( setFirst( members[i].getRight() ) );  //求每个对象的first集
//			System.out.print(this.members[i].getLeft()+" : ");
//			for ( int j = 0; j < this.members[i].getFirst().length; j++ ){
//				System.out.print(this.members[i].getFirst()[j]+" ");
//			}
//			System.out.println();
		}
		for ( int i = 0; i < this.members.length; i++ ) { //求每个对象的是否推出空
			for ( int j = 0; j < this.members[i].getFirst().length; j++) {
				if ( this.members[i].getFirst()[j].equals("ε") ) {
					this.members[i].setIsNull( "Y" );
					break;
				}
				this.members[i].setIsNull( "N" );
			}
		}
		for ( int i = 0; i < this.left.length; i++ ) {  //求每个对象的follow集
			this.members[i].setFollow( setFollow( members[i].getLeft() ) );
//			System.out.print(this.members[i].getLeft()+" : ");
//			for ( int j = 0; j < this.members[i].getFollow().length; j++ ){
//				System.out.print(this.members[i].getFollow()[j]+" ");
//			}
//			System.out.println();
		}
		
		/* 下面为终结符号和非终结符号的去重处理 */
		for ( int i = 0; i < this.members.length; i++) {
//			String str1[] = this.members[i].getFirst();
//			String str2[] = this.members[i].getFollow();
//			System.out.print("T：");   //输出测试
//			for ( int j = 0; j < str1.length ; j++) {
//				System.out.print( str1[j] +" ");
//			}
//			System.out.println();
//			System.out.print("NT：");
//			for ( int j = 0; j < str2.length ; j++) {
//				System.out.print( str2[j] +" ");
//			}
//			System.out.println();
		}
		String T[] = new String[50];
		int T_index = 0;
		String NT = "";
		for ( int i = 0; i < this.members.length; i++ ) {
			if ( NT.indexOf( this.members[i].getLeft() ) == -1) { //不存在就放入非终结符集
				NT += this.members[i].getLeft();
			}
		}
		for ( int i = 0; i < this.members.length; i++ ) {
			String temp_right = this.members[i].getRight();
			String s = "";
			for ( int j = 0; j < temp_right.length(); j++) {
				int r = 0;
				if ( !( temp_right.charAt(j) + "" ).equals(" ") )
					if ( ( temp_right.charAt(j) + "").matches("[a-z]") ){
						while(( temp_right.charAt(j) + "").matches("[a-z]")){
							s +=  temp_right.charAt(j);
							j++;
							if ( j == temp_right.length() ){
								if ( isExist( T, s) == 0 ){ //去重
									T[ T_index++ ] = s;
								}
								s = "";
								r = 1;
								break;
							}
						}
						if ( r == 0 ){
							if ( isExist( T, s) == 0 ){ //去重
								T[ T_index++ ] = s;
							}
							s = "";
						}
					}else if ( !( temp_right.charAt(j) + "").matches("[A-Z]") ){ //运算符
						s = temp_right.charAt(j) + "";
						if ( isExist( T, s) == 0 ){ //去重
							T[ T_index++ ] = s;
						}
						s = "";
					}
			}
//				if ( ( T.indexOf( this.members[i].getRight().charAt(j) ) ) == -1 && !( this.members[i].getRight().charAt(j) + "" ).matches("[A-Z]") && !( this.members[i].getRight().charAt(j) + "" ).equals("ε")) {
//					T += this.members[i].getRight().charAt(j);
//				}
		}
		T[T_index] = "#"; //终结符加入#号
//		
		/*下面把字符串转为集合*/
		this.T = new String[ T_index + 1 ];
		for ( int i = 0; i <= T_index; i++ ) {
			this.T[i] = T[i] ;
//			System.out.print(this.T[i]+" ");
		}
		this.NT = new String[ NT.length() ];
		for ( int i = 0; i < NT.length(); i++ ) {
			this.NT[i] = NT.charAt(i) + "" ;
		}
		printTable();
		this.first = new String [this.NT.length];
		int fir_index = 0;
		this.follow = new String [ this.NT.length];
		int fol_index = 0;
		for ( int i = 0; i < this.NT.length; i++ ) {
			String first = "";
			for ( int j = 0; j < this.members.length; j++ ) {
				if ( this.members[j].getLeft().equals( this.NT[i] ) ) {
					for ( int k = 0; k < this.members[j].getFirst().length; k++) {
//						first += this.members[j].getFirst()[k] + " ";
						if ( first.indexOf( this.members[j].getFirst()[k] ) == -1 ) {
							first += this.members[j].getFirst()[k] + " ";
						}
					}
				}
			}
			this.first[ fir_index++ ] = first;
		}
		for ( int i = 0; i < this.NT.length; i++ ) {
			String follow = "";
			for ( int j = 0; j < this.members.length; j++ ) {
				if ( this.members[j].getLeft().equals( this.NT[i] ) ) {
					for ( int k = 0; k < this.members[j].getFollow().length; k++) {
						if ( follow.indexOf( this.members[j].getFollow()[k] ) == -1 ) {
							follow += this.members[j].getFollow()[k] + " ";
						}
					}
				}
			}
			this.follow[ fol_index++ ] = follow;
		}
//		for( int i = 0; i<this.first.length;i++){
//			System.out.println(this.first[i]  +"***"+ this.follow[i]);
//		}
	}
	
	public void printTable() {
		/*下面为构造预测表，放到二维数组table中*/
		this.table = new String[ this.NT.length ][ this.T.length ];
		for ( int i = 0; i < this.NT.length; i++) {  //在每一个非终结符号层循环
			for ( int s = 0; s < this.T.length; s++ ) {  //在每一个终结符号列循环
				int r = 0; //找到标识符
				for ( int j = 0; j < this.members.length; j++ ) {   //在每一个文法对象中循环
					if ( this.members[j].getLeft().equals( this.NT[i] ) ) { //如果该对象的左部和此层的非终结符相等
						if ( this.members[j].getRight().equals( "ε" ) ) {  //如果右部为空，则在follow集中查找
							for ( int k = 0; k < this.members[j].getFollow().length; k++ ) { //在对象的follow集中循环
								if ( this.members[j].getFollow()[k].equals( this.T[s] ) ) {  //如果此时的终结符和本列的终结符相等
									table[i][s] = this.members[j].getRight();  //把右部放入集合table中
									r = 1;  //找到
									break;
								}
							}
						}else {  //如果右部非空，则直接在first集合中查找
							for ( int k = 0; k < this.members[j].getFirst().length; k++ ) { //在对象的first集中循环
								if ( this.members[j].getFirst()[k].equals( this.T[s] ) ) { //如果此时的终结符和本列的终结符相等
									table[i][s] = this.members[j].getRight(); //把右部放入集合table中
									r = 1; //找到
									break;
								}
							}
						}
					}
				}
				if ( r == 0 ) {  //一次列的循环下来  若r仍为空  则没有任何匹配项，table中存“ ”
					table[i][s] = " ";
				}else {  //否则  ，重置r为0，为下一次循环做准备
					r = 0;
				}
				
			}
		}
		/*以下操作打印预测分析表*/
		System.out.println();
		System.out.println("预测分析表：");
		System.out.print("\t");
		for ( int i = 0; i < this.T.length; i++) {
			System.out.print( this.T[i] + "\t" );
		}
		System.out.println();
		for ( int i = 0; i < table.length ; i++) {
			System.out.print( this.NT[i] + "\t" );
			for ( int j = 0; j < table[i].length; j++ ) {
				if ( !table[i][j].equals(" "))
				{
					System.out.print( this.NT[i] + "::=" + table[i][j] + "\t" );
				}else {
					System.out.print( table[i][j] + "\t" );
				}
			}
			System.out.println();
		}
	}
	
	public String[] removeLeftRecursion( String G_left[], String G_right[] ) { //消去左递归
		int len = G_left.length;
		String GG[][] = new String[len][5]; //化文法为二维数组
		String GG_isLR[][] = new String[len][5];//是否存在左递归
		String G_isLR[] = new String[len];
		for ( int i = 0; i < len; i++ ) {
			int GGindex = 0;
			int begin_index = 0;
			int end_index = G_right[i].indexOf( "|" );
			int r = 0;
			while ( end_index != -1 ) {
				GG[i][GGindex ] = G_right[i].substring(begin_index, end_index);  //截取文法
				if ( ( G_right[i].charAt(begin_index) + "" ).equals( G_left[i] ) ) { //是否是左递归形式
					GG_isLR[i][ GGindex ] = "Y";
					r = 1;
				}else{  //不是左递归
					GG_isLR[i][ GGindex ] = "N";
				}
				GGindex++;
				begin_index = end_index + 1;
				end_index = G_right[i].indexOf( "|", begin_index ); //从begin_index开始搜索|
			}
			GG[i][ GGindex ] = G_right[i].substring( begin_index ); //末尾文法
			if ( ( G_right[i].charAt(begin_index) + "" ).equals( G_left[i] ) ) { //是否存在左递归
				GG_isLR[i][ GGindex ] = "Y";
				r = 1;
			}else{  //不是左递归
				GG_isLR[i][ GGindex ] = "N";
			}
			if ( r == 1 ) {  //整个这个文法是否存在左递归
				G_isLR[i] = "Y";
			}else {
				G_isLR[i] = "N";
			}
		}
		String new_G[] = new String[10];
		int G_index = 0;
		for ( int i = 0; i < G_left.length; i++ ) {
			String temp = "";
			String temp2 = "";
			if ( G_isLR[i].equals( "Y" ) ) {//存在左递归
				char new_left = (char) (G_left[i].charAt(0) + 1); //新的左部
				int r = 0;
				for( int k = 0; k < G_left.length; k++) {  //判断新的左部是否已经存在于文法中
					if ( G_left[k].equals( new_left + "" ) ) {
						r = 1;  //已存在
						break;
					}
				}
				while ( r == 1) {  //继续产生左部
					r = 0;
					new_left = (char) ( new_left + 1); //继续产生新的左部
					for( int k = 0; k < G_left.length; k++) {
						if ( G_left[k].equals( new_left ) ) {
							r = 1;
							break;
						}
					}
				}
				temp += new_left + "::="; //新的文法
				temp2 += G_left[i] + "::=";  //旧的文法
				for ( int j = 0; GG[i][j] != null; j++ ) {
					if ( GG_isLR[i][j].equals( "Y" ) ) { //存在左递归的子文法
						temp += GG[i][j].substring(1) + new_left + "|"; //化作E'::=+tE'形式
					}else {
						temp2 += GG[i][j] + new_left + "|";  //不存在左递归的子文法化作E::=fE'形式
					}
				}
				temp += "ε"; //新文法E'加入ε 变为E'::=+tE'|ε形式
				temp2 = temp2.substring( 0, temp2.length()-1 ); //去掉最后的|
				new_G[ G_index++ ] = temp2; //加入文法数组里
				new_G[ G_index++ ] = temp;
			}else { //不存在左递归
				String t = G_left[i] + "::=" + G_right[i];
				new_G[ G_index++ ] = t;
			}
		}
		String now_G[] = new String[ G_index ];
		for ( int i = 0; i < now_G.length; i++ ) {
			now_G[i] = new_G[i];
	//		System.out.println( now_G[i] );
		}
		System.out.println();
		return now_G;
	}
	
	public void getLeftRight( String G[] ) {

		int len = G.length;
		int sum = 0;
		
		//计算总文法的数量
		for( int i = 0; i<len; i++ ) {
			int temp = getTotalOfG( G[i] );
			if( temp == 0 ) {
				sum++;
			}else {
				sum += temp+1;
			}
		}
		
		//申请空间
		this.left = new String[sum];
		this.right = new String[sum];
		
		int k=0;
		
		//遍历文法G，若没有|则表示只有一个右部，直接分离出左右部
		//若存在|则表示有多个右部，则需要先将右部分离成多个文法句子
		//再依次进行左右部的分离
		for( int i = 0; i < len; i++) {
			int index = G[i].indexOf("|");
			if( index == -1 ) {
				this.left[k] = getLeftPart( G[i] );
				this.right[k] = getRightPart( G[i] );
				k++;
			}else {
				String str[] = getPeerGG( getRightPart( G[i] ) );
				int size = str.length;
				String temp = G[i];
				for( int j = 0; j < size; j++ ) {
					this.left[k] = getLeftPart( temp );
					this.right[k] = str[j];
					k++;
				}
			}
		}
		this.members = new Model[ this.left.length ];
		for ( int i = 0; i < this.members.length; i++) {
			this.members[i] = new Model();
		}
		for ( int i = 0; i < this.left.length; i++ ) {
			this.members[i].setLeft( this.left[i] );
			this.members[i].setRight( this.right[i] );
		}
	}
	
	//分离复合文法
	public String[] getPeerGG(String GG) {
		int preLen,nowLen;//GG字符串前后变换的长度
		String string[];
		int number = getTotalOfG(GG);
		string = new String[number+1]; //文法的个数
		int index1 = 0;
		for(int i = 0; i<number; i++) {
			int index2 = GG.indexOf("|", index1); //搜索第一个|的下标
			string[i] = GG.substring(index1, index2);
			index1 = index2+1;
		}
		string[number] = GG.substring(index1);
		return string;
	}
	
	//返回文法的总个数
	public int getTotalOfG(String GG) {
		int number = 0;
		for( int i = 0; i < GG.length(); i++) {
			if(GG.charAt(i)=='|') {
				number++;
			}
		}
		return number;
	}
	
	//分离出左部
	public String getLeftPart(String GG) {
		return GG.charAt(0)+"";
	}
	
	//分离出右部
	public String getRightPart(String GG) {
		if( ( GG.charAt(1) + "" ).equals("-") ) { //若输入的文法为E->xxxx格式
			return GG.substring(3);
		}else {  //否则应为E::=xxxx格式
			return GG.substring(4);
		}
	}
	
	public String[] setFirst( String GG ) {
//		String s = "";
		String str[] = new String[50];
		int str_index = 0;
		int r = 0 ;
		int index;
		for ( index = 0; index < GG.length(); index++ ){
			if ( !( GG.charAt(index) + "" ).matches("[A-Z]") ){
				r = 1;
				break;
			}
		}
//		if ( !( GG.charAt(0) + "" ).matches("[A-Z]") || ( GG.charAt(0) + "" ).equals( "ε" ) ) { //如果为终结符
//			if ( s.indexOf( GG.charAt(0) + "" ) == -1) { //去重，不存在的非终结符才加到字符串中
//				s += GG.charAt(0) + "";
//			}
//		}
		if ( r == 1 ){  //存在终结符
			if ( !( GG.charAt(index) + "" ).matches("[a-zA-Z]") ){ //是运算符或者空
				int isExist = isExist( str, GG.charAt(index) + "" ); //不存在再加入到数组中，去重
				if ( isExist == 0 ){
					str[ str_index++ ] = GG.charAt(index) + "";
				}
			}else if ( ( GG.charAt(index) + "" ).matches("[a-z]") ){  //是小写字母的终结符
				String s = "";
				for ( int i = index; i < GG.length(); i++ ){
					if ( ( GG.charAt(i) + "" ).matches("[a-z]") ){ //是终结符
						s += GG.charAt(i);
					}else{
						break;
					}
				}
				int isExist = isExist( str, s ); //判断数组是否已经包含s
				if ( isExist == 0 ){
					str[ str_index++ ] = s;
				}
			}
			
//			if ( s.indexOf( GG.charAt(index) + "" ) == -1) { //去重，不存在的非终结符才加到字符串中
//				s += GG.charAt(index) + "";
//			}
		}else { //没有终结符
			String temp[] = getNTFirst( GG.charAt(0) + "" ); //递归寻找终结符
			for( int j = 0; j < temp.length; j++) {
				if ( temp[j] != null) {
					if ( isExist( str, temp[j] ) == 0 ) {  //是否包含temp[j]
						str[ str_index++ ] = temp[j];
					}
				}
			}
		}
		String new_str[] = new String[ str_index ];
		for ( int i = 0; i < str_index; i++ ){
			new_str[i] = str[i];
		}
		return new_str;
	}
	
	public String[] getNTFirst( String GG ) {
		int leftLen = this.left.length;
//		String s = "";
		String str[] = new String[50];
		int str_index = 0;
		for ( int i = 0; i < leftLen; i++) {
			if ( this.left[i].equals(GG) ) {
//				if ( !( right[i].charAt(0) + "" ).matches("[A-Z]") || ( right[i].charAt(0) + "" ).equals( "ε" ) ) { //如果为小写 则为终结符
//					if ( s.indexOf( right[i].charAt(0) + "" ) == -1) { //去重，不存在的非终结符才加到字符串中
//						s += right[i].charAt(0) + "";
//					}
				String temp_str[] = setFirst(right[i]);
				for ( int j = 0; j < temp_str.length; j++ ){
					if ( isExist( str, temp_str[j] ) == 0 ){
						str[ str_index++ ] = temp_str[j];
					}
				}
//				int index;
//				int r = 0;
//				for ( index = 0; index < right[i].length(); index++ ){
//					if ( !( right[i].charAt(index) + "" ).matches("[A-Z]") ){
//						r = 1;
//						break;
//					}
//				}
//				if ( r == 1 ){  //存在终结符
//					if ( s.indexOf( right[i].charAt(index) + "" ) == -1) { //去重，不存在的非终结符才加到字符串中
//						s += right[i].charAt(index) + "";
//					}
//				}
//				else { //不存在终结符
//					String temp[] = getNTFirst( right[i].charAt(0) + "" ); //递归寻找终结符
//					for( int j = 0; j < temp.length; j++) {
//						if ( temp[j] != null) {
//							if ( s.indexOf( temp[j] ) == -1) {
//								s += temp[j];
//							}
//						}
//					}
//				}
			}
		}
		String new_str[] = new String[ str_index ];
		for( int i = 0; i < str_index; i++) { //将字符串转换为数组
			new_str[i] = str[i];
		}
		return new_str;
	}
	
	public String[] setFollow( String GG ) {
		int rightLen = this.right.length;
//		String s = "";
		String str[] = new String[50];
		int str_index = 0;
		if ( GG.equals( this.left[0] ) ) {  //如果为起始符号
			if ( isExist( str, "#" ) == 0 ) {
				str[ str_index++ ] = "#";
			}
		}
		for ( int i = 0; i < rightLen; i++ ) {  //遍历所有文法右部
			int index = this.right[i].indexOf(GG);  //是否包含GG非终结符
			if ( index != -1) { //包含
				if ( index == this.right[i].length()-1 ) {//是否为最后一位
					if ( isExist( str, "#" ) == 0 ) {
						str[ str_index++ ] = "#";
					}
					//求左部的Follow
					if ( !this.left[i].equals( GG )) {
						String temp[] = setFollow( this.left[i] ); //求左部
						for ( int q = 0; q < temp.length; q++) {
							if ( isExist( str, temp[q] ) == 0 ) {
								str[ str_index++ ] = temp[q];
							}
						}
					}
				}else { //不是最后一位
					int new_index = index + 1;
					while( ( right[i].charAt( new_index ) + "" ).equals(" ") ){
						new_index++;
					}
					if ( !( right[i].charAt( new_index ) + "" ).matches("[A-Z]") ) {//后跟终结符号
						if ( !( right[i].charAt( new_index ) + "" ).matches("[a-z]") ){ //后跟运算符
							if ( isExist( str, right[i].charAt( new_index ) + "" ) == 0 ) {
								str[ str_index++ ] = right[i].charAt( new_index ) + "";
							}
						}else{//后跟单词
							String s = "";
							for ( int k = new_index; k < right[i].length(); k++ ){
								if ( ( right[i].charAt(k) + "" ).matches("[a-z]") ){ //是终结符
									s += right[i].charAt(k);
								}else{
									break;
								}
							}
							int isExist = isExist( str, s ); //判断数组是否已经包含s
							if ( isExist == 0 ){
								str[ str_index++ ] = s;
							}
						}
//						if ( s.indexOf( right[i].charAt( index+1 ) + "" ) == -1 ) {
//							s += right[i].charAt( index+1 ) + "";
//						}
					}else { //后跟非终结符
						for (int j = 0; j < this.members.length; j++) {
							if ( this.members[j].getLeft().equals( right[i].charAt( index+1 ) + "" ) ) {
								for ( int k = 0; k < this.members[j].getFirst().length; k++) { //把该终结符号的first集赋值给s
									if ( ( isExist( str, this.members[j].getFirst()[k] ) == 0 ) && !this.members[j].getFirst()[k].equals( "ε" ) ) {
										str[ str_index++ ] =  this.members[j].getFirst()[k];
									}
								}
								if ( this.members[j].getIsNull().equals( "Y" ) ) { //若该符号中包含空，则再加上此时左部的follow集
									String leftFollow[];
									if ( this.members[i].getFollow() == null ){  //左部follow
										leftFollow = setFollow( this.members[i].getLeft() );
									}else{
										leftFollow = this.members[i].getFollow();
									}
									for ( int u = 0; u < leftFollow.length; u++) {
										if ( leftFollow[u] != null && isExist( str, leftFollow[u] ) == 0 )
											str[ str_index++ ] = leftFollow[u];
									}
								}
							}
						}
					}
				}
			}
		}
		String new_str[] = new String[ str_index ];
		for ( int i = 0; i < str_index; i++ ){
			new_str[i] = str[i];
		}
		return new_str;
	}
	
//	public String getSym( String string, int index_begin){
//		String str = "";
//		int len = string.length();
//		for ( int i = index_begin; i < len; i++ ){
//			if ( !( string.charAt(i) + "" ).equals( " " ) ){
//				str += string.charAt(i);
//			}
//		}
//	}
	
	public int isExist( String str[], String s ){
		for ( int i = 0; i < str.length && str[i]!=null; i++ ){
			if ( str[i].equals(s) ){
				return 1;
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "FirstFollowSelect [left=" + Arrays.toString(left) + ", right=" + Arrays.toString(right) + "]";
	}

	public String[] getLeft() {
		return left;
	}

	public void setLeft(String[] left) {
		this.left = left;
	}

	public String[] getRight() {
		return right;
	}

	public void setRight(String[] right) {
		this.right = right;
	}
	
	public String[] getT() {
		return T;
	}

	public void setT(String[] t) {
		T = t;
	}

	public String[] getNT() {
		return NT;
	}

	public void setNT(String[] nT) {
		NT = nT;
	}

	public String[][] getTable() {
		return table;
	}

	public void setTable(String[][] table) {
		this.table = table;
	}
	
	public String[] getNoLeftG() {
		return noLeftG;
	}

	public void setNoLeftG(String[] noLeftG) {
		this.noLeftG = noLeftG;
	}

	public Model[] getMembers() {
		return members;
	}

	public void setMembers(Model[] members) {
		this.members = members;
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

	public static void main(String[] args) {
		Scanner scan = new Scanner( System.in );
		int choose = scan.nextInt();
		if ( choose == 0 ){ //i+i*i式文法
			
		}else{  //if else 式文法
			String str[] ={
					"E::=if E else T|true",
					"T::=if then F|T,F",
					"F::=end|E end"
			};
			new YuceTable_if_then( str );
		}
	}
}
