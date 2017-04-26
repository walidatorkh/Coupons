package core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import a.db.ConnectionPool;
import core.beans.Company;
import core.beans.Coupon;
import core.dao.CompanyDAO;
import core.dao.CouponDAO;
import core.exceptions.CouponSystemException;

/**
 * implementation of {@link CompanyDAO} which uses {@link ConnectionPool} to work with Apache derby DB
 * @author Itsik
 *
 */
public class CompanyDAODB implements CompanyDAO {
	
	private CouponDAO couponDAO = new CouponDAODB();
	//private CustomerDAO customerDAO = new CustomerDAODB();
	
	

	@Override
	public void create(Company company) throws CouponSystemException {

		Connection con = null;
		try {
			// get con from pool
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("INSERT INTO COMPANY (comp_name , password,email) values (?,?,?)");

			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("Failed to save the company, please check input", e);
		} finally {
			if (con != null) {
				// return con to pool
				try {
					ConnectionPool.getInstance().returnConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new CouponSystemException("returning connection pool failed in create company", e);
				}
			}
		}

	}

	@Override
	public Company read(Company company) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		try {
			// get con from pool
			
			String query = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			if (company.getEmail()==null) {
				query = "select id,comp_name,password,email from COMPANY where ID = ?";
				con = ConnectionPool.getInstance().getConnection();
				pstmt = con.prepareStatement(query);
				pstmt.setLong(1, company.getId());
				rs = pstmt.executeQuery();
			} else {
				query = "select id,comp_name,password,email from COMPANY where email = ?";
				con = ConnectionPool.getInstance().getConnection();
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, company.getEmail());
				rs = pstmt.executeQuery();
			}
			
			

			while (rs.next()) {
				company.setId(rs.getLong("ID"));
				company.setName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));

			}

		} catch (SQLException e) {
			throw new CouponSystemException("read failed", e);
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

		return company;
	}

	@Override
	public void update(Company company) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		try {
			// Updates

			String query = "update COMPANY set comp_name= ?, password = ? ,email = ? WHERE id = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			pstmt.setLong(4, company.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("update company failed", e);
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
	public void delete(Company company) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		try {
			// get con from pool

			String query = "DELETE FROM COMPANY WHERE email like ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, company.getEmail());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("delete failed", e);
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
	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		ArrayList<Company> companies = new ArrayList<>();
		try {
			// get con from pool

			String query = "select id,comp_name,password,email from COMPANY";
			con = ConnectionPool.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Company company = new Company();

				company.setId(rs.getLong("ID"));
				company.setName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));
				if (company.getEmail()!=null) {
					companies.add(company);
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

		return companies;
	}

	@Override
	public ArrayList<Coupon> getCouponsByCompany(Company company) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		try {
			// get coupons by company

			String query = "select COUPON_ID from Company_Coupon where COMPANY_ID = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, company.getId());
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
	public void linkCompanyCoupon(Company company, Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		

		Connection con = null;
		try {
			// get con from pool
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO Company_Coupon (COMPANY_ID , COUPON_ID) values (?,?)");
			
			company = read(company);
			coupon = couponDAO.read(coupon);
			
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("linking Company to Coupon failed, check if company has this coupon already", e);
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
	public void unLinkCompanyCoupon(Company company, Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		

		Connection con = null;
		try {
			// get con from pool

			String query = "DELETE FROM Company_Coupon WHERE COMPANY_ID = ? and COUPON_ID = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("unLinkCompanyCoupon failed", e);
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
	public Boolean nameExist(Company company) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		int numOfNames = 0;
		
		try {
			// get con from pool
			
			String query = "select count (comp_name) AS numOfNames from COMPANY where comp_name = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, company.getName());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				numOfNames = rs.getInt("numOfNames");

			}

		} catch (SQLException e) {
			throw new CouponSystemException("nameExist comp failed", e);
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

}
