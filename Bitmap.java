package BitMap;

import java.util.*;
public class Bitmap {

	private static final int bitsize = 100;//位向量的长度相当于记录数
	public String name;//索引名
	public Vector<Integer> total;
	public BitSet bitmap [];
	public Bitmap(String n, Vector<Integer> testdata,int size) {
        name = n;
		total = new Vector<Integer>();
		bitmap = new BitSet[100];//估计100个取值
		
		for(int i=0;i<100;i++) {
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
	
	public void display(int index) {
		for(int j=0;j<total.size();j++) {
			System.out.print(total.get(j).toString() + ": " );
			for(int k=0;k<index;k++) {
				System.out.print(bitmap[j].get(k) + " ");
			}
			System.out.println();
		}
	}
	//1、统计0的个数i，计算要j位二进制数才能表示i，j-1个1和一个0后面再跟对应i的二进制表示
//	public void compression(int index) throws FileNotFoundException {
//		int i = 0;
//		int j = 0;
//		String result;
//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("compressionBitmap.txt"));
//		for(int p=0;p<total.size();p++) {
//			for(int q=0;q<index;q++) {
//				if(bitmap[p].get(q)==false) {
//					i++;
//				}
//				else {
//					j=(int)(Math.log(i)/Math.log(j))+1;
//					result = Integer.toBinaryString(i);
//					result.toCharArray();
//					i=0;
//				}
//			}
//		}
//	}
//	public void  discompression() {
//		
//	}
}
