package BitMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Jdbc {
	public Jdbc(Vector<Integer> a,Vector<Integer> b) {
		Connection conn = null;
		ResultSet rs = null;
		
		String url ="jdbc:sqlserver://localhost:1433;databaseName=SALES";
		String user = "sa";
		String password="123321";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("驱动加载成功");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("连接数据库成功");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			Statement st = conn.createStatement();
			String sql = "select age, money from test";
			rs = st.executeQuery(sql);
			while(rs.next()) {
			a.addElement(rs.getInt(1));
			b.addElement(rs.getInt(2));
			}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
}
