package core.cs;

import java.sql.SQLException;

import a.db.ConnectionPool;
import core.exceptions.CouponSystemException;
import core.facade.AdminFacade;
import core.facade.ClientFacade;
import core.facade.CompanyFacade;
import core.facade.CustomerFacade;
import core.run.DailyCouponExpirationTask;

/**
 * coupon system - singleton, primary class for the coupon system project.
 * Allows you to get instance and login.
 * @author Itsik
 *
 */
public class CouponSystem {

	private static CouponSystem instance;
	DailyCouponExpirationTask daily = new DailyCouponExpirationTask();

	/**
	 * private ctor
	 */
	private CouponSystem() {
		Thread dailyTaskThread = new Thread(daily);
		dailyTaskThread.start();
	}

	/**
	 * get instance, for login purpose
	 * @return {@link CouponSystem} instance 
	 */
	public static CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}

		return instance;
	}

	/**
	 * primary login method - enter username (email) and password to login - returns reference to {@link ClientFacade} which you need to cast to either 
	 * {@link AdminFacade} (Admin/Admin) {@link CustomerFacade} or {@link CompanyFacade}.
	 * @param email - {@link String}
	 * @param password - {@link String}
	 * @param clientType - {@link ClientType}
	 * @return - {@link ClientFacade}
	 * @throws CouponSystemException
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {

		AdminFacade adminfacade = new AdminFacade();
		CompanyFacade companyfacade = new CompanyFacade();
		CustomerFacade customerfacade = new CustomerFacade();

		switch (clientType) {
		case ADMIN:
			adminfacade = adminfacade.login(email, password);
			return adminfacade;

		case COMPANY:
			companyfacade = companyfacade.login(email, password);
			return companyfacade;

		case CUSTOMER:
			customerfacade = customerfacade.login(email, password);
			return customerfacade;

		default:
			return null;

		}

	}

	/**
	 * shut down: stops daily task, closes all connection pools
	 * @throws CouponSystemException
	 */
	public void shutDown() throws CouponSystemException {
		daily.stopTask();
		try {
			ConnectionPool.getInstance().closeAllConnections();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
				throw new CouponSystemException("shut down failed", e);
			
		}
	}

}
