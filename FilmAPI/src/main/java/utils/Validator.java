package utils;

import java.util.regex.Pattern;

/**
 * Java utils class that sanitises and validates data (id, year and string
 * inputs) from the server side.
 *
 * @author Sabiha Patel
 */
public class Validator {

	/**
	 * Checks if input string has a valid ID (i.e., consisting only of numerical
	 * values) for delete.
	 *
	 * @param input the input string
	 * @return true if the input string is a valid ID, false if it is not
	 */
	public static boolean isValidId(String input) { 
		String regex = "[0-9]+";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(input).matches();
	}

	/**
	 * Checks if input string is a valid year and has 4 values.
	 *
	 * @param year the input year
	 * @return true if the input string is a valid year, false if it is not
	 */
	public static boolean isValidYear(int year) {
		String yearStr = String.valueOf(year);
		String regex = "\\d{4}";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(yearStr).matches();
	}

	/**
	 * Sanitizes the input text fields to prevent SQL injection.
	 *
	 * @param input the input text
	 * @return the sanitized text
	 * @throws IllegalArgumentException if the text is not sanitized
	 */
	public static String sanitize(String input) {
		if (!input.matches("[a-zA-Z0-9 !,.()-/:&$?]*")) {
			throw new IllegalArgumentException("Input contains invalid characters: " + input);
		}
		return input;
	}

}
