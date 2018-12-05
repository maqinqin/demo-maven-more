/**  
 * All rights Reserved, Designed By www.gitcloud.com.cn
 * @Title:  XssFilter.java   
 * @Package com.git.cloud.common.xss   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: chengbin   
 * @date:   2018年8月14日 下午4:19:52   
 * @version V1.0 
 * @Copyright: 2018 www.gitcloud.com.cn Inc. All rights reserved. 
 */
package com.git.cloud.common.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.stereotype.Component;

/**   
 * @ClassName:  XssFilter 
 * @Package: com.git.cloud.common.xss  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: chengbin
 * @date:   2018年8月14日 下午4:19:52     
 * @Copyright: 2018 www.gitCloud.com Inc. All rights reserved. 
 */
@Component
public class XssFilter implements Filter{
	
	private static final String FILTER_APPLIED = XssFilter.class.getName() + ".FILTERED";
	
	/**   
	 * <p>Title: init</p>   
	 * <p>Description: </p>   
	 * @param filterConfig
	 * @throws ServletException   
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)   
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	/**   
	 * <p>Title: doFilter</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException   
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)   
	 */
	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
		if( !( request instanceof HttpServletRequest ) || !( response instanceof HttpServletResponse ) ) {
			throw new ServletException( "XSSFilter just supports HTTP requests" );
		}
		HttpServletRequest httpRequest = ( HttpServletRequest )request;
		String uri = httpRequest.getRequestURI();
		
		// Apply Filter
		if( null != httpRequest.getAttribute( FILTER_APPLIED ) ) {
			chain.doFilter( request, response );
			return;
		}
		
		try {
			request.setAttribute( FILTER_APPLIED, Boolean.TRUE );
			SecurityRequestWrapper requestWrapper = new SecurityRequestWrapper( httpRequest );
			ESAPI.httpUtilities().setCurrentHTTP((HttpServletRequest)requestWrapper, (HttpServletResponse)response);
			chain.doFilter( requestWrapper, response );
		} finally {
			httpRequest.removeAttribute( FILTER_APPLIED );
		}
	}

	/**   
	 * <p>Title: destroy</p>   
	 * <p>Description: </p>      
	 * @see javax.servlet.Filter#destroy()   
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
