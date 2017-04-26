package a.db.create;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildDB {
	
	
	public static void createDb(){
		
		String dbBuilderQuery = "jdbc:derby://localhost:1527/testCouponDB;create=true";
		String createCompanyTableInDb = "Create table Company(ID bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), COMP_NAME varchar(25), PASSWORD varchar (15), EMAIL varchar(25), PRIMARY KEY (EMAIL) )";
		String createCustomerTableInDb = "Create table Customer(ID bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), CUST_NAME varchar(25), PASSWORD varchar (15),EMAIL varchar(25), PRIMARY KEY (EMAIL) )";
		String createCouponTableInDb = "Create table Coupon (ID bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), TITLE varchar(25),START_DATE DATE , END_DATE DATE, AMOUNT INTEGER, TYPE VARCHAR (20), MESSAGE varchar(30),PRICE DOUBLE, IMAGE varchar(30) , PRIMARY KEY (TITLE) )";
	    String createCustomerCouponTableInDb = "Create table Customer_Coupon (CUST_ID BIGINT, COUPON_ID BIGINT , PRIMARY KEY (CUST_ID , COUPON_ID))";	
		String createCompanyCouponTableInDb = "Create table Company_Coupon (COMPANY_ID BIGINT, COUPON_ID BIGINT , PRIMARY KEY (COMPANY_ID , COUPON_ID))";

		try {
			Connection con = DriverManager.getConnection(dbBuilderQuery);
			Statement stmt = con.createStatement();
			stmt.executeUpdate(createCompanyTableInDb);
			stmt.executeUpdate(createCustomerTableInDb);
			stmt.executeUpdate(createCouponTableInDb);
			stmt.executeUpdate(createCustomerCouponTableInDb);
			stmt.executeUpdate(createCompanyCouponTableInDb);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Tables: COMPANY, CUSTOMER, COUPON, CUSTOMERCOUPON, COMPANYCOUPON created");


		
	}
	
	

}
