package controller;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

import java.util.*;
import business.*;
import data.*;


@WebServlet("/bs")
public class DatabaseController extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
        
        String url = "/admin.jsp";
        ServletContext sc = getServletContext();
        String requestURI = request.getRequestURI();
        String action = request.getParameter("action");
        log("action = " + action);

        if (requestURI.endsWith("/bs")) 
        {
            action = request.getParameter("action");
            if (action == null || action.isBlank()) 
            {
                List<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/admin.jsp";
            } 
            
            // update user
            else if (action.equals("update")) 
            {
                String email = request.getParameter("email");

                String message;
                if (email == null || email.isBlank()) 
                {
                    message = "Failed to update the user because the email is empty.";
                } 
                else 
                {
                    String userName = request.getParameter("userName");
                    String addr = request.getParameter("addr");
                    String phoneNo = request.getParameter("phoneNo");
                    User user = UserDB.selectUser(email);
                    if (userName != null && !userName.isBlank()) 
                    {
                        user.setUserName(userName);
                    }
                    if (addr != null && !addr.isBlank()) 
                    {
                        user.setAddr(addr);
                    }
                    if (phoneNo != null && !phoneNo.isBlank()) 
                    {
                        user.setPhoneNo(phoneNo);
                    }

                    UserDB.update(user);
                    message = "Successfully updated the user.";
                }

                request.setAttribute("message", message);
            } 
            
            // update product
            else if (action.equals("updateProduct")) 
            {
                String productCode = request.getParameter("productCode");
                
                String message;
                if (productCode == null || productCode.isBlank()) 
                {
                    message = "Failed to update the product because the product code is empty.";
                } 
                else 
                {
                    String price = request.getParameter("price");
                    String discount = request.getParameter("discount");
                    Product product = ProductDB.selectProduct(productCode);
                    if (price != null && !price.isBlank()) 
                    {
                        try 
                        {
                            double price2 = Double.parseDouble(price);
                            product.setPrice(price2);
                        } 
                        catch (NumberFormatException e) 
                        {
                            message = "Invalid price format. Please enter a valid number.";
                        }
                    }
                    if (discount != null && !discount.isBlank()) 
                    {
                        try 
                        {
                            double discount2 = Double.parseDouble(discount);
                            product.setDiscount(discount2);
                        } 
                        catch (NumberFormatException e) 
                        {
                            message = "Invalid price format. Please enter a valid number.";
                        }
                    }
                    ProductDB.update(product);
                    message = "Successfully updated the product.";
                }
                
                request.setAttribute("message", message);
            }
            
            // update description
            else if (action.equals("updateDescription")) 
            {
                String bookName = request.getParameter("bookName");
                
                String message;
                if (bookName == null || bookName.isBlank()) 
                {
                    message = "Failed to update the product because the book name is empty.";
                } 
                else 
                {
                    String author = request.getParameter("author");
                    String publisher = request.getParameter("publisher");
                    String detail = request.getParameter("detail");
                	Description desc = DescriptionDB.selectDescription(bookName);
                	if (author != null && !author.isBlank())
                	{
                		desc.setAuthor(author);
                	}
                	if (publisher != null && !publisher.isBlank())
                	{
                		desc.setPublisher(publisher);
                	}
                	if (detail != null && !detail.isBlank())
                	{
                		desc.setDetail(detail);
                	}
                    DescriptionDB.update(desc);
                    message = "Successfully updated the description.";
                    request.setAttribute("message", message);
                }
            } 
            
            // delete a user
            else if (action.equals("delete")) 
            { 
                String email = request.getParameter("email");
                String message;
                if (email == null || email.isBlank()) 
                {
                    message = "Failed to delete the user because the email is empty.";
                } 
                else if ("andi@murach.com".equalsIgnoreCase(email) || 
                           "joelmurach@yahoo.com".equalsIgnoreCase(email) || 
                           "mike@murach.com".equalsIgnoreCase(email)) 
                {
                    message = "Cannot delete this user.";
                } 
                else 
                {
                    User user = UserDB.selectUser(email);
                    if (CartDB.cartExists(user.getUserId()))
                    {
                        Cart cart = CartDB.selectCart(user.getUserId());
                        if (InvoiceDB.invoiceExists(cart.getCartId()))
                        {
                            Invoice invoice = InvoiceDB.selectInvoice(cart.getCartId());
                            InvoiceDB.delete(invoice);
                        }
                        CartDB.delete(cart);
                    }
                    UserDB.delete(user);
                    message = "Successfully deleted the user and associated information.";
                }

                request.setAttribute("message", message);
            } 
            
            // delete a product
            else if (action.equals("deleteProduct")) 
            {
                String productCode = request.getParameter("productCode");
                String message;
                if (productCode == null || productCode.isBlank()) 
                {
                    message = "Failed to delete the product because the product code is empty.";
                } 
                else 
                {
                    Product product = ProductDB.selectProduct(productCode);
                    ProductDB.delete(product);
                    message = "Successfully deleted the product.";
                }

                request.setAttribute("message", message);
                
            } 
            
            // delete a description
            else if (action.equals("deleteDescription")) 
            {
                String bookName = request.getParameter("bookName");
                String message;
                if (bookName == null || bookName.isBlank()) 
                {
                    message = "Failed to delete description because the book name is empty.";
                } 
                else 
                {
                    Description description = DescriptionDB.selectDescription(bookName);
                    DescriptionDB.delete(description);
                    message = "Successfully deleted the description.";
                }
                request.setAttribute("message", message);
            
            } 
            
            // insert a new user
            else if (action.equals("insert")) 
            {
                String userName = request.getParameter("userName");
                String email = request.getParameter("email");
                String addr = request.getParameter("addr");
                String phoneNo = request.getParameter("phoneNo");

                String message;
                if (userName == null || email == null || addr == null || phoneNo == null
                        || userName.isBlank() || email.isBlank() || addr.isBlank() || phoneNo.isBlank()) {
                    message = "Please fill out all four text boxes.";
                } 
                else if (UserDB.emailExists(email)) 
                {
                    message = "This email address already exists. Please enter another email address.";
                } 
                else 
                {
                    User user = new User(userName, email, addr, phoneNo, "");
                    UserDB.insert(user);
                    message = "Successfully inserted the user.";
                }

                request.setAttribute("message", message);
	        } 
            
            // insert a new product
            else if (action.equals("insertProduct")) 
            {
	            String productCode = request.getParameter("productCode");
	            String bookName = request.getParameter("bookName");
	            String priceStr = request.getParameter("price");
	            
	            String message;
	            if (productCode == null || productCode.isBlank() || bookName == null || bookName.isBlank() || priceStr == null || priceStr.isBlank()) {
	                message = "Please provide both the product code and the price for insertion.";
	            } 
	            else 
	            {
	                try 
	                {
	                    double price = Double.parseDouble(priceStr);
	                    Product existingProduct = ProductDB.selectProduct(productCode);
	                    if (existingProduct == null) {
	                    	Description info = DescriptionDB.selectDescription(bookName);
	                        Product newProduct = new Product(productCode,info, price);
	                        ProductDB.insert(newProduct);
	                        message = "Successfully inserted the new product.";
	                    } 
	                    else 
	                    {
	                        message = "Product with the given code already exists.";
	                    }
	                } 
	                catch (NumberFormatException e) 
	                {
	                    message = "Invalid price format. Please enter a valid number.";
	                }
	            }
	            request.setAttribute("message", message);
	        } 
            
            // insert a new description
            else if (action.equals("insertDescription")) 
            {
	            String bookName = request.getParameter("bookName");
	            String author = request.getParameter("author");
	            String publisher = request.getParameter("publisher");
	            String detail = request.getParameter("detail");
	            String genre = request.getParameter("genre");
	            
	            String message;
	            if (bookName == null || bookName.isBlank() || author == null || author.isBlank() ||
	                publisher == null || publisher.isBlank() || detail == null || detail.isBlank() ||
	                genre == null || genre.isBlank()) 
	            {
	                message = "Please fill out all fields for description insertion.";
	            } 
	            else if (DescriptionDB.bookNameExists(bookName))
	            {
	            	message = "This book name already existed!";
	            }
	            else 
	            {
                    Description newDescription = new Description(bookName, author, publisher, detail, genre, new Date());
                    DescriptionDB.insert(newDescription);
                    message = "Successfully inserted the new description.";
	            }
	            request.setAttribute("message", message);
	        }
	        List<User> users = UserDB.selectUsers();
	        List<Product> products = ProductDB.selectProducts();
	        List<Description> descriptions = DescriptionDB.selectDescriptions();
            List<LineItem> lineItems = LineItemDB.selectLineItems();
            List<Cart> carts = CartDB.selectCarts();
            List<Invoice> invoices = InvoiceDB.selectInvoices();
            
	        request.setAttribute("users", users);
	        request.setAttribute("products", products);
            request.setAttribute("descriptions", descriptions);
            request.setAttribute("lineItems", lineItems);
            request.setAttribute("carts", carts);
            request.setAttribute("invoices", invoices);
            
	        sc.getRequestDispatcher(url).forward(request, response);
        }
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
        doPost(request, response);
    }
}
