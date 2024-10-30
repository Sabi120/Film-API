package utils;

import models.Film; 
import models.FilmList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Java utils class that parses the response in JSON, XML and Text formats for
 * the GET API operation. It retrieves all films for both table and search results
 * 
 * @see controllers.FilmAPI
 *
 * @author Sabiha Patel
 */
public class FormatUtilsGet {

	/**
	 * Parses Films from java to JSON Uses Gson library to parse from a java film
	 * object to a JSON film object.
	 * 
	 * @param allFilms the list of all films
	 * @param response the response from the server
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void javaToJson(ArrayList<Film> allFilms, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(allFilms);

		out.write(json);
		out.close();

	}

	/**
	 * Parses Films from java to XML Uses JAXB library to parse from a java film
	 * object to a XML film object.
	 * 
	 * @param allFilms the list of all films
	 * @param response the response from the server
	 * @throws JAXBException the JAXB exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void javaToXml(ArrayList<Film> allFilms, HttpServletResponse response)
			throws JAXBException, IOException {
		
		response.setContentType("application/xml");

		FilmList filmList = new FilmList(allFilms);

		JAXBContext jaxbContext = JAXBContext.newInstance(FilmList.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();

		marshaller.marshal(filmList, sw);

		PrintWriter out = response.getWriter();
		out.write(sw.toString());

		System.out.println("Films in XML Format:\n" + sw);

	}

	/**
	 * Parses Films from Text to Java using | and # delimiters
	 * 
	 * @param allFilms the list of all films
	 * @param response the response from the server
	 * @throws JAXBException the JAXB exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void javaToText(ArrayList<Film> allFilms, HttpServletResponse response)
			throws JAXBException, IOException {
		response.setContentType("text/plain");
		String data = "";

		for (Film film : allFilms) {
			String filmString = String.format("%d#%s#%d#%s#%s#%s|", film.getId(), film.getTitle(), film.getYear(),
					film.getDirector(), film.getStars(), film.getReview());
			data += filmString;

		}

		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();

		System.out.println("Films in Text Format:\n" + data);

	}


}