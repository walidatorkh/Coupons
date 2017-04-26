package core.beans;

/**
 * customer bean
 * @author Itsik
 *
 */
public class Customer {

	private long id;
	private String name;
	private String password;
	private String email;

	/**
	 * get id - 
	 * {@link Long}
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * CTOR - use only to update
	 */
	public Customer() {

	}

	/**
	 * CTOR, use to crerate new customers
	 * @param name - {@link String}
	 * @param password - {@link String}
	 * @param email - {@link String}
	 */
	public Customer(String name, String password, String email) {

		this.name = name;
		this.password = password;
		this.email = email;
	}

	/**
	 * setId
	 * @param id - {@link Long}
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * getName
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get PW
	 * @return - {@link String}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password
	 * @param password - {@link String}
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * get email - Used as user name!!!!!!!
	 * @return - {@link String}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email  - Used as user name!!!!!!!
	 * @param email - {@link String}
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + "]";
	}

}
