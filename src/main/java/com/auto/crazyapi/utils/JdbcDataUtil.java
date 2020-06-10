package com.auto.crazyapi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcDataUtil {

	public static void main(String[] args) {
//		Connection conn = JdbcDataUtil.getConn(
//				"jdbc:mysql://localhost:3306/javamall?useUnicode=true&characterEncoding=utf8",
//				"root", "123456");
		Connection conn = JdbcDataUtil.getConn(
				"jdbc:mysql://localhost:3309/crm?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false",
				"root", "123456");
		try {
			Object[][] data = JdbcDataUtil.getData(conn, "SELECT customer_name FROM 72crm_crm_customer where customer_id=34;");
			System.out.println(data);
			for (int i = 0; i < data.length; i++) {
				System.out.println(data[0][0]);
			}
		} finally {
			JdbcDataUtil.closeConn(conn);
		}
	}

	public static Connection getConn(String url, String username, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Object[][] getData(Connection conn, String query) {
		Object[][] data = null;

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();//customer_name=sdfff,customer_name=aaa
			int column = meta.getColumnCount();
			List<Object[]> list = new ArrayList<Object[]>();
			while (rs.next()) {
				Object[] oa = new Object[column];
				for (int i = 1; i <= column; i++) {
					oa[i - 1] = rs.getObject(i);
				}
				list.add(oa);
			}
			if (list.size() > 0) {
				data = new Object[list.size()][column];
				for (int i = 0; i < list.size(); i++) {
					data[i] = list.get(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	// executeUpdate方法可以执行新增、更新、删除三种sql语句
	public static int executeUpdate(Connection conn, String sql) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			int updateCount = stmt.getUpdateCount();
			return updateCount;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
}
