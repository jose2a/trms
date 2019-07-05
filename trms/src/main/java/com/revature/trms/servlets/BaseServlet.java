package com.revature.trms.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.pojos.EmployeeType;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.SessionUtilities;

public abstract class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651555881410342046L;

	/**
	 * Body sent in the request in JSON format.
	 */
	protected String body;
	/**
	 * Parts of the pathInfo. To get variables from URI.
	 */
	protected String[] pathInfoParts;
	/**
	 * Object mapper. Serialize/Deserialize JSON objects.
	 */
	protected ObjectMapper objectMapper;

	private DoGetMethod doGetMethodImpl;
	private DoPostMethod doPostMethodImpl;
	private DoPutMethod doPutMethodImpl;
	private DoDeleteMethod doDeleteMethodImpl;

	abstract boolean validateAuthorization(List<EmployeeType> employeeTypes);

	public BaseServlet() {
		LogUtilities.trace("BaseServlet - Constructor");

		if (this instanceof DoGetMethod) {
			this.doGetMethodImpl = (DoGetMethod) this;
		}

		if (this instanceof DoPostMethod) {
			this.doPostMethodImpl = (DoPostMethod) this;
		}

		if (this instanceof DoPutMethod) {
			this.doPutMethodImpl = (DoPutMethod) this;
		}

		if (this instanceof DoDeleteMethod) {
			this.doDeleteMethodImpl = (DoDeleteMethod) this;
		}

		objectMapper = new ObjectMapper();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("BaseServlet: GET");

		if (doGetMethodImpl == null) {
			LogUtilities.trace("Not Implemented");

			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}

		if (handleAuthenticationAndAuthorization(request, response)) {

			setJsonResponseHeaders(response);

			if (request.getPathInfo() != null) {
				pathInfoParts = request.getPathInfo().split("/");
			} else {
				pathInfoParts = new String[0];
			}

			LogUtilities.trace("Path info parts: " + String.join(", ", pathInfoParts));

			doGetMethodImpl.get(request, response);
		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("BaseServlet: POST");

		if (doPostMethodImpl == null) {
			LogUtilities.trace("Not Implemented");

			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}

		if (handleAuthenticationAndAuthorization(request, response)) {

			setJsonResponseHeaders(response);

			body = readRequestBody(request);

			doPostMethodImpl.post(request, response);
		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("BaseServlet: PUT");

		if (doPutMethodImpl == null) {
			LogUtilities.trace("Not Implemented");

			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}

		if (handleAuthenticationAndAuthorization(request, response)) {

			setJsonResponseHeaders(response);

			body = readRequestBody(request);

			doPutMethodImpl.put(request, response);
		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogUtilities.trace("BaseServlet: DELETE");

		if (doDeleteMethodImpl == null) {
			LogUtilities.trace("Not Implemented");

			response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}

		if (handleAuthenticationAndAuthorization(request, response)) {

			setJsonResponseHeaders(response);

			pathInfoParts = request.getPathInfo().split("/");

			LogUtilities.trace("Path info parts: " + String.join("", pathInfoParts));

			doDeleteMethodImpl.delete(request, response);
		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean handleAuthenticationAndAuthorization(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LogUtilities.trace("handleAuthenticationAndAuthorization");

		boolean authenticate = validateAuthentication(request, response);
		boolean authorized = false;

		if (authenticate) {

			if (SessionUtilities.isEmployeeInSession(request)) {
				List<EmployeeType> employeeTypes = SessionUtilities.getEmployeeFromSession(request).getEmployeeTypes();

				authorized = validateAuthorization(employeeTypes);

				if (!authorized) {
					LogUtilities.trace("Not authorized");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}
			}
		}

		if (!authorized || !authenticate) {
			LogUtilities.info("Not authenticated or not authorized");

			return false;

		}

		return true;
	}

	// Setting Json response content type
	protected void setJsonResponseHeaders(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}

	/**
	 * Validation if user is in the session, otherwise return Unauthorized ststus.
	 * 
	 * @param request  The request
	 * @param response The response
	 * @throws IOException
	 * @throws ServletException
	 */
	// TODO Activate this when everything is tested
	private boolean validateAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!SessionUtilities.isEmployeeInSession(request)) {

			LogUtilities.trace("Employee not in session");

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			// request.getRequestDispatcher("/empty").forward(request, response);

			return false;
		}

		return true;
	}

	protected String readRequestBody(HttpServletRequest request) throws IOException {
		List<String> lines = request.getReader().lines().collect(Collectors.toList());

		String body = String.join("\n", lines);
		LogUtilities.trace("Body: " + body);

		return body;
	}

	protected String getUri(HttpServletRequest request) {
		String uri = request.getScheme() + "://" + // "http" + "://
				request.getServerName() + // "myhost"
				":" + // ":"
				request.getServerPort() + // "8080"
				request.getRequestURI();

		LogUtilities.trace(uri);

		return uri;
	}

}
