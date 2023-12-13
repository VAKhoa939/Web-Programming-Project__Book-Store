package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBUtil 
{
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookStorePU");
	
	public static EntityManagerFactory getEmFactory()
	{
		return emf;
	}
}

