package BitMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Jdbc {
	Connection conn = null;
	ResultSet rs = null;
	
	String url ="jdbc:sqlserver://localhost:1433;databaseName=SALES";
	String user = "sa";
	String password="123321";
	public Jdbc() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("驱动加载成功");
		
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("连接数据库成功");
		}catch(Exception e) {
				e.printStackTrace();
			}
	}
	public void initTestData(Vector<Integer> a,Vector<Integer> b) {
		String sql = "select age, money from test";
		try {
		Statement st = conn.createStatement();
		rs = st.executeQuery(sql);
			while (rs.next()) {
				a.addElement(rs.getInt(1));
				b.addElement(rs.getInt(2));
			}
			st.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertTableCompression(String name,Vector<String> result,Vector<Integer> total) {
		String tablename = name + "compression";
		String createsql = "create table " + tablename +"( value int,C_bitmap varchar(8000) )";
		String insertsql = "insert into " + tablename + "( value,C_bitmap ) values (?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(createsql);
			ps.executeUpdate();
			ps = (PreparedStatement) conn.prepareStatement(insertsql);
			for(int i=0;i<result.size();i++) {
				ps.setInt(1, total.get(i));
			    ps.setString(2, result.get(i));
				ps.executeUpdate();
			}
			ps.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
//	public boolean tableCompressionHasData(String indexname) {
//		String sql = "select top 1 " + indexname + " from compression";
//		boolean hasdata = false;
//		try {
//			Statement st = conn.createStatement();
//			rs = st.executeQuery(sql);
//			if (rs.next()) {
//				rs.getObject(1);
//				if (rs.wasNull()) {
//					hasdata = false;
//				} else {
//					hasdata = true;
//				}
//			}
//			st.close();
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		return hasdata;
//	}
	public void close() {
		try {
			rs.close();
			conn.close();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
//	public boolean tableExist(String tableName){
//		boolean flag = false;
//		try {
//			DatabaseMetaData meta = conn.getMetaData();
//			String type [] = {"TABLE"};
//			rs = meta.getTables(null, null, tableName, type);
//			flag = rs.next();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	 }
	public void getFromCompressionTable(Bitmap a, Vector<String> compression) {
		String tablename = a.name + "compression";
		String sql = "select value,C_bitmap from " + tablename ;
		compression.clear();
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
				while (rs.next()) {
					a.total.add(rs.getInt(1));
					compression.add(rs.getString(2));
				}
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
