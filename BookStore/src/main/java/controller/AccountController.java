package controller;

import jakarta.servlet.*;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

import business.*;
import data.*;
import util.*;

@WebServlet("/account")
public class AccountController extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(true); // Create session if it doesn't exist
        String csrfToken = (String) session.getAttribute("csrf_token");
        if (csrfToken == null) {
            csrfToken = generateCSRFToken(); // Generate CSRF token
            session.setAttribute("csrf_token", csrfToken); // Store CSRF token in session
        }
        request.setAttribute("csrfToken", csrfToken); // Add CSRF token to request attributes
		ServletContext sc = getServletContext();
		String requestURI = request.getRequestURI();
		String url = null;
		String action = null;
		
		if (requestURI.endsWith("/account"))
		{
			action = request.getParameter("action");
			if (checkActiveSession(request, response))
			{
				if (action == null || action.isBlank() || action.length() > 50)
				{
					url = "/account.jsp";
				}
				else if (action.equals("logout"))
				{
					url = logout(request, response);
				}
			}
			else
			{
				if (action == null || action.isBlank() || action.length() > 50)
				{
					url = "/login.jsp";
				}
				else if (action.equals("login"))
				{
					url = login(request, response);
				}
				else if (action.equals("register"))
				{
					url = register(request, response);
				}
				else
				{
					url = "/errors/error_404.jsp";
				}
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
	
	private String login(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		String message = null;
		String url = null;

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String csrfToken = request.getParameter("csrf_token");
		
		if (email == null|| password == null || email.isBlank() || password.isBlank())
		{
			message = "Please fill out all information.";
			url = "/login.jsp";
			request.setAttribute("email", email);
			request.setAttribute("password", password);
		}
        else if (!validateCSRFToken(session, csrfToken)) 
        {
            message = "CSRF token mismatch!";
            url = "/login.jsp";
        }
		else if(email.length() > 50 || password.length() > 50)
        {
            message = "Email or Password can not exceed 50 characters.";
            url = "/login.jsp";
            request.setAttribute("email", email);
            request.setAttribute("password", password);
        }
		else if (UserDB.emailExists(email)) 
		{
			User temp = UserDB.selectUser(email);
			if (password.equals(temp.getPassword())) 
			{
				synchronized (lock)
				{
					session.setAttribute("user", temp);
				}
				
				Cookie c = new Cookie("userEmail", email);
				c.setMaxAge(60 * 60 * 24 * 30);
				c.setPath("/");
				c.setHttpOnly(true);
				response.addCookie(c);
				
				message = "";
				url = "/account.jsp";
			} 
			else 
			{
				message = "Wrong password.";
				url = "/login.jsp";
				request.setAttribute("email", email);
				request.setAttribute("password", null);
			}
		} 
		else 
		{
			message = "Account does not exist.";
			url = "/login.jsp";
			request.setAttribute("email", null);
			request.setAttribute("password", null);
		}
		request.setAttribute("message", message);
		return url;
	}
	
	private String register(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		String message = null;
		String url = null;
		String mode = "register";

		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String addr = request.getParameter("addr");
		String phoneNo = request.getParameter("phoneNo");
		String password = request.getParameter("password");
		String csrfToken = request.getParameter("csrf_token");
		
		if (userName == null || email == null || addr == null || phoneNo == null || password == null
				|| userName.isBlank() || email.isBlank() || addr.isBlank() || phoneNo.isBlank() || password.isBlank())
		{
			message = "Please fill out all information.";
			url = "/login.jsp";
			request.setAttribute("userName", userName);
			request.setAttribute("email", email);
			request.setAttribute("addr", addr);
			request.setAttribute("phoneNo", phoneNo);
			request.setAttribute("password", password);
		}
		else if (!validateCSRFToken(session, csrfToken)) 
		{
            message = "CSRF token mismatch!";
            url = "/login.jsp";
        }
		else if (userName.length() > 50 || email.length() > 50 || addr.length() > 50 || phoneNo.length() > 50 || password.length() > 50)
	    {
			message = "Username, Email, Address, Phone Number and Password can not exceed 50 characters.";
			url = "/login.jsp";
			request.setAttribute("userName", userName);
			request.setAttribute("email", email);
			request.setAttribute("addr", addr);
			request.setAttribute("phoneNo", phoneNo);
			request.setAttribute("password", password);
	    }
		else if (UserDB.emailExists(email)) 
		{
			message = "Account already exists. Please use another email address.";
			url = "/login.jsp";
			request.setAttribute("userName", userName);
			request.setAttribute("email", null);
			request.setAttribute("addr", addr);
			request.setAttribute("phoneNo", phoneNo);
			request.setAttribute("password", password);
		}
		else 
		{
			User user = new User(userName, email, addr, phoneNo, password);
			synchronized (lock)
			{
				session.setAttribute("user", user);
			}
			UserDB.insert(user);
			Cookie c = new Cookie("userEmail", email);
			c.setMaxAge(60 * 60 * 24 * 30);
			c.setHttpOnly(true);
			c.setPath("/");
			response.addCookie(c);
			
			message = "";
			url = "/account.jsp";
		}
		request.setAttribute("message", message);
		return url;
	}
	
	private String logout(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		final Object lock = session.getId().intern();
		User user;
		synchronized(lock)
		{
			user = (User) session.getAttribute("user");
		}
		String url = "/login.jsp";
		String mode = "login";
		String message = null;
		
		if (user != null) 
		{
			synchronized(lock)
			{
				session.removeAttribute("user");
				session.invalidate();
			}

			Cookie[] cookies = request.getCookies();
			String userEmail = CookieUtil.getCookieValue(cookies, "userEmail");
			Cookie c = new Cookie("userEmail", userEmail);
			c.setMaxAge(0);
			c.setPath("/");
			
			response.addCookie(c);
		}
		request.setAttribute("mode", mode);
		request.setAttribute("message", message);
		return url;
	}
	
    private String generateCSRFToken() 
    {
        return UUID.randomUUID().toString();
    }

    private boolean validateCSRFToken(HttpSession session, String requestCSRFToken) 
    {
        String sessionCSRFToken = (String) session.getAttribute("csrf_token");
        return requestCSRFToken != null && requestCSRFToken.equals(sessionCSRFToken);
    }
}
