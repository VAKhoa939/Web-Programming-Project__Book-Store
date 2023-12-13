package business;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
public class Product implements Serializable
{
	@Id
	private String productCode;
	
	@OneToOne
	@JoinColumn(name = "descId")
	private Description infor;
	private double price;
	private double discount;
	
	public Product()
	{
		productCode = "";
		infor = new Description();
		price = 0;
		discount = 0;
	}

	public Product(String productCode, Description infor, double price) 
	{
		this.productCode = productCode;
		this.infor = infor;
		this.price = price;
		discount = 100;
	}

	public Product(String productCode, Description infor, double price, double discount) 
	{
		this.productCode = productCode;
		this.infor = infor;
		this.price = price;
		this.discount = discount;
	}

	public String getProductCode() 
	{
		return productCode;
	}

	public void setProductCode(String productCode) 
	{
		this.productCode = productCode;
	}

	public Description getInfor() 
	{
		return infor;
	}

	public void setInfor(Description infor) 
	{
		this.infor = infor;
	}

	public double getPrice() 
	{
		return price;
	}

	public void setPrice(double price) 
	{
		this.price = price;
	}

	public double getDiscount() 
	{
		return discount;
	}

	public void setDiscount(double discount) 
	{
		this.discount = discount;
	}
	
	public double calPrice()
	{
		return price * (discount / 100); 
	}
	
	public String getCurrencyFormat()
	{
		return calPrice() + " VND";
	}
}
