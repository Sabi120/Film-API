package utils;

import database.FilmDAO;
import models.Film;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringReader;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.xml.bind.JAXBException;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

/**
 * Java utils class that parses the response in JSON, XML and Text formats for
 * the POST/Add API operation to add films based on the details entered. Also
 * performs data sanitisation on inputs
 * 
 * @see controllers.FilmAPI
 *
 * @author Sabiha Patel
 */
public class FormatUtilsPost {

	/**
	 * Parses Films from JSON to Java Uses Gson library to parse from a JSON film
	 * object to a Java film object.
	 * 
	 * @param input    the input
	 * @param request  the request
	 * @param response the response
	 * @throws IOException   Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @throws SQLException  the SQL exception
	 */
	public static Film jsonToJava(String input, HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, SQLException, IOException {
		FilmDAO dao = new FilmDAO();
		Film film = null;
		PrintWriter out = response.getWriter();

		try {

			Gson gson = new Gson();

			if (input != null && !input.isEmpty()) {
				film = gson.fromJson(input, Film.class);

				// Validate
				if (!Validator.isValidYear(film.getYear())) {
					throw new IllegalArgumentException("Invalid year: " + film.getYear());
				}

				// Ensure all fields are sanitized
				Validator.sanitize(film.getTitle());
				Validator.sanitize(film.getDirector());
				Validator.sanitize(film.getStars());
				Validator.sanitize(film.getReview());

				dao.insertFilm(film);
			}

		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write(e.getMessage());
			return null;
		}

		out.write("JSON '" + film.getTitle() + "' Added Successfully: ");
		out.write(film.toString());

		System.out.println("JSON Film '" + film.getTitle() + "' Added Successfully: \n" + film);
		response.setStatus(HttpServletResponse.SC_OK);

		return film;
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
		FilmDAO dao = new FilmDAO();
		Film film = null;
		PrintWriter out = response.getWriter();

		try {
			if (input != null && !input.isEmpty()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				film = (Film) unmarshaller.unmarshal(new StringReader(input));

				// validate year
				if (!Validator.isValidYear(film.getYear())) {
					throw new IllegalArgumentException("Invalid year: " + film.getYear());
				}

				// Ensure all fields are sanitized
				Validator.sanitize(film.getTitle());
				Validator.sanitize(film.getDirector());
				Validator.sanitize(film.getStars());
				Validator.sanitize(film.getReview());

				dao.insertFilm(film);
			}
		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write(e.getMessage());
			return null;
		}

		out.write("XML '" + film.getTitle() + "' Added Successfully: ");
		out.write(film.toString());

		System.out.println("XML Film '" + film.getTitle() + "' Added Successfully: \n" + film);
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
		FilmDAO dao = new FilmDAO();
		Film film = null;

		PrintWriter out = response.getWriter();

		try {
			if (input.contains("#") || input.contains("|")) {
				if (input.startsWith("#")) {
					input = input.substring(1);
				}

				String[] filmsArray = input.split("\\|");

				for (String filmStr : filmsArray) {
					String[] filmFields = filmStr.split("#");

					String title = filmFields[0];
					int year = Integer.parseInt(filmFields[1]);
					String director = filmFields[2];
					String stars = filmFields[3];
					String review = filmFields[4];

					// Ensure all fields are sanitized
					Validator.sanitize(title);
					Validator.sanitize(director);
					Validator.sanitize(stars);
					Validator.sanitize(review);

					film = new Film(title, year, director, stars, review);

					dao.insertFilm(film);

					out.write("'" + film.getTitle() + "' Added Successfully: ");
					out.println(film.toString());
				}

			} else {
				out.write("Incorrect Format");
				System.out.println("Incorrect Format");
			}
		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write(e.getMessage());
			return null;
		}

		out.flush();
		out.close();

		System.out.println("Text '" + film.getTitle() + "' Added Successfully: \n" + film);
		response.setStatus(HttpServletResponse.SC_OK);

		return film;
	}

}