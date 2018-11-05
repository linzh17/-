package BitMap;

import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;
import java.util.Vector;
import BitMap.Jdbc;
import BitMap.Bitmap;

public class Table {

	public static final int testdatasize = 10;// 初始测试数据的大小！！！一定要对应真实记录数
	public static int lastRecordIndex = testdatasize;// 当前记录数
	public static Vector<Integer> testdataage;// 年龄的模拟字段
	public static Vector<Integer> testdatamoney;// 薪金的模拟字段
	public static Vector<Integer> deleterow;// 删除标记
    public static Vector<String> compression;
	public static void main(String[] args) {
		Table table = new Table();
		Jdbc jdbc = new Jdbc();
		jdbc.initTestData(testdataage, testdatamoney);
		//如果数据库里没有索引就新建
		Bitmap age = new Bitmap("age", testdataage, testdatasize);
		System.out.println("列age索引生成完毕"); 
		Bitmap money = new Bitmap("money", testdatamoney, testdatasize);
		System.out.println("列money索引生成完毕");	 
		//如果有就解压生成索引
		
		try {                  //查询，插入，删除操作
//			table.Findone(age);
		table.Find(age, money);
			// table.Insert(age, money);
			// table.Delete(age, money);
			// age.display(lastRecordIndex);
			// money.display(lastRecordIndex);
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!jdbc.tableCompressionHasData(age.name)) {//最后压缩存进数据库
			age.compression(lastRecordIndex,compression);
			jdbc.insertTableCompression(age.name, compression);	
		}
		if (!jdbc.tableCompressionHasData(money.name)) {
			money.compression(lastRecordIndex,compression);
			jdbc.insertTableCompression(money.name, compression);
		}

	}

	public Table() {
		deleterow = new Vector<Integer>();
		testdataage = new Vector<Integer>();
		testdatamoney = new Vector<Integer>();
		compression = new Vector<String>();
	}

	public boolean Find(Bitmap a, Bitmap b) throws IOException {
		System.out.print("请输入两个值：");
		Scanner sc = new Scanner(System.in);
		int temp1 = sc.nextInt();
		int temp2 = sc.nextInt();
		int temp3 = a.total.indexOf(temp1);
		int temp4 = b.total.indexOf(temp2);
		sc.close();
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
		Scanner sc = new Scanner(System.in);
		int temp1 = sc.nextInt();
		int temp3 = a.total.indexOf(temp1);
		sc.close();
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
		/* 现在是两个值，到时候可以输入一条记录，然后做拆分 */
		System.out.print("请输入两个值：");
		Scanner sc = new Scanner(System.in);
		int temp1 = sc.nextInt();
		int temp2 = sc.nextInt();
		int temp3 = a.total.indexOf(temp1);
		int temp4 = b.total.indexOf(temp2);
		sc.close();
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
		System.out.print("请输入要删除的第n条记录：");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
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
