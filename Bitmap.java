package BitMap;

import java.util.*;

public class Bitmap {

	private static final int bitsize = 10005;//位向量的长度相当于记录数留5个空位来演示插入
	private static final int totalResult = 7000;//估计取值数
	public String name;//索引名
	public Vector<Integer> total;
	public BitSet bitmap [];
	public Bitmap(String n, Vector<Integer> testdata,int size) {
        name = n;
		total = new Vector<Integer>();
		bitmap = new BitSet[totalResult];
		
		for(int i=0;i<totalResult;i++) {
			bitmap[i] = new BitSet(bitsize);
		}
		
		int temp,index;
		
		for(int j=0;j<size;j++) {
			temp = testdata.get(j);
			index=total.indexOf(temp);//根据取值去找对应下标
			if(index!=-1) {
				bitmap[index].set(j, true);//如果total里面有就在对应的bitmap的下标设置为true
			}
			else {
				total.add(temp);//没有就加入到total
				bitmap[total.size()-1].set(j,true);
			}
		}
	}
	
	public Bitmap(String n) {
        name = n;
		total = new Vector<Integer>();
		bitmap = new BitSet[totalResult];
		
		for(int i=0;i<totalResult;i++) {
			bitmap[i] = new BitSet(bitsize);
		}	
	
	}
	public void display(int index) {
		for(int j=0;j<total.size();j++) {
			System.out.print(total.get(j).toString() + ": " );
			for(int k=0;k<index;k++) {
				System.out.print(bitmap[j].get(k) + " ");
			}
			System.out.println();
		}
	}
	//统计0的个数i，计算要j位二进制数才能表示i，j-1个1和一个0后面再跟对应i的二进制表示
	public void compression(int index,Vector<String> vector)  {
		int i = 0;
		int j = 0;
		String result = "";
		String temp;
		
		vector.clear();
		System.out.println("正在压缩" + name +"索引");
		for(int p=0;p<total.size();p++) {
			for(int q=0;q<index;q++) {
				if(bitmap[p].get(q)==false) {
					i++;
				}
				else {
					j=(int)(Math.log(i)/Math.log(2))+1;
					temp = Integer.toBinaryString(i);
					temp = "0" + temp;
				       for(int z=0;z<j-1;z++) {
				    	   temp = "1" + temp;
				       }
				       result = result + temp;
					i=0;
				}
			}
			vector.add(result);
			i=0;
			result = "";
		}
		System.out.println("压缩完毕");
	}
	//统计共有多少个1 再加一个0 = i 表示后i位为一个二进制数 j 加j个0后补个1 
	public void  discompression(Vector<String> vector) {
		String result;
		String temp;
		int j = 0;
		int i = 0;
		int onecount = 0;//1的个数
		System.out.println("正在解压" + name +"索引");
		for(int p=0;p<total.size();p++) {
			result = vector.get(p);
			for(int q=0;q<result.length();) {
				if(result.charAt(q)=='1') {
					i++;
					q++;
				}
				else {
					i++;
					temp = result.substring(q+1, q+i+1);
					j = j + Integer.parseInt(temp,2);//bug
					bitmap[p].set(j+onecount);
					q=q+i+1;
					i=0;
					onecount++;
				}
			}
			j=0;
			onecount=0;
		}
		System.out.println("解压完毕");
	}
	public void clear() {
		for(int i=0;i<totalResult;i++) {
			bitmap[i].clear();
		}	
		total.clear();
	}
}
