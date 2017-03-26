package com.zero.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zero.core.tdd.annotation.TDD;

@TDD
public class H2DataBaseTest {

	private Server server;

	@Before
	public void startServer() throws Exception {
		System.out.println("Starting H2 server...");
		server = Server.createTcpServer().start();
		System.out.println("Start H2 server success");

	}

	@After
	public void stopServer() {
		System.out.println("stoping H2 server...");
		server.stop();
		System.out.println("stop H2 server success");
	}

	@Test
	public void testDbConnection() throws Exception {
		System.out.println("Testing...");
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/mem:zero", "sa", "");
		Statement stmt = conn.createStatement();

		stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");
		stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(1, 'Hello_Mem');");
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_MEM");
		while (rs.next()) {
			System.out.println(rs.getInt("ID") + "," + rs.getString("NAME"));
		}
		conn.close();
	}
}