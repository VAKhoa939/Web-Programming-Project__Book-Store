package data;

import java.util.*;
import jakarta.persistence.*;
import util.DBUtil;
import business.Product;

public class ProductDB 
{
	public static void insert(Product product) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(product);
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
	
	public static void update(Product product) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(product);
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
	
	public static void delete(Product product) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(product));
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
	
	public static boolean productCodeExists(String productCode)
	{
		Product u = selectProduct(productCode);
		return u != null;
	}
	
	public static Product selectProduct(String productCode)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM Product u "
				+ "WHERE u.productCode = :productCode";
		TypedQuery<Product> q = em.createQuery(qString, Product.class);
		q.setParameter("productCode", productCode);
		try 
		{
			Product product = q.getSingleResult();
			return product;
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

	public static List<Product> selectProducts()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM Product u ";
		TypedQuery<Product> q = em.createQuery(qString, Product.class);
		List<Product> products;
		try 
		{
			products = q.getResultList();
			if (products == null || products.isEmpty())
				products = null;
		} 
		finally 
		{
			em.close();
		} 
		return products;
	}
}