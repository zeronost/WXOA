package com.zero.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zero.core.tdd.annotation.TDD;

@TDD
public class H2DataBaseTest {

	private static Server server;
	
	private static Logger logger = Logger.getLogger(H2DataBaseTest.class);

	@BeforeClass
	public static void startServer() throws Exception {
		logger.info("Starting H2 server...");
		server = Server.createTcpServer().start();
		server.runTool("-web","-webAllowOthers","-webPort","8090");
		logger.info("Start H2 server success");
	}

	@AfterClass
	public static void stopServer() {
		logger.info("stoping H2 server...");
		server.stop();
		logger.info("stop H2 server success");
	}

	@Test
	public void testDbConnection() throws Exception {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/mem:zero", "sa", "");
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));");
		stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(1, 'Hello_Mem');");
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_MEM");
		while (rs.next()) {
			logger.info(rs.getInt("ID") + "," + rs.getString("NAME"));
		}
		conn.close();
	}
}