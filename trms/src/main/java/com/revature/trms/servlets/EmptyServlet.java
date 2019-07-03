package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.utilities.LogUtilities;

public class EmptyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055135922873015658L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LogUtilities.trace("EmptyServlet");
	}

}
