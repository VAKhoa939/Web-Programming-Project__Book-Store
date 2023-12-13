package data;

import java.util.*;
import jakarta.persistence.*;
import util.DBUtil;
import business.User;

public class UserDB 
{
	public static void insert(User user) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(user);
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
	
	public static void update(User user) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(user);
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
	
	public static void delete(User user) 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(user));
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
	
	public static boolean emailExists(String email)
	{
		User u = selectUser(email);
		return u != null;
	}
	
	public static User selectUser(String email)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM User u "
				+ "WHERE u.email = :email";
		TypedQuery<User> q = em.createQuery(qString, User.class);
		q.setParameter("email", email);
		try 
		{
			User user = q.getSingleResult();
			return user;
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
	
	public static User selectUser(long userId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM User u "
				+ "WHERE u.userId = :userId";
		TypedQuery<User> q = em.createQuery(qString, User.class);
		q.setParameter("userId", userId);
		try 
		{
			User user = q.getSingleResult();
			return user;
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

	public static List<User> selectUsers()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT u FROM User u ";
		TypedQuery<User> q = em.createQuery(qString, User.class);
		List<User> users;
		try 
		{
			users = q.getResultList();
			if (users == null || users.isEmpty())
				users = null;
		} 
		finally 
		{
			em.close();
		} 
		return users;
	}
}