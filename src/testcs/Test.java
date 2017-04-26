package testcs;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import core.beans.Company;
import core.beans.Coupon;
import core.beans.CouponType;
import core.beans.Customer;
import core.cs.ClientType;
import core.cs.CouponSystem;
import core.exceptions.CouponSystemException;
import core.facade.AdminFacade;
import core.facade.CompanyFacade;
import core.facade.CustomerFacade;

public class Test {

	public static void testCs()  {

		try {
			AdminFacade adminfacade = (AdminFacade) CouponSystem.getInstance().login("Admin", "Admin", ClientType.ADMIN);

			System.out.println("Admin Facade test");
			System.out.println();
			Company company = new Company("TestCompany1", "123456", "itsik190@gmail.com");
			adminfacade.createCompany(company);
			
			System.out.println("created company " + adminfacade.getCompany(company));
			
			company.setPassword("1111");
			adminfacade.updateCompany(company);
			System.out.println("updated company " + adminfacade.getCompany(company));
			System.out.println("get all companies " + adminfacade.getAllCompanies());
			
			Customer customer = new Customer("TestCustomer1", "123456", "itsik190@gmail.com");
			adminfacade.createCustomer(customer);
			System.out.println("get customer " + adminfacade.getCustomer(customer));
			
			customer.setPassword("1111");
			adminfacade.updateCustomer(customer);
			System.out.println("updated customer " + adminfacade.getCustomer(customer));
			System.out.println("get all customers " + adminfacade.getAllCustomers());
			
			
			
			System.out.println("Test Company Facade:");
			System.out.println();
			CompanyFacade companyfacade = (CompanyFacade) CouponSystem.getInstance().login("itsik190@gmail.com", "1111", ClientType.COMPANY);
			
			Calendar cal = Calendar.getInstance();
			cal.set(2017, Calendar.APRIL, 20);
			Date future = new Date(cal.getTime().getTime());
			cal.set(2017, Calendar.FEBRUARY, 23);
//			long ts = System.currentTimeMillis();
			Date past = new Date(cal.getTime().getTime());
			
			Coupon coupon = new Coupon("TestCoupon1", past, future, 5, CouponType.FOOD, "qwert", 35432.552454, "Image.com");
			companyfacade.createCoupon(coupon);
			System.out.println("company get coupon " + companyfacade.getCoupon(coupon));
			
			coupon.setAmount(10);
			companyfacade.updateCoupon(coupon);
			System.out.println("company update coupon " + companyfacade.getCoupon(coupon));
			
			System.out.println("company get all my coupons " + companyfacade.getAllMyCompanysCoupons());
			
			System.out.println("company get coupons by date" + companyfacade.getCouponsByDate(past));
			
			System.out.println("company get coupons by price" + companyfacade.getCouponsByPrice(60000.0));
			System.out.println("company get coupons by type" + companyfacade.getCouponsByType(CouponType.FOOD));
			
			
			Coupon coupon2 = new Coupon("TestCoupon2", past, past, 0, CouponType.HEALTH, "asdfg", 2.2, "Image2.com");
			companyfacade.createCoupon(coupon2);
			System.out.println("company get coupon 2 " + companyfacade.getCoupon(coupon2));
			
			
			coupon2.setImage("new new image");;
			companyfacade.updateCoupon(coupon2);
			System.out.println("company update coupon2 " + companyfacade.getCoupon(coupon2));
			
			System.out.println("company get all my coupons  should be 2: " + companyfacade.getAllMyCompanysCoupons());
			
			System.out.println("company get coupons by date" + companyfacade.getCouponsByDate(past));
			
			System.out.println("company get coupons by price" + companyfacade.getCouponsByPrice(3.0));
			System.out.println("company get coupons by type" + companyfacade.getCouponsByType(CouponType.HEALTH));
			
			
			
			CustomerFacade customerfacade = (CustomerFacade) CouponSystem.getInstance().login("itsik190@gmail.com","1111", ClientType.CUSTOMER);
			customerfacade.purchaseCoupon(coupon);
			
			
			System.out.println("get customer purchased coupon " + customerfacade.getAllPurchasedCoupons());
			System.out.println("get customer purchased coupon " + customerfacade.getAllPurchasedCouponsByPrice(600000.0));
			System.out.println("get customer purchased coupon " + customerfacade.getAllPurchasedCouponsByType(CouponType.FOOD));
			
//
//			
//

//
//			Coupon coupon = new Coupon();
//			coupon.setTitle("Company 1 coupon");
//			coupon.setAmount(1);
//			coupon.setEndDate(tomorow);
//			coupon.setImage("qwerqwerftsa");
//			coupon.setMessage("asdgfazdvfcb");
//			coupon.setPrice(14.99);
//			coupon.setStartDate(yesterday);
//			coupon.setType(CouponType.ELECTRICITY);
//
//			Coupon coup4 = new Coupon("coup4", yesterday, tomorow, 5, CouponType.FOOD, "aasd", 35432.552454, "Image.com");
//
//			// CouponDAODB comda = new CouponDAODB();
//
//			// companyfacade.createCoupon(coup4);
//			adminfacade.removeCompany(company);
//
//			// companyfacade.updateCoupon(coupon);
//			// Customer customer = new Customer("Itsik", "123456",
//			// "itsik190@gmail.com");
//			// adminfacade.createCustomer(customer);
//			// customerfacade.purchaseCoupon(coupon);
//			//
//			// System.out.println(customerfacade.getAllPurchasedCoupons());
//			//
//			//
//			//
//			companyfacade.getAllMyCompanysCoupons();
//
//			System.out.println("companies:");
//			System.out.println(adminfacade.getAllCompanies());
//
//			System.out.println("customers:");
//			System.out.println(adminfacade.getAllCustomers());
//
//			System.out.println("Companies Coupons:");
//			System.out.println(companyfacade.getAllMyCompanysCoupons());
//
//			System.out.println("Company Coupons:");
//			System.out.println(companyfacade.getAllMyCompanysCoupons());
//
//			System.out.println("Customers Coupons:");
//			System.out.println(customerfacade.getAllPurchasedCoupons());

			
			
			
			
			Thread.sleep(2000);
			System.out.println("company get all my coupons  should be 1: " + companyfacade.getAllMyCompanysCoupons());
			System.out.println("all coupons test delete task " + adminfacade.getAllCoupon());
			
			
			companyfacade.removeCoupon(coupon2);
			System.out.println("removed coupon2");
			
			
			
			companyfacade.removeCoupon(coupon);
			System.out.println("removed coupon");
			
			System.out.println("get customer purchased coupon should not be any after delete " + customerfacade.getAllPurchasedCoupons());
			System.out.println("all coupons should not be any after delete " + adminfacade.getAllCoupon());
			System.out.println("get customer purchased coupon should be empty  " + customerfacade.getAllPurchasedCoupons());
			
			adminfacade.removeCustomer(customer);
			System.out.println("removed customer");
			
			System.out.println("get all customers " + adminfacade.getAllCustomers());
			
			adminfacade.removeCompany(company);
			System.out.println("removed company");
			
			System.out.println("get all companies " + adminfacade.getAllCompanies());
			
			CouponSystem.getInstance().shutDown();
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void delete(){
		try {
			AdminFacade adminfacade = (AdminFacade) CouponSystem.getInstance().login("Admin", "Admin", ClientType.ADMIN);
			ArrayList<Company> companies= (ArrayList<Company>) adminfacade.getAllCompanies();
			for (Company company : companies) {
				adminfacade.removeCompany(company);
			}
			ArrayList<Customer> customers = (ArrayList<Customer>) adminfacade.getAllCustomers();
			for (Customer customer : customers) {
				adminfacade.removeCustomer(customer);
			}
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
