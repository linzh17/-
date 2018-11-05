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
	public void insertTableCompression(String name,Vector<String> result) {
		String sql = "insert into compression(" + name + ")values (?)";
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			for(int i=0;i<result.size();i++) {
			    ps.setString(1, result.get(i));
				ps.executeUpdate();
			}
			ps.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	public boolean tableCompressionHasData(String indexname) {
		String sql = "select top 1 " + indexname + " from compression";
		boolean hasdata = false;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				rs.getObject(1);
				if (rs.wasNull()) {
					hasdata = false;
				} else {
					hasdata = true;
				}
			}
			st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		return hasdata;
	}
	public void close() {
		try {
			rs.close();
			conn.close();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
}
