package database;

import java.util.ArrayList; 

import models.Film;

import java.sql.*;


/**
 * Java class that uses SQL queries to perform CRUD and search operations on a
 * database.
 *
 * @author Sabiha Patel
 */
public class FilmDAO {

	/** fetches one film */
	Film oneFilm = null;

	/** connection for SQL database */
	Connection conn = null;

	/** statement for prepared statement */
	Statement stmt = null;

	// Without Cloud
	
	/** The username for the database connection */
	String user = "patelsab";
	/** The password for the database connection */
	String password = "vermiloN2";
	/** The url for the database connection */
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;
	
	// Cloud Login
	
	/** The username for the cloud database connection */	
	 //String user = "admin";
	 /** The password for the cloud database connection */	
	 //String password = "password";
	 /** Name of the table for the cloud database connection */
	 //String table_name = "films";
	 /** Name of the RDS hostname */
	 //String rds_hostname = "filmapi-db.c07hwjrh19bk.eu-west-2.rds.amazonaws.com";
	 /** Name of the RDS port */
	 //String port ="3306";
	 /** Name of the MySQL driver */
	 //String driver = "com.mysql.jdbc.Driver";
	 /** Name of connection URL */
	 //String url = "jdbc:mysql://" + rds_hostname + ":" + port + "/" + table_name + "?user=" + user + "&password=" + password;

	/**
	 * Instantiates a new film DAO.
	 */
	public FilmDAO() {
	}

	/**
	 * Establishes a connection to the MySQL database using JDBC.
	 * 
	 * Attempts to open a connection using login details/parameters to connect to
	 * the database.
	 */
	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			
			/**connection without cloud*/
			Class.forName("com.mysql.jdbc.Driver");

			/**connection with cloud*/
			//conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {
			// connection string for demos database, username demos, password demos
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	/**
	 * Closes the connection.
	 */
	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * Gets the next film.
	 * 
	 * Reads the fields from the row in the result set and creates a new Film object
	 *
	 * @param rs resultset from where the film data is received
	 * @return the next film
	 */
	private Film getNextFilm(ResultSet rs) {
		Film thisFilm = null;
		try {
			thisFilm = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("stars"), rs.getString("review"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return thisFilm;
	}

	/**
	 * Gets all the films
	 * 
	 * Gets films one by one using a ResultSet and adds them to an arraylist
	 *
	 * @return allFilms all the films in the arraylist
	 */
	public ArrayList<Film> getAllFilms() {

		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "SELECT * FROM films";
			ResultSet rs1 = stmt.executeQuery(selectSQL);

			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
				allFilms.add(oneFilm);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;
	}

	/**
	 * Inserts a film into the films table
	 * 
	 * Opens a connection to the database and uses a SQL INSERT prepared statement
	 * to create a new film based on the fields.
	 *
	 * @param film the film being added
	 * @throws SQLException the SQL exception
	 */
	public void insertFilm(Film film) throws SQLException {
		openConnection();

		String query = "INSERT INTO films (title, year, director, stars, review) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, film.getTitle());
			pstmt.setInt(2, film.getYear());
			pstmt.setString(3, film.getDirector());
			pstmt.setString(4, film.getStars());
			pstmt.setString(5, film.getReview());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException("Error: Film Not Inserted" + ex);
		} finally {
			closeConnection();
		}

	}

	/**
	 * Deletes a film.
	 * 
	 * Opens a connection to the database and uses a SQL DELETE prepared statement
	 * to delete a film based on the ID.
	 *
	 * @param id the ID of the film being deleted
	 * @throws SQLException the SQL exception
	 */
	public void deleteFilm(int id) throws SQLException {
		openConnection();

		String query = "DELETE FROM films WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException("Error: Film Not Deleted" + ex);
		} finally {
			closeConnection();
		}

	}

