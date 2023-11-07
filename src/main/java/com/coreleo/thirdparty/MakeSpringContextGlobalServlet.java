package com.coreleo.thirdparty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.coreleo.Toolbox;

public class MakeSpringContextGlobalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public MakeSpringContextGlobalServlet() {
		super();
	}
	
	
	public void init() throws ServletException {
		super.init();
		Object ctx = SpringUtil.getApplicationContext( super.getServletContext() );
		Toolbox toolbox = Toolbox.getInstance();
		toolbox.register("applicationContext", ctx);
	}



}
