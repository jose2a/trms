package com.revature.trms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DoPutMethod {
	
	void put(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
