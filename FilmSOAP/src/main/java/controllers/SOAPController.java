package controllers;

import java.sql.SQLException; 
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.google.gson.Gson;

import models.Film;
import database.FilmDAO;

/**
 * The Class SOAPController used for the FilmSOAPClient.
 * 
 * Each of the methods should perform CRUD operations on the database
 * 
 * 
 * @author Sabiha Patel
 */
@WebService
public class SOAPController {

	/** New DAO object to perform SQL CRUD operations on */
	FilmDAO dao = new FilmDAO();

	/**
	 * Gets a list of films.
	 *
	 * @return all lists of films in JSON
	 */
	@WebMethod
	public String getAllFilms() {

		ArrayList<Film> filmsList = dao.getAllFilms();

		Gson gson = new Gson();

		return gson.toJson(filmsList);
	}

	/**
	 * Creates a film object based on the parameters entered
	 *
	 * @param title    the title
	 * @param year     the year
	 * @param director the director
	 * @param stars    the stars
	 * @param review   the review
	 * @return film object
	 */
	@WebMethod
	public Film insertFilm(
			@WebParam(name = "title") String title, 
			@WebParam(name = "year") int year,
			@WebParam(name = "director") String director, 
			@WebParam(name = "stars") String stars,
			@WebParam(name = "review") String review) {
		Film film = new Film(title, year, director, stars, review);

		try {
			dao.insertFilm(film);
			System.out.println("Film Added: " + "\n" + title + "\n" + year + "\n" + director + "\n" + stars + "\n"
					+ review + "\n");
			return film;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Deletes a film based on the ID entered
	 *
	 * @param id the id
	 * @return film object
	 */
	@WebMethod
	public Film deleteFilm(@WebParam(name = "id") int id) {
		Film film = new Film();

		film.setId(id);

		try {

			dao.deleteFilm(id);
			return film;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates a film based on the parameters entered
	 *
	 * @param id       the id
	 * @param title    the title
	 * @param year     the year
	 * @param director the director
	 * @param stars    the stars
	 * @param review   the review
	 * @return the film
	 */
	@WebMethod
	public Film updateFilm(
			@WebParam(name = "id") int id, 
			@WebParam(name = "title") String title,
			@WebParam(name = "year") int year, 
			@WebParam(name = "director") String director,
			@WebParam(name = "stars") String stars, 
			@WebParam(name = "review") String review) {
		Film film = new Film(id, title, year, director, stars, review);

		try {
			dao.updateFilm(film);
			System.out.println("Film Updated: " + "\n" + id + "\n" + title + "\n" + year + "\n" + director + "\n"
					+ stars + "\n" + review + "\n");
			return film;
		}

		catch (SQLException e)

		{
			e.printStackTrace();
			return null;
		}
	}
}
