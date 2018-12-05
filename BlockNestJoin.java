package join;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;

public class BlockNestJoin {
	public static final int MEMORY_SIZE = 11;//缓冲区大小
	public static final int TOTAL_TUPLES_R = 8000;//R关系总记录数
	public static final int TOTAL_TUPLES_S = 15000;//S关系总记录数
	public static final int R_BLOCK_SIZE = 100;//R的一个块能装的记录数
	public static final int S_BLOCK_SIZE = 150;//S的一个块能装的记录数
	public static final int R_BLOCK_NUMBER = TOTAL_TUPLES_R/R_BLOCK_SIZE;//R关系包含的块数
	public static final int S_BLOCK_NUMBER = TOTAL_TUPLES_S/S_BLOCK_SIZE;//S关系包含的块数
	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("join_result.txt",true)));
		FileInputStream fileInputStream = new FileInputStream("Relation_R.txt");
		InputStreamReader in = new InputStreamReader(fileInputStream);
		LineNumberReader lnrR = new LineNumberReader(in);
		fileInputStream = new FileInputStream("Relation_S.txt");
		in = new InputStreamReader(fileInputStream);
		LineNumberReader lnrS = new LineNumberReader(in);
	    String [] temp = new String[2];
		String [] s1 = new String[R_BLOCK_SIZE*(MEMORY_SIZE-1)];
		String [] s2 = new String[S_BLOCK_SIZE];
		for (int i = 0; i < R_BLOCK_NUMBER/(MEMORY_SIZE-1); i++) {//5 读R的10个块 即10*100条记录 
			lnrR.setLineNumber(i*R_BLOCK_SIZE*(MEMORY_SIZE-1));
			for(int n=0;n<R_BLOCK_SIZE*(MEMORY_SIZE-1);n++) {
				s1[n]=lnrR.readLine();
			}
			for (int j = 0; j < S_BLOCK_NUMBER; j++) {//10 读S的一个块 即150条记录
				lnrS.setLineNumber(j*S_BLOCK_SIZE);
				for(int m=0;m<S_BLOCK_SIZE;m++) {
					s2[m]=lnrS.readLine();
				}
				for (int p = 0; p < R_BLOCK_SIZE*(MEMORY_SIZE-1); p++) {//10 块R中的元组
					for (int q = 0; q < S_BLOCK_SIZE; q++) {//12 块S中的元组
						 if (join(s1[p],s2[q])) {
							 temp = s2[q].split(" ");
						  bufferedWriter.write(s1[p] + " " +temp[1]);
						  bufferedWriter.newLine();
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
	    bufferedWriter.close();
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
