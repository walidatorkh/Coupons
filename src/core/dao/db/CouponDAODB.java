package core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import a.db.ConnectionPool;
import core.beans.Coupon;
import core.beans.CouponType;
import core.dao.CouponDAO;
import core.exceptions.CouponSystemException;

/**
 * implementation of {@link CouponDAO} which uses {@link ConnectionPool} to work with derby DB
 * @author Itsik
 *
 */
public class CouponDAODB implements CouponDAO {

	@Override
	public void create(Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		try {
			// get con from pool
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO Coupon (TITLE,START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) values (?,?,?,?,?,?,?,?)");

			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, coupon.getStartDate());
			pstmt.setDate(3, coupon.getEndDate());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().toString());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("create Coupon failed", e);
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
	public Coupon read(Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			// get con from pool
			
			String query = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			if (coupon.getTitle()==null) {
				query = "select ID,TITLE,START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE from COUPON where ID=?";
				con = ConnectionPool.getInstance().getConnection();
				pstmt = con.prepareStatement(query);
				pstmt.setLong(1, coupon.getId());
				rs = pstmt.executeQuery();
			} else {
				query = "select ID,TITLE,START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE from COUPON where TITLE=?";
				con = ConnectionPool.getInstance().getConnection();
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, coupon.getTitle());
				rs = pstmt.executeQuery();
			}
			
			

			while (rs.next()) {
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));

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

		return coupon;
	}

	@Override
	public void update(Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		try {
			// Updates
			
			
			String query = " update coupon set START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ? where TITLE = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);

			
			pstmt.setDate(1, coupon.getStartDate());
			pstmt.setDate(2, coupon.getEndDate());
			pstmt.setInt(3, coupon.getAmount());
			pstmt.setString(4, coupon.getType().toString());
			pstmt.setString(5, coupon.getMessage());
			pstmt.setDouble(6, coupon.getPrice());
			pstmt.setString(7, coupon.getImage());
			pstmt.setString(8, coupon.getTitle());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("update coupon failed", e);
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
	public void delete(Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		try {
			// get con from pool

			String query = "DELETE FROM coupon WHERE id = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, coupon.getId());
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
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		try {
			// get con from pool

			String query = "select ID, TITLE,START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE from COUPON";
			con = ConnectionPool.getInstance().getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				if (coupon.getTitle()!=null) {
					coupons.add(coupon);
				}
				

			}

		} catch (SQLException e) {
			throw new CouponSystemException("getAllCoupons failed", e);
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
	public ArrayList<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException {
		// TODO Auto-generated method stub

		Connection con = null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		try {
			// get coupons by company

			String query = "select ID from COUPON where Type=?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, couponType.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("ID"));
				if (coupon.getId()!=0) {
					coupons.add(this.read(coupon));
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
	public Boolean titleExist(Coupon coupon) throws CouponSystemException {
		// TODO Auto-generated method stub
		Connection con = null;
		int numOfTitles = 0;
		
		try {
			// get con from pool
			
			String query = "select count (TITLE) AS NUMOFTITLES from COUPON where TITLE = ?";
			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, coupon.getTitle());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				numOfTitles = rs.getInt("NUMOFTITLES");

			}

		} catch (SQLException e) {
			throw new CouponSystemException("titleExist coupon failed", e);
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
		
		if (numOfTitles>0) {
			return true;
		}

		return false;
	}

	
}
