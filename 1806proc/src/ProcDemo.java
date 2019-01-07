import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.oracore.OracleType;

public class ProcDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dirver = "oracle.jdbc.OracleDriver"; // 驱动
		String url = "jdbc:oracle:thin:@localhost:1521:ORCL";// 连接地址 Oracle瘦连接 ORCL： service id
		String username = "scott";
		String password = "1234";
		Connection conn=null;
		CallableStatement cs=null;
		ResultSet rs=null;
		try {
			// 加载驱动
			Class.forName(dirver);
			// 获得连接
			 conn = DriverManager.getConnection(url, username, password);
			// 创建存储过程调用对象 ？ 表示占位
			 cs = conn.prepareCall("{call p_180604(?,?)}");
			// 设置入参 第一个？ 位置 写2000 钱数用double
			cs.setDouble(1, 2000);
			// 设置出参 在第二个?位置 返回游标类型
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			// 执行存储过程
			cs.execute();
			// 在第二个?位置 获得返回游标
			 rs = (ResultSet) cs.getObject(2);
			while (rs.next()) {
				System.out.println(rs.getString("ename") + "、" + rs.getDouble("sal"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(cs!=null) {
				try {
					cs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
