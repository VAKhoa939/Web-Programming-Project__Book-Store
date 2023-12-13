package business;

import java.io.Serializable;
import java.util.*;
import jakarta.persistence.*;

@Entity
public class Invoice implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long invoiceId;
	
	@OneToOne
	@JoinColumn(name = "cartId")
	private Cart cart;
	int invoiceNum;
	private String deliveryAddr;
	private Date invoiceDate;
	
	public Invoice()
	{
		cart = new Cart();
		invoiceNum = 0;
		deliveryAddr = "";
		invoiceDate = new Date();
	}

	public Invoice(Cart cart) 
	{
		this.cart = cart;
		invoiceDate = new Date();
	}

	public Invoice(Cart cart, int invoiceNum, String deliveryAddr, Date invoiceDate) 
	{
		this.cart = cart;
		this.invoiceNum = invoiceNum;
		this.deliveryAddr = deliveryAddr;
		this.invoiceDate = invoiceDate;
	}
	
	public long getInvoiceId() 
	{
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) 
	{
		this.invoiceId = invoiceId;
	}

	public Cart getCart() 
	{
		return cart;
	}

	public void setCart(Cart cart) 
	{
		this.cart = cart;
	}

	public int getInvoiceNum() 
	{
		return invoiceNum;
	}

	public void setInvoiceNum(int invoiceNum) 
	{
		this.invoiceNum = invoiceNum;
	}

	public String getDeliveryAddr() 
	{
		return deliveryAddr;
	}

	public void setDeliveryAddr(String deliveryAddr) 
		{
		this.deliveryAddr = deliveryAddr;
	}

	public Date getInvoiceDate() 
	{
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) 
	{
		this.invoiceDate = invoiceDate;
	}
	
	public Date getEstArrivalDate()
	{
		Date arrivalDate = (Date) invoiceDate.clone();
		Calendar c = Calendar.getInstance();
		c.setTime(arrivalDate);
		c.add(Calendar.DATE, 2);
		arrivalDate = c.getTime();
		return arrivalDate;
	}
}
