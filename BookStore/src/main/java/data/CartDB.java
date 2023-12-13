package data;

import java.util.*;
import jakarta.persistence.*;
import util.DBUtil;
import business.Cart;

public class CartDB 
{
	public static void insert(Cart cart) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(cart);
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
	
	public static void update(Cart cart) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(cart);
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
	
	public static void delete(Cart cart) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(cart));
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
	
	public static boolean cartExists(long userId)
	{
		Cart u = selectCart(userId);
		return u != null;
	}
	
	public static Cart selectCart(long userId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM Cart u "
				+ "WHERE u.user.userId = :userId AND u.active = true";
		TypedQuery<Cart> q = em.createQuery(qString, Cart.class);
		q.setParameter("userId", userId);
		try 
		{
			Cart cart = q.getSingleResult();
			return cart;
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

	public static List<Cart> selectCarts()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM Cart u ";
		TypedQuery<Cart> q = em.createQuery(qString, Cart.class);
		List<Cart> carts;
		try 
		{
			carts = q.getResultList();
			if (carts == null || carts.isEmpty())
				carts = null;
		} 
		finally 
		{
			em.close();
		} 
		return carts;
	}
}