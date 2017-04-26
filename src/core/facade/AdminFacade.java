package core.facade;

import java.util.ArrayList;
import java.util.Collection;

import core.beans.Company;
import core.beans.Coupon;
import core.beans.Customer;
import core.dao.CompanyDAO;
import core.dao.CouponDAO;
import core.dao.CustomerDAO;
import core.dao.db.CompanyDAODB;
import core.dao.db.CouponDAODB;
import core.dao.db.CustomerDAODB;
import core.exceptions.CouponSystemException;

public class AdminFacade implements ClientFacade {

	private CompanyDAO compDAO = new CompanyDAODB();
	private CustomerDAO customerDAO = new CustomerDAODB();
	private CouponDAO couponDAO = new CouponDAODB();
	private final String ADMIN = "Admin";

	/**
	 * creates a company and saves it to the DB. Please note that you cannot
	 * create two companies with the same Email or name.
	 * @param company
	 * @throws CouponSystemException
	 */
	public void createCompany(Company company) throws CouponSystemException {
		if (!compDAO.nameExist(company)) {
			compDAO.create(company);
		}else {
			throw new CouponSystemException("Name exists");
		}
	}
	
	
	/**
	 * Removes Company, by mail or ID
	 * @param company
	 * @throws CouponSystemException
	 */
	public void removeCompany(Company company) throws CouponSystemException {
		company = compDAO.read(company);
		ArrayList<Coupon> allThisCompanyCoupons;

		allThisCompanyCoupons = compDAO.getCouponsByCompany(company);

		for (Coupon coupon : allThisCompanyCoupons) {
			ArrayList<Customer> allCustomers = customerDAO.getAllCustomers();
			for (Customer customer : allCustomers) {
				customerDAO.unLinkCustomerCoupon(customer, coupon);
			}
			compDAO.unLinkCompanyCoupon(company, coupon);
			couponDAO.delete(coupon);
		}
		compDAO.delete(company);

	}
	
	/**
	 * updates a company's Password or Email or name
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {

		compDAO.update(company);

	}
	
	/**
	 * returns company by ID or email if the customer does not exist returns null.
	 * @param company
	 * @return company
	 * @throws CouponSystemException
	 */
	public Company getCompany(Company company) throws CouponSystemException {

		company = compDAO.read(company);

		if (company.getId() != 0) {
			return company;
		} else {
			return null;
		}
	}
	
	/**
	 * gets all companies returns collection of all companies, can cast to ArrayList
	 * @return collection
	 * @throws CouponSystemException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		ArrayList<Company> allCompanies = new ArrayList<>();

		allCompanies = compDAO.getAllCompanies();

		return allCompanies;
	}
	
	/**
	 * creates a customer, Please note that you cannot
	 * create two companies with the same name Email or name.
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void createCustomer(Customer customer) throws CouponSystemException {
		if (!customerDAO.nameExist(customer)) {
			customerDAO.create(customer);
		}else {
			throw new CouponSystemException("customer name already exists");
		}
		

	}
	
	
	/**
	 * Removes a customer and all 
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException {
		customer = customerDAO.read(customer);
		ArrayList<Coupon> allCoupons = customerDAO.getCouponsByCustomer(customer);

		for (Coupon coupon : allCoupons) {
			customerDAO.unLinkCustomerCoupon(customer, coupon);
			couponDAO.delete(coupon);
		}

		customerDAO.delete(customer);

	}
	
	/**
	 * updates name and password by email
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {

		customerDAO.update(customer);

	}
	
	/**
	 * get Customer by email or ID.
	 * @param customer
	 * @return
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(Customer customer) throws CouponSystemException {

		customer = customerDAO.read(customer);

		return customer;
	}
	
	/**
	 * get all customers
	 * @return {@link Collection} with all {@link Customer}s, can be cast to {@link ArrayList} 
	 * @throws CouponSystemException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		ArrayList<Customer> allCustomers = new ArrayList<>();

		allCustomers = customerDAO.getAllCustomers();

		return allCustomers;
	}
	
	/**
	 * gets all coupons
	 * @return {@link Collection} with all {@link Coupon}s, can be cast to {@link ArrayList} 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupon() throws CouponSystemException {
		ArrayList<Coupon> allCoupons = new ArrayList<>();

		allCoupons = couponDAO.getAllCoupons();

		return allCoupons;
	}
	
	/**
	 * gets coupons by {@link Company}
	 * @param company
	 * @return {@link Collection} with all {@link Coupon}s by {@link Company}, can be cast to {@link ArrayList} 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponByCompany(Company company) throws CouponSystemException {
		ArrayList<Coupon> couponsByCompany = new ArrayList<>();
		couponsByCompany = compDAO.getCouponsByCompany(company);
		return couponsByCompany;
	}
	
	/**
	 * login (Admin/Admin)
	 * @param email
	 * @param password
	 * @return {@link AdminFacade}
	 * @throws CouponSystemException 
	 */
	public AdminFacade login(String email, String password) throws CouponSystemException {
		if (email.equals(ADMIN) && password.equals(ADMIN)) {
			return new AdminFacade();
		}else {
			throw new CouponSystemException("incorrect admin Login");
		}
	}

}
