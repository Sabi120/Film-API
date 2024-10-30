package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAO;
import jakarta.xml.bind.JAXBException;
import models.Film;
import utils.FormatUtilsDelete;
import utils.FormatUtilsGet;
import utils.FormatUtilsPost;
import utils.FormatUtilsPut;

/**
 * Servlet for FilmAPI that fetches, inserts, deletes, updates and films from a
 * database in JSON, XML and Text. Performs CRUD operations based on classes in
 * the utils folder.
 *
 * @author Sabiha Patel
 * @see utils
 */
@WebServlet("/filmapi")
public class FilmAPI extends HttpServlet {

	/** Serialises the class object. */
	private static final long serialVersionUID = 1L;

	/** Instantiates a new dao object to perform SQL CRUD operations on. */
	FilmDAO dao = new FilmDAO();

	/** Accept header for API requests. */
	String accept;

	/** Combines request input and concatenates into a string. */
	String input;

	/**
	 * Constructs a new instance of the FilmAPI class.
	 */
	public FilmAPI() {
		super();

	}

	/**
	 * Performs GET operations on the database.
	 * 
	 * Uses getFormat method to get all films and films if they contain certain
	 * parameters (for search) in JSON, XML and Text formats.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException The servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @see utils.FormatUtilsGet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		accept = request.getHeader("Accept");

		System.out.println("Format: " + accept + "\n");

		/** ArrayList which stores fetched films */
		ArrayList<Film> allFilms = new ArrayList<>();

		try {
			if (request.getParameterMap().containsKey("title")) {

				String title = request.getParameter("title");
				allFilms = dao.getFilmByTitle(title);
				System.out.println("Get by Title");

			} else if (request.getParameterMap().containsKey("id")) {

				int id = Integer.parseInt(request.getParameter("id"));
				allFilms = dao.getFilmByID(id);
				System.out.println("Get by ID");

			} else if (request.getParameterMap().containsKey("year")) {

				int year = Integer.parseInt(request.getParameter("year"));
				allFilms = dao.getFilmByYear(year);
				System.out.println("Get by Year");

			} else if (request.getParameterMap().containsKey("all")) {

				String all = request.getParameter("all");
				allFilms = dao.searchAllFilms(all);
				System.out.println("Get by All Fields");

			} else {

				allFilms = dao.getAllFilms();
				System.out.println("Get All Films");
			}

			if (!allFilms.isEmpty()) {
				getFormat(accept, allFilms, response);
			}
		} catch (SQLException | IOException | JAXBException | NumberFormatException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Calls the FormatUtilsGet methods to get all the films or search parameters in
	 * json, xml or text.
	 * 
	 * Defaults to json.
	 *
	 * @param accept   the HTTP header
	 * @param allFilms    the list of films
	 * @param response the response
	 * @throws IOException   Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * 
	 * @see utils.FormatUtilsGet
	 */
	private void getFormat(String accept, ArrayList<Film> allFilms, HttpServletResponse response)
			throws IOException, JAXBException {
		switch (accept) {
		case "application/json":
			FormatUtilsGet.javaToJson(allFilms, response);
			break;
		case "application/xml":
			FormatUtilsGet.javaToXml(allFilms, response);
			break;
		case "text/plain":
			FormatUtilsGet.javaToText(allFilms, response);
			break;
		default:
			FormatUtilsGet.javaToJson(allFilms, response);
			break;
		}
	}

	/**
	 * Performs POST operations on the database.
	 * 
	 * Uses util methods to add films in JSON, XML and Text formats, defaults to
	 * JSON.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException The servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @see utils.FormatUtilsPost
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		accept = request.getHeader("Content-Type");
		input = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		System.out.println(accept + "\n");

		// Case statement to switch between all formats.
		switch (accept) {
		case "application/json": {
			try {
				FormatUtilsPost.jsonToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding JSON Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		case "application/xml": {
			try {
				FormatUtilsPost.xmlToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding XML Data to the Server");
				e.printStackTrace();
			}
			break;
		}

		case "text/plain": {

			try {
				FormatUtilsPost.textToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding Text Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		default: {
			try {
				FormatUtilsPost.jsonToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding JSON Data to the Server");
				e.printStackTrace();
			}
			break;
		}

		}

	}

	/**
	 * Performs PUT operations on the database.
	 * 
	 * Uses util methods to update films in JSON, XML and Text formats, defaults to
	 * JSON.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException The servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @see utils.FormatUtilsPut
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		accept = request.getHeader("Content-Type");
		input = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		System.out.println(accept + "\n");

		// Case statement to switch between all formats.
		switch (accept) {
		case "application/json": {
			try {
				FormatUtilsPut.jsonToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding JSON Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		case "application/xml": {
			try {
				FormatUtilsPut.xmlToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding XML Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		case "text/plain": {

			try {
				FormatUtilsPut.textToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding Text Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		default: {
			try {
				FormatUtilsPut.jsonToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding JSON Data to the Server");
				e.printStackTrace();
			}
			break;
		}

		}

		if (input == null || input.isEmpty()) {
			System.out.println("Error - No Data to Update");
		}

	}

	/**
	 * Performs DELETE operations on the database.
	 * 
	 * Uses util methods to delete films in JSON, XML and Text formats, defaults to
	 * JSON.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException The servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @see utils.FormatUtilsDelete
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accept = request.getHeader("Content-Type");
		String input = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		System.out.println(accept + "\n");

		// Case statement to switch between all formats.
		switch (accept) {
		case "application/json": {
			try {
				FormatUtilsDelete.jsonToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding JSON Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		case "application/xml": {
			try {
				FormatUtilsDelete.xmlToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding XML Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		case "text/plain": {

			try {
				FormatUtilsDelete.textToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding Text Data to the Server");
				e.printStackTrace();
			}
			break;

		}

		default: {
			try {
				FormatUtilsDelete.jsonToJava(input, request, response);
			} catch (IOException | JAXBException | SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error Forwarding JSON Data to the Server");
				e.printStackTrace();
			}
			break;
		}

		}

		if (input == null || input.isEmpty()) {
			System.out.println("Error - No Data to Delete");
		}

	}

}