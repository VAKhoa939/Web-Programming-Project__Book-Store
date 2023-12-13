package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import business.*;
import data.*;

@WebServlet("/detail")
public class ProductController extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext sc = getServletContext();
		String requestURI = request.getRequestURI();
		String url = null;
		String action = null;

		if (requestURI.endsWith("/detail"))
		{
			url = displayProductDetail(request, response);
			sc.getRequestDispatcher(url).forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	private String displayProductDetail(HttpServletRequest request, HttpServletResponse response)
	{
		String url = "/product_detail.jsp";
		String productCode = request.getParameter("productCode");
		Product product = ProductDB.selectProduct(productCode);
		request.setAttribute("product", product);
		return url;
	}
}
