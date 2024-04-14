package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import business.*;
import data.*;

@WebServlet("/catalog")
public class CatalogController extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ServletContext sc = getServletContext();
		String requestURI = request.getRequestURI();
		String url = null;
		String action = null;

		if (requestURI.endsWith("/catalog"))
		{
			action = request.getParameter("action");
			if (action == null || action.isEmpty())
			{
				url = displayProducts(request, response);
			}
			else
			{
				url = "/errors/error_404.jsp";
			}
			
			sc.getRequestDispatcher(url).forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		doPost(request, response);
	}
	
	private String displayProducts(HttpServletRequest request, HttpServletResponse response)
	{
		String url = null;
		url = "/products.jsp";
		List<Product> products = ProductDB.selectProducts();
		String message = "";
		request.setAttribute("message", message);
		request.setAttribute("products", products);
		return url;
	}
}
