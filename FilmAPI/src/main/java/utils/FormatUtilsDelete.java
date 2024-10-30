package utils;

import database.FilmDAO;
import models.Film;

import java.io.IOException;
import java.sql.SQLException;

import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Java utils class that parses the response in JSON, XML and Text formats for
 * the DELETE API operation to delete a film based on the ID. Validates the ID
 * input
 * 
 * @see controllers.FilmAPI
 *
 * @author Sabiha Patel
 */
public class FormatUtilsDelete {

	/**
	 * Parses Films from JSON to Java Uses Gson library to parse from a JSON film
	 * object to a Java film object.
	 * 
	 * @param input    the input
	 * @param request  the request
	 * @param response the response
	 * @throws IOException   Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @throws SQLException the SQL exception
	 */
	public static void jsonToJava(String input, HttpServletRequest request, HttpServletResponse response)
			throws IOException, JAXBException, SQLException {
		/** Accesses the DAO to perform SQL operation */
		FilmDAO dao = new FilmDAO();

		/** Creates new film class object to perform operation on */
		Film film = null;

		/** Prints out the response to the server */
		PrintWriter out = response.getWriter();

		/** Gson library to parse */
		Gson gson = new Gson();

		if (input != null && !input.isEmpty()) {
			film = gson.fromJson(input, Film.class);

			// Validate ID
			if (!Validator.isValidId(String.valueOf(film.getId()))) {
				throw new IllegalArgumentException("Invalid ID: " + film.getId());
			}

			dao.deleteFilm(film.getId());
		}

		out.write("JSON Film with ID: '" + film.getId() + "' Deleted Successfully.");

		System.out.println("Film with ID: '" + film.getId() + "' Deleted Successfully.\n");
		response.setStatus(HttpServletResponse.SC_OK);

	}

	/**
	 * Parses Films from XML to Java Uses JAXB library to parse from a XML film
	 * object to a Java film object.
	 * 
	 * @param input    the input
	 * @param request  the request
	 * @param response the response
	 * @throws IOException   Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @throws SQLException  the SQL exception
	 */
	public static Film xmlToJava(String input, HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, IOException, SQLException {

		/** Accesses the DAO to perform SQL operation */
		FilmDAO dao = new FilmDAO();

		/** Creates new film class object to perform operation on */
		Film film = null;

		/** Prints out the response to the server */
		PrintWriter out = response.getWriter();

		if (input != null && !input.isEmpty()) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			film = (Film) unmarshaller.unmarshal(new StringReader(input));

			// Validate ID
			if (!Validator.isValidId(String.valueOf(film.getId()))) {
				throw new IllegalArgumentException("Invalid ID: " + film.getId());
			}

			dao.deleteFilm(film.getId());
		}

		out.write("XML Film with ID: '" + film.getId() + "' Deleted Successfully.");

		System.out.println("Film with ID: '" + film.getId() + "' Deleted Successfully.\n");
		response.setStatus(HttpServletResponse.SC_OK);

		return film;
	}

	/**
	 * Parses Films from Text to Java using | and # delimiters
	 * 
	 * @param input    the input
	 * @param request  the request
	 * @param response the response
	 * @throws IOException   Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @throws SQLException  the SQL exception
	 */
	public static Film textToJava(String input, HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, IOException, SQLException {

		String[] filmsArray = input.split("\\|");
		FilmDAO dao = new FilmDAO();
		Film film = null;

		PrintWriter out = response.getWriter();

		if (input.contains("#") || input.contains("|")) {
			if (input == null || input.isEmpty()) {
				return null;
			}

			for (String filmObj : filmsArray) {
				String[] filmFields = filmObj.split("#");

				// Validate ID
				if (!Validator.isValidId(filmFields[0])) {
					throw new IllegalArgumentException("Invalid ID: " + filmFields[0]);
				}

				int id = Integer.parseInt(filmFields[0]);

				film = new Film(id);
				film.setId(id);

				dao.deleteFilm(film.getId());

			}

		}

		else {
			if (!Validator.isValidId(input)) {
				throw new IllegalArgumentException("Invalid ID: " + input);
			}

			int id = Integer.parseInt(input);
			film = new Film(id);
			film.setId(id);

			dao.deleteFilm(film.getId());

		}

		out.write("Text Film with ID: '" + film.getId() + "' Deleted Successfully.");

		System.out.println("Film with ID: '" + film.getId() + "' Deleted Successfully.\n");
		response.setStatus(HttpServletResponse.SC_OK);

		out.flush();
		out.close();

		return film;

	}

}