	/**
	 * Updates a film.
	 * 
	 * Opens a connection to the database and uses a SQL UPDATE prepared statement
	 * to update a film based on the ID.
	 *
	 * @param film the film being updated
	 * @throws SQLException the SQL exception
	 */
	public void updateFilm(Film film) throws SQLException {
		openConnection();

		String query = "UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, film.getTitle());
			pstmt.setInt(2, film.getYear());
			pstmt.setString(3, film.getDirector());
			pstmt.setString(4, film.getStars());
			pstmt.setString(5, film.getReview());
			pstmt.setInt(6, film.getId());

			// Execute the prepared statement and update the row in the table
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException("Error: Film Not Updated" + ex);
		} finally {
			closeConnection();
		}

	}

	/**
	 * Searches films based on all fields
	 * 
	 * Opens a connection to the database and uses a SQL LIKE prepared statement to
	 * return all the films from all fields based on the users input
	 * 
	 * @param searchStr the string input from the user
	 * @return an array list of results
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Film> searchAllFilms(String searchStr) throws SQLException {
		openConnection();

		String query = "SELECT id, title, year, director, stars, "
				+ "review FROM films WHERE id LIKE ? OR title LIKE ? "
				+ "OR year LIKE ? OR director LIKE ? OR stars LIKE ? OR review LIKE ?";

		ArrayList<Film> searchResults = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			// ID
			pstmt.setString(1, "%" + searchStr + "%");

			// Title
			pstmt.setString(2, "%" + searchStr + "%");

			// Year
			pstmt.setString(3, "%" + searchStr + "%");

			// Director
			pstmt.setString(4, "%" + searchStr + "%");

			// Stars
			pstmt.setString(5, "%" + searchStr + "%");

			// Review
			pstmt.setString(6, "%" + searchStr + "%");

			// Execute the prepared statement and get the results
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					// Get the values of each column in the current row
					int id = rs.getInt("id");
					String title = rs.getString("title");
					int year = rs.getInt("year");
					String director = rs.getString("director");
					String stars = rs.getString("stars");
					String review = rs.getString("review");

					// Create a string representation of the current row and add it to the ArrayList
					Film film = new Film(id, title, year, director, stars, review);
					searchResults.add(film);
				}
			}
		} catch (SQLException ex) {
			throw new SQLException("Error Searching Films" + ex);
		} finally {
			closeConnection();
		}

		return searchResults;

	}

	/**
	 * Gets the film by ID.
	 * 
	 * Opens a sql connection and performs a WHERE SQL query to return the film that
	 * matches the ID. Used for the search functionality
	 *
	 * @param id the films id
	 * @return an array list of results
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Film> getFilmByID(int id) throws SQLException {
		openConnection();

		ArrayList<Film> allFilms = new ArrayList<>();

		try {
			String query = "SELECT * FROM films WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int idRS = rs.getInt("id");
				String title = rs.getString("title");
				int year = rs.getInt("year");
				String director = rs.getString("director");
				String stars = rs.getString("stars");
				String review = rs.getString("review");

				allFilms.add(new Film(idRS, title, year, director, stars, review));

			}

			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;
	}

	/**
	 * Gets the film by title.
	 * 
	 * Opens a sql connection and performs a LIKE SQL query to return the films that
	 * matches the title. Used for the search functionality
	 *
	 * @param title the films title
	 * @return an array list of results
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Film> getFilmByTitle(String title) throws SQLException {
		ArrayList<Film> allFilms = new ArrayList<>();
		openConnection();

		try {
			String query = "SELECT * FROM films WHERE title LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + title + "%");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String titleRS = rs.getString("title");
				int year = rs.getInt("year");
				String director = rs.getString("director");
				String stars = rs.getString("stars");
				String review = rs.getString("review");

				allFilms.add(new Film(id, titleRS, year, director, stars, review));

			}

			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;
	}

	/**
	 * Gets the film by year.
	 * 
	 * Opens a sql connection and performs a LIKE SQL query to return the films that
	 * matches the year. Used for the search functionality
	 *
	 * @param year the year title
	 * @return an array list of results
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Film> getFilmByYear(int year) throws SQLException {
		ArrayList<Film> allFilms = new ArrayList<>();
		openConnection();

		try {
			String query = "SELECT * FROM films WHERE year = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, year);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				int yearRS = rs.getInt("year");
				String director = rs.getString("director");
				String stars = rs.getString("stars");
				String review = rs.getString("review");

				allFilms.add(new Film(id, title, yearRS, director, stars, review));

			}

			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;
	}

}