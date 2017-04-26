package core.run;

import java.util.ArrayList;

import core.beans.Company;
import core.beans.Coupon;
import core.dao.CompanyDAO;
import core.dao.CouponDAO;
import core.dao.CustomerDAO;
import core.dao.db.CompanyDAODB;
import core.dao.db.CouponDAODB;
import core.dao.db.CustomerDAODB;
import core.exceptions.CouponSystemException;

/**
 * class to create a Daily Coupon Expiration Task, implements Runnable
 * @author Itsik
 *
 */
public class DailyCouponExpirationTask implements Runnable {

	private CompanyDAO compDAO = new CompanyDAODB();
	private CustomerDAO customerDAO = new CustomerDAODB();
	private CouponDAO couponDAO = new CouponDAODB();
	private static Boolean quit;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		quit = false;
		while (!quit) {
			try {
				ArrayList<Company> allCompanies = compDAO.getAllCompanies();
				for (Company company : allCompanies) {
					ArrayList<Coupon> allCompanysCoupons = compDAO.getCouponsByCompany(company);
					for (Coupon coupon : allCompanysCoupons) {
						if (coupon.isExpired()) {
							customerDAO.unLinkCouponFromAllCustomers(coupon);
							compDAO.unLinkCompanyCoupon(company, coupon);
							couponDAO.delete(coupon);

						}
					}
				}

			} catch (CouponSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println();
			System.out.println("finnished deleting thread is sleeping for 24 hrs");
			System.out.println();
			
			try {
				Thread.sleep(60 * 60 * 24 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void stopTask() {
		quit = true;
	}

}
