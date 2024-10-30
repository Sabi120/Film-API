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

	/** The username for the database connection */
	String user = "patelsab";

	/** The password for the database connection */
	String password = "vermiloN2";

	/** The url for the database connection */
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

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

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
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
	 * @param f the film being added
	 * @throws SQLException the SQL exception
	 */
	public void insertFilm(Film f) throws SQLException {
		openConnection();

		// String query1 = "SELECT LAST_INSERT_ID();";
		// String query = "INSERT INTO films (id, title, year, director, stars, review)
		// VALUES (?, ?, ?, ?, ?, ?)";
		String query = "INSERT INTO films (title, year, director, stars, review) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			// pstmt.setInt(1, f.getId()); // needed?
			pstmt.setString(1, f.getTitle());
			pstmt.setInt(2, f.getYear());
			pstmt.setString(3, f.getDirector());
			pstmt.setString(4, f.getStars());
			pstmt.setString(5, f.getReview());
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
	 * @param f the film being updated
	 * @throws SQLException the SQL exception
	 */
	public void updateFilm(Film f) throws SQLException {
		openConnection();

		String query = "UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, f.getTitle());
			pstmt.setInt(2, f.getYear());
			pstmt.setString(3, f.getDirector());
			pstmt.setString(4, f.getStars());
			pstmt.setString(5, f.getReview());
			pstmt.setInt(6, f.getId());

			// Execute the prepared statement and update the row in the table
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException("Error: Film Not Updated" + ex);
		} finally {
			closeConnection();
		}

	}

}
