package utils;

import models.Film;
import database.FilmDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import jakarta.xml.bind.JAXBException;
import java.sql.SQLException;

import java.io.PrintWriter;
import java.io.StringReader;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

/**
 * Java utils class that parses the response in JSON, XML and Text formats for
 * the PUT/Update API operation to update a film based on the ID. Also performs
 * data sanitisation on inputs
 * 
 * @see controllers.FilmAPI
 *
 * @author Sabiha Patel
 */
public class FormatUtilsPut {

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
			throws IOException, JAXBException, SQLException {
		FilmDAO dao = new FilmDAO();
		Film film = null;

		PrintWriter out = response.getWriter();

		Gson gson = new Gson();

		if (input != null && !input.isEmpty()) {
			film = gson.fromJson(input, Film.class);

			// Validation
			if (!Validator.isValidYear(film.getYear())) {
				throw new IllegalArgumentException("Invalid year: " + film.getYear());
			}

			// Ensure all fields are sanitized
			film.setTitle(Validator.sanitize(film.getTitle()));
			film.setDirector(Validator.sanitize(film.getDirector()));
			film.setStars(Validator.sanitize(film.getStars()));
			film.setReview(Validator.sanitize(film.getReview()));

			dao.updateFilm(film);
		}

		out.write("JSON '" + film.getTitle() + "' Updated Successfully: ");
		out.println(film.toString());

		System.out.println("JSON Film '" + film.getTitle() + "' Updated Successfully:\n" + film + "\n");
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

		if (input != null && !input.isEmpty() | !input.startsWith("<")) {
			JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			film = (Film) unmarshaller.unmarshal(new StringReader(input));

			// Validate year
			if (!Validator.isValidYear(film.getYear())) {
				throw new IllegalArgumentException("Invalid year: " + film.getYear());
			}

			// Ensure all fields are sanitized
			film.setTitle(Validator.sanitize(film.getTitle()));
			film.setDirector(Validator.sanitize(film.getDirector()));
			film.setStars(Validator.sanitize(film.getStars()));
			film.setReview(Validator.sanitize(film.getReview()));

			dao.updateFilm(film);

		}

		out.write("XML '" + film.getTitle() + "' Updated Successfully: ");
		out.println(film.toString());

		System.out.println("XML Film '" + film.getTitle() + "' Updated Successfully:\n" + film + "\n");
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
		Film f = null;

		PrintWriter out = response.getWriter();

		if (input.contains("#") || input.contains("|")) {
			String[] filmsArray = input.split("\\|");

			for (String film : filmsArray) {
				String[] filmFields = film.split("#");

				// Ensure all fields are sanitized
				int id = Integer.parseInt(filmFields[0]);
				String title = Validator.sanitize(filmFields[1]);
				int year = Integer.parseInt(filmFields[2]);
				String director = Validator.sanitize(filmFields[3]);
				String stars = Validator.sanitize(filmFields[4]);
				String review = Validator.sanitize(filmFields[5]);

				// Validate year
				if (!Validator.isValidYear(year)) {
					throw new IllegalArgumentException("Invalid year: " + year);
				}

				f = new Film(id, title, year, director, stars, review);

				dao.updateFilm(f);

			}
		}

		else {
			out.write("Incorrect Format");
			System.out.println("Incorrect Format");
		}

		out.write("Text '" + f.getTitle() + "' Updated Successfully: ");
		out.println(f.toString());

		out.flush();
		out.close();

		System.out.println("Text Film '" + f.getTitle() + "' Updated Successfully:\n" + f + "\n");
		response.setStatus(HttpServletResponse.SC_OK);

		return f;

	}

}