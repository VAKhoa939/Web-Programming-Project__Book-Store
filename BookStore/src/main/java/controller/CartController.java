package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import business.*;
import data.*;
import util.*;

@WebServlet("/cart")
public class CartController extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext sc = getServletContext();
		String requestURI = request.getRequestURI();
		String url = null;
		String action = null;
		
		if (requestURI.endsWith("/cart"))
		{
			if (checkActiveSession(request, response))
			{
				action = request.getParameter("action");
				if (action == null || action.isEmpty())
				{
					url = displayCart(request, response);
				}
				else if (action.equals("new"))
				{
					url = addToCart(request, response);
				}
				else if (action.equals("change"))
				{
					url = changeQuantity(request, response);
				}
				else if (action.equals("remove"))
				{
					url = removeItem(request, response);
				}
				else
				{
					url = "/errors/error_404.jsp";
				}
			}
			else
			{
				url = "/login.jsp";
			}
			sc.getRequestDispatcher(url).forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}
	
	private boolean checkActiveSession(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}

		if (user != null) 
		{
			return true;
		} 
		else 
		{
			Cookie[] cookies = request.getCookies();
			String userEmail = CookieUtil.getCookieValue(cookies, "userEmail");
			if (userEmail == null || userEmail.isBlank()) 
			{
				return false;
			}
			else
			{
				user = UserDB.selectUser(userEmail);
				synchronized(lock)
				{
					session.setAttribute("user", user);
				}
				return true;
			}
		}
	}
	
	private Cart getCart(User user) 
	{
		Cart cart = CartDB.selectCart(user.getUserId());
		if (cart == null) 
		{
			cart = new Cart(user);
			cart.setActive(true);
			CartDB.insert(cart);
		}
		return cart;
	}
	
	private String displayCart(HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}
		String url = "/cart.jsp";
		
		Cart cart = getCart(user);
		
		request.setAttribute("cart", cart);
		return url;
	}

	private String addToCart(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}
		String url = "/product_detail.jsp";
		
		String productCode = request.getParameter("productCode");
		Cart cart = getCart(user);
		boolean itemExists = false;
		for (LineItem item : cart.getItems())
		{
			if (item.getProduct().getProductCode().equals(productCode))
			{
				itemExists = true;
				item.setQuantity(item.getQuantity() + 1);;
				LineItemDB.update(item);
				break;
			}
		}
		if (!itemExists)
		{
			Product product = ProductDB.selectProduct(productCode);
			LineItem item = new LineItem(1, product);
			LineItemDB.insert(item);
			
			cart.addItem(item);
			CartDB.update(cart);
		}
		String message = "Product has been added to cart.";
		Product product = ProductDB.selectProduct(productCode);
		request.setAttribute("product", product);
		request.setAttribute("message", message);
		return url;
	}
	
	private String changeQuantity(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}
		
		Cart cart = getCart(user);
		String productCode = request.getParameter("productCode");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		if (quantity <= 0)
		{
			System.out.println(quantity);
			return removeItem(request, response);
		}
		else
		{
			for (LineItem item : cart.getItems()) 
			{
				if (item.getProduct().getProductCode().equals(productCode))
				{
					item.setQuantity(quantity);
					LineItemDB.update(item);
					CartDB.update(cart);
					break;
				}
			}
			return displayCart(request, response);
		}
	}
	
	private String removeItem(HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}
		String url = "/cart.jsp";
		
		Cart cart = getCart(user);
		String productCode = request.getParameter("productCode");
		
		List<LineItem> items = cart.getItems();
		System.out.println(cart.getItems().size());
		for (LineItem item : items) 
		{
			if (item.getProduct().getProductCode().equals(productCode))
			{
				System.out.println(cart.getItems().size());
				cart.removeItem(item);
				CartDB.update(cart);
				System.out.println(cart.getItems().size());
				LineItemDB.delete(item);
				break;
			}
		}
		url = displayCart(request, response);
		
		return url;
	}
}
