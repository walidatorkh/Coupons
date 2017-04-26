package core.dao;

import java.util.ArrayList;

import core.beans.Coupon;
import core.beans.CouponType;
import core.exceptions.CouponSystemException;

/**
 * coupon DAO interface, gives guidline for implementing coupon DAO
 * @author Itsik
 *
 */
public interface CouponDAO {
	
	/**
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void create(Coupon coupon) throws CouponSystemException;

	/**
	 * read coupon
	 * @param coupon
	 * @return
	 * @throws CouponSystemException
	 */
	Coupon read(Coupon coupon) throws CouponSystemException;

	/**
	 * upddate coupon
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void update(Coupon coupon) throws CouponSystemException;

	/**
	 * delete coupon
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void delete(Coupon coupon) throws CouponSystemException;
	
	/**
	 * get al coupons
	 * @return
	 * @throws CouponSystemException
	 */
	ArrayList<Coupon> getAllCoupons() throws CouponSystemException;
	
	/**
	 * get coupons by type
	 * @param couponType
	 * @return
	 * @throws CouponSystemException
	 */
	ArrayList<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException;
	
	/**
	 * checks if a coupon title exists
	 * @param coupon
	 * @return
	 * @throws CouponSystemException
	 */
	Boolean titleExist(Coupon coupon) throws CouponSystemException;
	
	
	
	

}
