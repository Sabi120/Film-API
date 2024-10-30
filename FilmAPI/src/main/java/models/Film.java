package models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * The model class for the Film object.
 * 
 * @author Sabiha Patel
 */
@XmlRootElement(name = "Film")
@XmlAccessorType(XmlAccessType.FIELD)
public class Film {

	/**
	 * Instantiates a new film for editing a film
	 *
	 * @param id       the id
	 * @param title    the title
	 * @param year     the year
	 * @param director the director
	 * @param stars    the stars
	 * @param review   the review
	 */
	public Film(int id, String title, int year, String director, String stars, String review) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
	}

	/**
	 * Instantiates a new film for adding a Film
	 *
	 * @param title    the title
	 * @param year     the year
	 * @param director the director
	 * @param stars    the stars
	 * @param review   the review
	 */
	public Film(String title, int year, String director, String stars, String review) {
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
	}

	/**
	 * Instantiates a new film for deleting a film
	 *
	 * @param id the id
	 */
	public Film(int id) {
		this.id = id;

	}


	/**
	 * Instantiates a new film.
	 */
	public Film() {
		super();
	}

	/** The id. */
	int id;

	/** The title. */
	String title;

	/** The year. */
	int year;

	/** The director. */
	String director;

	/** The stars. */
	String stars;

	/** The review. */
	String review;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Gets the director.
	 *
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Sets the director.
	 *
	 * @param director the director
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * Gets the stars.
	 *
	 * @return the stars
	 */
	public String getStars() {
		return stars;
	}

	/**
	 * Sets the stars.
	 *
	 * @param stars the stars
	 */
	public void setStars(String stars) {
		this.stars = stars;
	}

	/**
	 * Gets the review.
	 *
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Sets the review.
	 *
	 * @param review the review
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Returns film object as a string object
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", year=" + year + ", director=" + director + ", stars=" + stars
				+ ", review=" + review + "]";
	}

}