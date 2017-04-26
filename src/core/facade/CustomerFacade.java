package core.facade;

import java.util.ArrayList;
import java.util.Collection;

import core.beans.Coupon;
import core.beans.CouponType;
import core.beans.Customer;
import core.dao.CouponDAO;
import core.dao.CustomerDAO;
import core.dao.db.CouponDAODB;
import core.dao.db.CustomerDAODB;
import core.exceptions.CouponSystemException;

/**
 * Customer Facade
 * @author Itsik
 *
 */
public class CustomerFacade implements ClientFacade {
	private static CustomerDAO customerDAO = new CustomerDAODB();
	private static CouponDAO couponDAO = new CouponDAODB();
	private Customer customer;
	
	public CustomerFacade() {
		
	}
	
	private CustomerFacade(Customer customer) throws CouponSystemException {
		
			this.customer = customerDAO.read(customer);
		

	}
	
	/**
	 * Purchase {@link Coupon} cannot purchase twice
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		
			customer = customerDAO.read(customer);
			coupon = couponDAO.read(coupon);
			ArrayList<Coupon> customersCoupons = customerDAO.getCouponsByCustomer(customer);
			Boolean hasCoupon = false;

			if (!customersCoupons.isEmpty()) {
				for (Coupon customerCoupon : customersCoupons) {
					if (customerCoupon.getTitle().equals(coupon.getTitle())) {
						hasCoupon = true;
					}
				}
			}
			
			if (!coupon.isActive()) {
				throw new CouponSystemException("Coupon is not active");
			} 
			if (hasCoupon) {
				throw new CouponSystemException("you already have this coupon");
			}
			
			if (!coupon.leftCoupon()) {
				throw new CouponSystemException("No more coupons left to buy");
			}
			
			customerDAO.linkCustomerCoupon(customer, coupon);
			coupon.setAmount(coupon.getAmount() - 1);
			couponDAO.update(coupon);
			
		
		
	}

	/**
	 * get all purchased coupons
	 * @return {@link Collection}
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		ArrayList<Coupon> allPurchasedCoupons = new ArrayList<>();
		
			allPurchasedCoupons = customerDAO.getCouponsByCustomer(customer);
		

		return allPurchasedCoupons;

	}

	/**
	 * get All Purchased Coupons By Type
	 * @param couponType
	 * @return {@link Collection}
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CouponSystemException {
		ArrayList<Coupon> couponsByType = new ArrayList<>();
		ArrayList<Coupon> coupons = new ArrayList<>();

		
			coupons = customerDAO.getCouponsByCustomer(customer);
		

		for (Coupon coupon : coupons) {
			if (couponType.equals(coupon.getType())) {
				couponsByType.add(coupon);
			}
		}

		return couponsByType;
	}

	/**
	 * getAllPurchasedCouponsByPrice
	 * @param price
	 * @return {@link Collection}
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price) throws CouponSystemException {
		ArrayList<Coupon> couponsByPrice = new ArrayList<>();
		ArrayList<Coupon> coupons = new ArrayList<>();

		
			coupons = customerDAO.getCouponsByCustomer(customer);
		

		for (Coupon coupon : coupons) {
			if (coupon.getPrice() == price) {
				couponsByPrice.add(coupon);
			}
		}

		return couponsByPrice;
	}

	

	/**
	 * login 
	 * @param email
	 * @param password
	 * @return {@link CustomerFacade}
	 * @throws CouponSystemException
	 */
	public CustomerFacade login(String email, String password) throws CouponSystemException {
		Customer customer = new Customer();
		customer.setEmail(email);
		CustomerFacade customerFacade = new CustomerFacade(customer);
		
		
			customer = customerDAO.read(customer);
		
		if (customer.getId() != 0 && password.equals(customer.getPassword())) {
			return customerFacade;
		}else {
			throw new CouponSystemException("incorrect customer Login");
		}
		
	}

}
