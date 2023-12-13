package business;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
public class LineItem implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long itemId;
	private String itemCode;
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	public LineItem()
	{
		quantity = 0;
		product = new Product();
	}

	public LineItem(int quantity, Product product) 
	{
		this.quantity = quantity;
		this.product = product;
		itemCode = this.product.getProductCode();
	}

	public LineItem(int quantity, Product product, String itemCode) 
	{
		this.quantity = quantity;
		this.product = product;
		this.itemCode = itemCode;
	}
	
	public long getItemId() 
	{
		return itemId;
	}

	public void setItemId(long itemId) 
	{
		this.itemId = itemId;
	}

	public String getItemCode() 
	{
		return itemCode;
	}

	public void setItemCode(String itemCode) 
	{
		this.itemCode = itemCode;
	}

	public int getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}

	public Product getProduct() 
	{
		return product;
	}

	public void setProduct(Product product) 
	{
		this.product = product;
	}

	public double getTotal()
	{
		return product.calPrice() * quantity;
	}
	
	public String getTotalCurrencyFormat()
	{
		return getTotal() + "VND";
	}
}
