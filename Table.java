package BitMap;

import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;
import java.util.Vector;
import BitMap.Jdbc;
import BitMap.Bitmap;

public class Table {

	public static final int testdatasize = 10000;// 初始测试数据的大小！！！一定要对应真实记录数
	public static int lastRecordIndex = testdatasize;// 当前记录数
	public static Vector<Integer> testdataage;// 年龄的模拟字段
	public static Vector<Integer> testdatamoney;// 薪金的模拟字段
	public static Vector<Integer> deleterow;// 删除标记
    public static Vector<String> compression;
    private static final Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		//新建表+数据
		Table table = new Table();
		Jdbc jdbc = new Jdbc();
		jdbc.initTestData(testdataage, testdatamoney);
		//根据数据建索引
		Bitmap age = new Bitmap("age", testdataage, testdatasize);
		System.out.println("列age索引生成完毕");

		Bitmap money = new Bitmap("money", testdatamoney, testdatasize);
		System.out.println("列money索引生成完毕");
		 
		//查询，插入，删除操作（默认查询）
		try {             
			// table.Findone(age);
			 table.Find(age, money);
			// table.Insert(age, money);
//			table.Delete(age, money);
			// age.display(lastRecordIndex);做小型演示用
			// money.display(lastRecordIndex);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//压缩存进数据库
			age.compression(lastRecordIndex,compression);
			jdbc.insertTableCompression(age.name, compression, age.total);	
		
			money.compression(lastRecordIndex,compression);
			jdbc.insertTableCompression(money.name, compression, money.total);	
		//解压再拿出来          	
          age.clear();
          jdbc.getFromCompressionTable(age, compression);
          age.discompression(compression);
          	         
          money.clear();
          jdbc.getFromCompressionTable(money, compression);
          money.discompression(compression);    	
		//再一次查询验证解压的到底对不对
		try {
			table.Find(age, money);
		} catch (Exception e) {
			e.printStackTrace();
		}
			jdbc.close();
			sc.close();
	}

	public Table() {
		deleterow = new Vector<Integer>();
		testdataage = new Vector<Integer>();
		testdatamoney = new Vector<Integer>();
		compression = new Vector<String>();
	}

	public boolean Find(Bitmap a, Bitmap b) throws IOException {
		System.out.print("请输入两个值：");
		int temp1 = sc.nextInt();
		int temp2 = sc.nextInt();
		int temp3 = a.total.indexOf(temp1);
		int temp4 = b.total.indexOf(temp2);
		if (temp3 != -1 && temp4 != -1) {
			BitSet bs = (BitSet) a.bitmap[temp3].clone();
			bs.and(b.bitmap[temp4]);
			if (bs.isEmpty()) {
				System.out.println("没有找到相应的记录");
				return false;
			}
			for (int i = 0; i < lastRecordIndex; i++) {
				if (bs.get(i) == true) {
					System.out.println("找到记录在第" + (i + 1) + "行");
				}
			}
			return true;
		}
		System.out.println("没有找到相应的记录");
		return false;

	}
	public boolean Findone(Bitmap a) throws IOException {
		System.out.print("请输入一个值：");
		int temp1 = sc.nextInt();
		int temp3 = a.total.indexOf(temp1);
		if (temp3 != -1) {
			if (a.bitmap[temp3].isEmpty()) {
				System.out.println("没有找到相应的记录");
				return false;
			}
			for (int i = 0; i < lastRecordIndex; i++) {
				if (a.bitmap[temp3].get(i) == true) {
					System.out.println("找到记录在第" + (i + 1) + "行");
				}
			}
			return true;
		}
		System.out.println("没有找到相应的记录");
		return false;

	}
	public void Insert(Bitmap a, Bitmap b) throws IOException {
		//指的是插入到索引里
		System.out.print("请输入两个插入值：");
		int temp1 = sc.nextInt();
		int temp2 = sc.nextInt();
		int temp3 = a.total.indexOf(temp1);
		int temp4 = b.total.indexOf(temp2);
		if (temp3 != -1) {
			a.bitmap[temp3].set(lastRecordIndex, true);
		} else {
			a.total.add(temp1);// 没有就加入到total
			a.bitmap[a.total.size() - 1].set(lastRecordIndex, true);
		}
		if (temp4 != -1) {
			b.bitmap[temp4].set(lastRecordIndex, true);
		} else {
			b.total.add(temp2);
			b.bitmap[b.total.size() - 1].set(lastRecordIndex, true);
		}
		lastRecordIndex++;
		testdataage.add(temp1);
		testdatamoney.add(temp2);
	}

	public void Delete(Bitmap a, Bitmap b) throws IOException {
		//从索引里删除
		System.out.print("请输入要删除的第n条记录：");
		int n = sc.nextInt();
		if (n > lastRecordIndex || n < 1) {
			System.out.println("输入的记录号超出范围");
			return;
		}
		int temp1 = testdataage.get(n - 1);
		int temp2 = testdatamoney.get(n - 1);
		int temp3 = a.total.indexOf(temp1);
		int temp4 = b.total.indexOf(temp2);
		a.bitmap[temp3].set(n - 1, false);
		b.bitmap[temp4].set(n - 1, false);
		deleterow.add(n);
	}// 可以增加一个管理函数，当进行了多次增删改查时，把位向量全为false的删掉
}
