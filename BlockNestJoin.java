package join;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class BlockNestJoin {
	public static final int R_BLOCK_NUMBER = 5;
	public static final int S_BLOCK_NUMBER = 10;
	public static final int R_BLOCK_SIZE = 10;
	public static final int S_BLOCK_SIZE = 12;
	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("Relation_R.txt");
		InputStreamReader in = new InputStreamReader(fileInputStream);
		LineNumberReader lnrR = new LineNumberReader(in);
		fileInputStream = new FileInputStream("Relation_S.txt");
		in = new InputStreamReader(fileInputStream);
		LineNumberReader lnrS = new LineNumberReader(in);
	    String [] temp = new String[2];
		String [] s1 = new String[R_BLOCK_SIZE];
		String [] s2 = new String[S_BLOCK_SIZE];
		for (int i = 0; i < R_BLOCK_NUMBER; i++) {//5 读R的一个块 即10条记录
			lnrR.setLineNumber(i*R_BLOCK_SIZE);
			for(int n=0;n<R_BLOCK_SIZE;n++) {
				s1[n]=lnrR.readLine();
			}
			for (int j = 0; j < S_BLOCK_NUMBER; j++) {//10 读S的一个块 即12条记录
				lnrS.setLineNumber(j*S_BLOCK_SIZE);
				for(int m=0;m<S_BLOCK_SIZE;m++) {
					s2[m]=lnrS.readLine();
				}
				for (int p = 0; p < R_BLOCK_SIZE; p++) {//10 块R中的元组
					for (int q = 0; q < S_BLOCK_SIZE; q++) {//12 块S中的元组
						 if (join(s1[p],s2[q])) {
							 temp = s2[q].split(" ");
						  System.out.println(s1[p] + " " +temp[1]);
						}
					}
				}
			}
			fileInputStream = new FileInputStream("Relation_S.txt");
			in = new InputStreamReader(fileInputStream);
			lnrS = new LineNumberReader(in);
			
		}
		lnrR.close();
	    lnrS.close();
	}
	public static boolean join(String s1,String s2) {
		String [] temp1 = new String[2];
		String [] temp2 = new String[2];
		temp1 = s1.split(" ");
		temp2 = s2.split(" ");
		if(temp1[0].equals(temp2[0])) {
			return true;
		}
		return false;
	}
}
