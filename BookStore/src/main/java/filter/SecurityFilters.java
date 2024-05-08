package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collection;

@WebFilter("/*")
public class SecurityFilters extends HttpFilter implements Filter 
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException 
	{
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader("Content-Security-Policy", "default-src 'none'; script-src 'self'; connect-src 'self'; img-src 'self'; style-src 'self'; base-uri 'self'; form-action 'self'");
		httpResponse.addHeader("X-Content-Type-Options", "nosniff");
		httpResponse.addHeader("X-Frame-Options", "DENY");
		chain.doFilter(request, httpResponse);
		addSameSiteAttribute(httpResponse);
	}    
	
	private void addSameSiteAttribute(HttpServletResponse response) 
	{
        Collection<String> headers = response.getHeaders("Set-Cookie");
        boolean firstHeader = true;
        for (String header : headers) 
        {  
            if (firstHeader) 
            {
                response.setHeader("Set-Cookie", String.format("%s; %s", header, "SameSite=Strict"));
                firstHeader = false;
                continue;
            }
            response.addHeader("Set-Cookie", String.format("%s; %s", header, "SameSite=Strict"));
        }
    }
}
