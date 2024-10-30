package models;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Used to generate a list of films and film object based on XML schema to
 * perform CRUD functions
 * 
 * @author Sabiha Patel
 */
@XmlRootElement(name = "Films")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilmList {

	/** XML Element */
	@XmlElement(name = "Film")
	private ArrayList<Film> filmList;

	/**
	 * Instantiates a new list of films
	 */
	public FilmList() {
		filmList = new ArrayList<>();
	}

	/**
	 * Instantiates a new list of films
	 *
	 * @param filmsList the films list
	 */
	public FilmList(ArrayList<Film> filmsList) {
		this.filmList = filmsList;
	}

	/**
	 * Uses Film as the name of the XML element of each Film object in the arraylist
	 *
	 * @return the film list
	 */
	public ArrayList<Film> getFilmList() {
		return filmList;
	}

	/**
	 * Sets the film list.
	 *
	 * @param filmsList the films list
	 */
	public void setFilmsList(ArrayList<Film> filmsList) {
		this.filmList = filmsList;
	}

}