package data;

import java.util.*;
import jakarta.persistence.*;
import util.DBUtil;
import business.LineItem;

public class LineItemDB 
{
	public static void insert(LineItem item) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(item);
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
	
	public static void update(LineItem item) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(item);
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
	
	public static void delete(LineItem item) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(item));
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
	
	public static LineItem selectLineItem(String itemId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM LineItem u "
				+ "WHERE u.itemId = :itemId";
		TypedQuery<LineItem> q = em.createQuery(qString, LineItem.class);
		q.setParameter("itemId", itemId);
		try 
		{
			LineItem lineItem = q.getSingleResult();
			return lineItem;
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
	
	public static List<LineItem> selectLineItems()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM LineItem u ";
		TypedQuery<LineItem> q = em.createQuery(qString, LineItem.class);
		List<LineItem> lineItems;
		try 
		{
			lineItems = q.getResultList();
			if (lineItems == null || lineItems.isEmpty())
				lineItems = null;
		} 
		finally 
		{
			em.close();
		} 
		return lineItems;
	}
	
	public static List<LineItem> selectLineItems(String productCode)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM LineItem u "
				+ "WHERE u.product.productCode = :productCode";
		TypedQuery<LineItem> q = em.createQuery(qString, LineItem.class);
		q.setParameter("productCode", productCode);
		List<LineItem> lineItems;
		try 
		{
			lineItems = q.getResultList();
			if (lineItems == null || lineItems.isEmpty())
				lineItems = null;
		} 
		finally 
		{
			em.close();
		} 
		return lineItems;
	}
}