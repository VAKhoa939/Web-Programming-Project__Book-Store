package data;

import java.util.*;
import jakarta.persistence.*;
import util.DBUtil;
import business.Invoice;

public class InvoiceDB 
{
	public static void insert(Invoice invoice) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(invoice);
			trans.commit();
		}
		catch (Exception e)
		{
			System.out.println(e);
			trans.rollback();
		}
		finally 
		{
			em.close();
		}
	}
	
	public static void update(Invoice invoice) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(invoice);
			trans.commit();
		}
		catch (Exception e)
		{
			System.out.println(e);
			trans.rollback();
		}
		finally 
		{
			em.close();
		}
	}
	
	public static void delete(Invoice invoice) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(invoice));
			trans.commit();
		}
		catch (Exception e)
		{
			System.out.println(e);
			trans.rollback();
		}
		finally 
		{
			em.close();
		}
	}
	
	public static boolean invoiceExists(long cartId)
	{
		Invoice u = selectInvoice(cartId);
		return u != null;
	}
	
	public static Invoice selectInvoice(long cartId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM Invoice u "
				+ "WHERE u.cart.cartId = :cartId";
		TypedQuery<Invoice> q = em.createQuery(qString, Invoice.class);
		q.setParameter("cartId", cartId);
		try 
		{
			Invoice invoice = q.getSingleResult();
			return invoice;
		} 
		catch (NoResultException e) 
		{
			System.out.println(e);
			return null;
		} 
		finally 
		{
			em.close();
		} 
	}

	public static List<Invoice> selectInvoices()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM Invoice u ";
		TypedQuery<Invoice> q = em.createQuery(qString, Invoice.class);
		List<Invoice> invoices;
		try 
		{
			invoices = q.getResultList();
			if (invoices == null || invoices.isEmpty())
				invoices = null;
		} 
		finally 
		{
			em.close();
		} 
		return invoices;
	}
}