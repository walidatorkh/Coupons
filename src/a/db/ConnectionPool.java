package a.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * simple connection pool - singleton
 * 
 * Derby user and PW: 
 *  USER = dbuser
 *  PASS = 123456
 *  
 * @author Itsik
 *
 */
public class ConnectionPool {

	private Set<Connection> connections  = new HashSet<>();
	public static final int MAX_CON = 10;
	private static ConnectionPool instance ;
	private String url = "jdbc:derby://localhost:1527/testCouponDB";
	//private String url = "jdbc:derby://localhost:1527/projectDb";
	//String USER = "dbuser";
	//String PASS = "123456";
	
	
	/**
	 * CTOR
	 * @throws SQLException
	 */
	private ConnectionPool() throws SQLException {
		
		for (int i = 0; i < MAX_CON; i++) {
			
			connections.add(DriverManager.getConnection(url));
			//connections.add(DriverManager.getConnection(url,USER,PASS));
			
		}

	}

	/**
	 * get instance
	 * @return connection pool instance
	 * @throws SQLException
	 */
	public static ConnectionPool getInstance() throws SQLException {
		if (instance==null) {
			instance  = new ConnectionPool();
		}
		
		return instance;
	}
	
	/**
	 * get connection
	 * @return
	 */
	public synchronized Connection getConnection() {
		while (connections.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Iterator<Connection> it = connections.iterator();
		Connection con = it.next();
		it.remove();
		return con;
		
	}
	
	/**
	 * return connection
	 * @param con
	 */
	public synchronized void returnConnection (Connection con){
		connections.add(con);
		this.notify();
	}
	
	/**
	 * close all connections
	 * used in Shutdown
	 * @throws SQLException
	 */
	public void closeAllConnections() throws SQLException{
		for (Connection connection : connections) {
			if (connection!=null) {
				connection.close();
			}
		}
	}

	
	
	
	

}
