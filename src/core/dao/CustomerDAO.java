package core.dao;

import java.util.ArrayList;

import core.beans.Coupon;
import core.beans.Customer;
import core.exceptions.CouponSystemException;

/**
 * customer DAO interface
 * @author Itsik
 *
 */
public interface CustomerDAO {

	/**
	 * Crate customer
	 * @param customer
	 * @throws CouponSystemException
	 */
	void create(Customer customer) throws CouponSystemException;

	/**
	 * read customer
	 * @param customer
	 * @return
	 * @throws CouponSystemException
	 */
	Customer read(Customer customer) throws CouponSystemException;

	/**
	 * update customer
	 * @param customer
	 * @throws CouponSystemException
	 */
	void update(Customer customer) throws CouponSystemException;

	/**
	 * delete a customer
	 * @param customer
	 * @throws CouponSystemException
	 */
	void delete(Customer customer) throws CouponSystemException;

	/**
	 * Gets a collection of all customers
	 * @return
	 * @throws CouponSystemException
	 */
	ArrayList<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * get coupons by customer
	 * @param customer
	 * @return
	 * @throws CouponSystemException
	 */
	ArrayList<Coupon> getCouponsByCustomer(Customer customer) throws CouponSystemException;

	/**
	 * links a customer to a coupon
	 * @param customer
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void linkCustomerCoupon(Customer customer, Coupon coupon) throws CouponSystemException;

	/**
	 * unlink a customer from a coupon
	 * @param customer
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void unLinkCustomerCoupon(Customer customer, Coupon coupon) throws CouponSystemException;
	
	/**
	 * unlinks a coupon from any customer that has it.
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void unLinkCouponFromAllCustomers(Coupon coupon) throws CouponSystemException;
	
	/**
	 * checks if a customer name exists
	 * @param customer
	 * @return
	 * @throws CouponSystemException
	 */
	Boolean nameExist(Customer customer) throws CouponSystemException;
	
	

}
