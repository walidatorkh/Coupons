package core.facade;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import core.beans.Company;
import core.beans.Coupon;
import core.beans.CouponType;
import core.dao.CompanyDAO;
import core.dao.CouponDAO;
import core.dao.CustomerDAO;
import core.dao.db.CompanyDAODB;
import core.dao.db.CouponDAODB;
import core.dao.db.CustomerDAODB;
import core.exceptions.CouponSystemException;

/**
 * company Facade
 * @author Itsik
 *
 */
public class CompanyFacade implements ClientFacade {
	private static CompanyDAO compDAO = new CompanyDAODB();
	private static CustomerDAO customerDAO = new CustomerDAODB();
	private static CouponDAO couponDAO = new CouponDAODB();
	private Company company;
	
	public CompanyFacade() {
	

	}
	
	
	private CompanyFacade(Company company) throws CouponSystemException {
		
			this.company = compDAO.read(company);
		

	}
	
	
	/**
	 * creates coupon and links it to Company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		
		if (!couponDAO.titleExist(coupon)) {
			couponDAO.create(coupon);
			coupon = couponDAO.read(coupon);
			compDAO.linkCompanyCoupon(company, coupon);
		}else {
			throw new CouponSystemException("Title exists");
		}
			
			

			
		
	}

	/**
	 * removes {@link Coupon} unlinks from company and customers
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		
			coupon = couponDAO.read(coupon);
			company = compDAO.read(company);
			compDAO.unLinkCompanyCoupon(company, coupon);
			customerDAO.unLinkCouponFromAllCustomers(coupon);
			couponDAO.delete(coupon);
		
	}

	/**
	 * updates {@link Coupon} by title
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		
			couponDAO.update(coupon);
		
	}
	
	/**
	 * gets coupon by title or ID
	 * @param coupon
	 * @return
	 * @throws CouponSystemException
	 */
	public Coupon getCoupon(Coupon coupon) throws CouponSystemException {
		
			coupon = couponDAO.read(coupon);
	
		return coupon;
	}
	
	/**
	 * gets all this {@link Company}s coupons
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllMyCompanysCoupons() throws CouponSystemException {
		ArrayList<Coupon> coupons = new ArrayList<>();

		
			coupons = compDAO.getCouponsByCompany(company);
	
		return coupons;
	}
	
	

	/**
	 * get coupon by {@link CouponType}
	 * @param couponType
	 * @return {@link Collection}
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException {
		ArrayList<Coupon> couponsByType = new ArrayList<>();

		
			couponsByType = couponDAO.getCouponsByType(couponType);
		

		return couponsByType;
	}

	/**
	 * gets Coupons By Price
	 * @param price {@link Double}
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByPrice(Double price) throws CouponSystemException {
		ArrayList<Coupon> allCoupons = new ArrayList<>();
		ArrayList<Coupon> allCouponsByPrice = new ArrayList<>();

		
			allCoupons = couponDAO.getAllCoupons();
		

		for (Coupon coupon : allCoupons) {
			if (coupon.getPrice() < price) {
				allCouponsByPrice.add(coupon);
			}
		}
		return allCouponsByPrice;

	}

	/**
	 * get coupon by date
	 * @param date
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByDate(Date date) throws CouponSystemException {
		ArrayList<Coupon> allMyCoupons = new ArrayList<>();
		ArrayList<Coupon> allCouponsByDate = new ArrayList<>();

		
		allMyCoupons = compDAO.getCouponsByCompany(company);
		

		for (Coupon coupon : allMyCoupons) {
			if (coupon.getEndDate().after(date)) {
				allCouponsByDate.add(coupon);
			}
		}
		return allCouponsByDate;

	}

	/**
	 * login 
	 * @param email
	 * @param password
	 * @return {@link CompanyFacade}
	 * @throws CouponSystemException
	 */
	public CompanyFacade login(String email, String password) throws CouponSystemException {
		
		// TODO Auto-generated method stub
		Company company = new Company();
		company.setEmail(email);
		CompanyFacade companyFacade = new CompanyFacade(company);
		
			company = compDAO.read(company);
		
		if (company.getId() != 0 && password.equals(company.getPassword())) {
			return companyFacade;
		}else {
			throw new CouponSystemException("incorrect company Login");
		}
		

	}

}
