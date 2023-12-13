package business;

import java.io.Serializable;
import java.util.*;
import jakarta.persistence.*;

@Entity
public class Description implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long descId;
	private String bookName;
	private String author;
	private String publisher;
	private String detail;
	private String genre;
	private Date releaseDate;
	
	public Description()
	{
		bookName = "";
		author = "";
		publisher = "";
		detail = "";
		genre = "";
		releaseDate = new Date();
	}

	public Description(String bookName, String author, String publisher, String detail, String genre, Date releaseDate)
	{
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.detail = detail;
		this.genre = genre;
		this.releaseDate = releaseDate;
	}

	public long getDescId() 
	{
		return descId;
	}

	public void setDescId(long descId) 
	{
		this.descId = descId;
	}

	public String getBookName() 
	{
		return bookName;
	}

	public void setBookName(String bookName) 
	{
		this.bookName = bookName;
	}

	public String getAuthor() 
	{
		return author;
	}

	public void setAuthor(String author) 
	{
		this.author = author;
	}

	public String getPublisher() 
	{
		return publisher;
	}

	public void setPublisher(String publisher) 
	{
		this.publisher = publisher;
	}

	public String getDetail() 
	{
		return detail;
	}

	public void setDetail(String detail) 
	{
		this.detail = detail;
	}
	
	public String getGenre() 
	{
		return genre;
	}

	public void setGenre(String genre) 
	{
		this.genre = genre;
	}

	public Date getReleaseDate() 
	{
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) 
	{
		this.releaseDate = releaseDate;
	}
}
