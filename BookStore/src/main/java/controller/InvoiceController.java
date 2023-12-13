package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

import business.*;
import data.*;
import util.*;

@WebServlet("/invoice")
public class InvoiceController extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext sc = getServletContext();
		String requestURI = request.getRequestURI();
		String url = null;
		String action = null;
		
		if (requestURI.endsWith("/invoice"))
		{
			action = request.getParameter("action");
			if (checkActiveSession(request, response))
			{
				if (action == null || action.isBlank())
				{
					url = "/cart.jsp";
				}
				else if (action.equals("checkout"))
				{
					url = display(request, response);
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
	
	private Invoice getInvoice(HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}
		
		Cart cart = CartDB.selectCart(user.getUserId());
		Invoice invoice = InvoiceDB.selectInvoice(cart.getCartId());
		if (invoice == null) 
		{
			user.setNoInvoices(user.getNoInvoices() + 1);
			UserDB.update(user);
			synchronized(lock)
			{
				session.setAttribute("user", user);
			}
			cart.setActive(false);
			CartDB.update(cart);
			invoice = new Invoice(cart);
			invoice.setInvoiceNum(user.getNoInvoices());
			invoice.setDeliveryAddr(user.getAddr());
			InvoiceDB.insert(invoice);
		}
		return invoice;
	}
	
	private String display(HttpServletRequest request, HttpServletResponse response)
	{
		String url = "/invoice.jsp";
		
		Invoice invoice = getInvoice(request, response);
		request.setAttribute("invoice", invoice);
		return url;
	}
}
