/**
 * This function is used to parse XML data and return an array of film objects.
 *
 * @param {string} text - XML data to parse
 * @returns {Array} An array of film objects parsed from the XML data
 */
export const parseXmlData = (text) => {
    const parser = new DOMParser();
    const xmlData = parser.parseFromString(text, "text/xml");
  
    const films = xmlData.getElementsByTagName("Film");
    const data = Array.from(films).map((film) => {
      return {
        id: parseInt(film.getElementsByTagName("id")[0].textContent),
        title: film.getElementsByTagName("title")[0].textContent,
        year: parseInt(film.getElementsByTagName("year")[0].textContent),
        director: film.getElementsByTagName("director")[0].textContent,
        stars: film.getElementsByTagName("stars")[0].textContent,
        review: film.getElementsByTagName("review")[0].textContent,
      };
    });
    return data;
  };
  
/**
 * This function is used to parse text data and return an array of film objects.
 *
 * @param {string} text - text data to parse
 * @returns {Array} An array of film objects parsed from the text data
 */
export const parseTextData = (text) => {
    const films = text.trim().split("|").filter(Boolean);
    const data = films.map((film) => {
        const [id, title, year, director, stars, review] = film.split("#");
        return {
        id: parseInt(id),
        title,
        year: parseInt(year),
        director,
        stars,
        review,
        };
    });
    return data;
}; 
  