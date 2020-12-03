package in.jamuna.hms.filters;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.login.SessionDto;


/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

private static final Logger LOGGER=Logger.getLogger(AuthenticationFilter.class.getName());
	
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		
		List<String> excludedUri=GlobalValues.getExcludedUri();
		
		if( GlobalValues.isDevelopmentBuild() ) {
			//mock session
			createFakeSession(httpRequest);
		}
		
		//check if uri isn't an element of ecludedUris
		boolean uriExcluded=false;
		
		String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		for(String element:excludedUri) {
			if(uri.equals(element))
			{
				uriExcluded=true;
				break;
			}
		}
		
		//if none then
		if(!uriExcluded) {
			try {
				HttpSession session=httpRequest.getSession(false);
				SessionDto sessionDto=(SessionDto)session.getAttribute("user");  
				int empId=sessionDto.getEmpId();
				String role=sessionDto.getRole();
				
				httpRequest.setAttribute("empId", empId);
				httpRequest.setAttribute("role", role);
				LOGGER.log(Level.INFO, "LINE 66 : {} ",sessionDto);
				
				
				chain.doFilter(request, response);
				
			}
			catch(Exception e) {
				HttpServletResponse httpResponse=(HttpServletResponse)response;
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/");  
			}
	    
		}else {
			chain.doFilter(request, response);
		}
		    
		
		
		
		
	}
	
	private void createFakeSession(HttpServletRequest httpRequest) {
		HttpSession session=httpRequest.getSession();
		SessionDto dto=new SessionDto();
		dto.setEmpId(12);
		dto.setName("john doe");
		dto.setRole("ADMIN");
		session.setAttribute("user", dto);
	}

	
}
