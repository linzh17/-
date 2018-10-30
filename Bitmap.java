package BitMap;
import java.util.*;
import BitMap.Table;
public class Bitmap {

	private static final int bitsize = 100;//位向量的长度
	public String name;//索引名
	public Vector<Integer> total;
	public BitSet bitmap [];
	public Bitmap(String n, Vector<Integer> testdata) {
        name = n;
		total = new Vector<Integer>();
		bitmap = new BitSet[100];//估计100个取值
		
		for(int i=0;i<100;i++) {
			bitmap[i] = new BitSet(bitsize);
		}
		
		int temp,index;
		
		for(int j=0;j<Table.testdatasize;j++) {
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
	
	public void display() {
		for(int j=0;j<total.size();j++) {
			System.out.print(total.get(j).toString() + ": " );
			for(int k=0;k<Table.lastRecordIndex;k++) {
				System.out.print(bitmap[j].get(k));
			}
			System.out.println();
		}
	}
}
