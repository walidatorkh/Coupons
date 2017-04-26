package core.dao;

import java.util.ArrayList;

import core.beans.Company;
import core.beans.Coupon;
import core.exceptions.CouponSystemException;

/**
 * company DAO interface, gives guidline for implementing company DAO
 * @author Itsik
 *
 */
public interface CompanyDAO {

	/**
	 * create company
	 * @param company
	 * @throws CouponSystemException
	 */
	void create(Company company) throws CouponSystemException;

	/**
	 * read company
	 * @param company
	 * @return
	 * @throws CouponSystemException
	 */
	Company read(Company company) throws CouponSystemException;

	/**
	 * update company
	 * @param company
	 * @throws CouponSystemException
	 */
	void update(Company company) throws CouponSystemException;

	/**
	 * delete company
	 * @param company
	 * @throws CouponSystemException
	 */
	void delete(Company company) throws CouponSystemException;
	
	/**
	 * get all companies
	 * @return
	 * @throws CouponSystemException
	 */
	ArrayList<Company> getAllCompanies() throws CouponSystemException;
	
	/**
	 * getCouponsByCompany
	 * @param company
	 * @return
	 * @throws CouponSystemException
	 */
	ArrayList<Coupon> getCouponsByCompany(Company company) throws CouponSystemException;
	
	/**
	 * links a Company to a Coupon
	 * @param company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void linkCompanyCoupon(Company company, Coupon coupon) throws CouponSystemException;
	
	/**
	 * unLinks a Company from a Coupon
	 * @param company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void unLinkCompanyCoupon(Company company, Coupon coupon) throws CouponSystemException;
	
	/**
	 * checks if a coupon exists
	 * @param company
	 * @return
	 * @throws CouponSystemException
	 */
	Boolean nameExist(Company company) throws CouponSystemException;
	
	
	
}
