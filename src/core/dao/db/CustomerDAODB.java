package core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import a.db.ConnectionPool;
import core.beans.Coupon;
import core.beans.Customer;
import core.dao.CouponDAO;
import core.dao.CustomerDAO;
import core.exceptions.CouponSystemException;



/**
 * implementation of {@link CustomerDAO} which uses {@link ConnectionPool} to work with derby DB
 * @author Itsik
 *
 */
public class CustomerDAODB implements CustomerDAO{
	
	private CouponDAO couponDAO = new CouponDAODB();
	

	@Override
	public void create(Customer customer) throws CouponSystemException {
		// TODO Auto-generated method stub
		
		Connection con = null;
		try {
			// get con from pool
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("INSERT INTO customer (cust_name , password,email) values (?,?,?)");

			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setString(3, customer.getEmail());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("create customer failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
		
	}

	@Override
	public Customer read(Customer customer) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			// get con from pool
			
			String query = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			if (customer.getEmail()==null) {
				query = "select id,cust_name,password,email from customer where ID = ?";
				con = ConnectionPool.getInstance().getConnection();
				pstmt = con.prepareStatement(query);
				pstmt.setLong(1, customer.getId());
				rs = pstmt.executeQuery();
			} else {
				query = "select id,cust_name,password,email from customer where email = ?";
				con = ConnectionPool.getInstance().getConnection();
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, customer.getEmail());
				rs = pstmt.executeQuery();
			}
			
			

			while (rs.next()) {
				customer.setId(rs.getLong("ID"));
				customer.setName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));
				customer.setEmail(rs.getString("EMAIL"));

			}

		} catch (SQLException e) {
			throw new CouponSystemException("read customer failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}

		return customer;
	}

	@Override
	public void update(Customer customer) throws CouponSystemException {
		// TODO Auto-generated method stub
		

		Connection con = null;
		try {
			// Updates

			String query = "update customer set cust_name= ?, password = ?  WHERE email = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setString(3, customer.getEmail());
			

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("update customer failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
		
	}

	@Override
	public void delete(Customer customer) throws CouponSystemException {
		// TODO Auto-generated method stub
		
		Connection con = null;
		try {
			// get con from pool

			String query = "DELETE FROM customer WHERE email = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, customer.getEmail());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("delete customer failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
		
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			// get con from pool

			String query = "select id,cust_name,password,email from customer";
			con = ConnectionPool.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Customer customer = new Customer();

				customer.setId(rs.getLong("ID"));
				customer.setName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));
				customer.setEmail(rs.getString("EMAIL"));
				if (customer.getEmail()!=null) {
					customers.add(customer);
				}
				

			}

		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanies failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}

		return customers;
	}

	@Override
	public ArrayList<Coupon> getCouponsByCustomer(Customer customer) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		try {
			// get coupons by customer
			
			customer = read(customer);

			String query = "select COUPON_ID from Customer_Coupon where Cust_ID = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, customer.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("COUPON_ID"));
				if (coupon.getId()!=0) {
					coupons.add(couponDAO.read(coupon));
				}
				

			}

		} catch (SQLException e) {
			throw new CouponSystemException("getCouponsByCompany failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}

		return coupons;
	}

	@Override
	public void linkCustomerCoupon(Customer customer, Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		

		Connection con = null;
		try {
			customer = read(customer);
			coupon = couponDAO.read(coupon);
			
			// get con from pool
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO Customer_Coupon (CUST_ID , COUPON_ID) values (?,?)");

			pstmt.setLong(1, customer.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("linkCustomerCoupon failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
		
	}

	@Override
	public void unLinkCustomerCoupon(Customer customer, Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			// get con from pool

			String query = "DELETE FROM Customer_Coupon WHERE CUSTOMER_ID = ? and COUPON_ID = ? ";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, customer.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("unLinkCustomerCoupon failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
	}

	@Override
	public Boolean nameExist(Customer customer) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		int numOfNames = 0;
		
		try {
			// get con from pool
			
			String query = "select count (cust_name) AS numOfNames from CUSTOMER where cust_name = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, customer.getName());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				numOfNames = rs.getInt("NUMOFNAMES");

			}

		} catch (SQLException e) {
			throw new CouponSystemException("nameExist cust failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
		
		if (numOfNames>0) {
			return true;
		}

		return false;
	}

	@Override
	public void unLinkCouponFromAllCustomers(Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			// get con from pool

			String query = "DELETE FROM Customer_Coupon WHERE  COUPON_ID = ? ";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("unLinkCouponFromAllCustomers failed", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed", e);
				}
			}
		}
	}

}
