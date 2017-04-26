package core.beans;

import java.sql.Date;

/**
 * coupon bean
 * @author Itsik
 *
 */
public class Coupon {
	
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	/**
	 * get coupon id 
	 * @return {@link Long}
	 */
	public long getId() {
		return id;
	}
	
	
	/**
	 * CTOR use only for updating
	 */
	public Coupon() {
	}



	/**
	 * CTOR use this one
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price,
			String image) {
		
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}


	/**
	 * set coupon id - for internal use only
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * get coupon title
	 * @return {@link String}
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * set title 
	 * @param title {@link String}
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * get start date
	 * @return Java.SQL.Date
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * set start date java sql date
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * getEndDate java sql date
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * setEndDate java sql date
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * int amount of coupons available
	 * @return
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * set int amount of coupons available
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * get coupon type
	 * @return {@link CouponType}
	 */
	public CouponType getType() {
		return type;
	}
	
	/**
	 * set {@link CouponType}
	 * @param coupon type
	 */
	public void setType(CouponType type) {
		this.type = type;
	}
	
	/**
	 * get message - {@link String}
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * set message - {@link String}
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * get coupon price - {@link Double}
	 * @return
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 *  price - {@link Double}
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Image - {@link String}
	 * @return
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Image - {@link String}
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * to string
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	
	/**
	 * checks if a coupon is active, thus available for purchase.
	 * @return
	 */
	public Boolean isActive(){
		long ts = System.currentTimeMillis();
		Date cd = new Date(ts);
		if (cd.after(startDate)&&cd.before(endDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks if coupon is expired for cleanup purpases
	 * @return
	 */
	public Boolean isExpired(){
		long ts = System.currentTimeMillis();
		Date cd = new Date(ts);
		if (cd.after(endDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks if there are coupons available to purchase.
	 * checks if amount if larger than zero.
	 * @return
	 */
	public boolean leftCoupon() {
		// TODO Auto-generated method stub
		if (amount>0) {
			return true;
		}
		return false;
	}
	

}
