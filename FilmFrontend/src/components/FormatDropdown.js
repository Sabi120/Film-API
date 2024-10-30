import React, { useState, useContext} from 'react';
import '../css/style.css';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import Films from './FilmTable';
import EditFilm from './FilmAddEdit';
import AddEdit from './FilmAddEdit';
import { FormGroup, Input } from 'reactstrap';
import { useNavigate } from "react-router-dom";
import FormatContext from '../util/FormatContext.js';

console.log("FormatContext",FormatContext.format);



/**
* Sets up the format context for the data format (defaults to 'json'). 
* It also sets up routing for the application pages to use the FormatContext
*
* @returns {React.Component} The FormatRoutes component which includes routing and the context provider for the application.
*/
const FormatRoutes = () => {

  const [format, setFormat] = useState('json');

  return (
    <FormatContext.Provider value={{ format, setFormat }}>
      <Router>
      <Routes>
        <Route exact path="/" element={<Films/>}/>
        <Route path='/films/:id' element={<EditFilm/>}/>
        <Route path='/films/add' element={<AddEdit/>}/>
      </Routes>
    </Router>
    </FormatContext.Provider>
  )
}



/**
 * The Format component of the application.
 *
 * This component provides a dropdown that allows the user to choose the format of data - JSON, XML or Text.
 * It also handles fetching data/GET request in the selected format from the '/FilmAPI/filmapi' endpoint whenever
 * the selected format is changed and the current page is '/FilmAPI/filmapi'.
 *
 * @returns {React.Component} The Format component which includes a selection dropdown and data fetching logic.
 */
  const Format = () => {

    const { format, setFormat } = useContext(FormatContext);
    const navigate = useNavigate();
    const location = useLocation();

    // sets the format of the dropdown value and does the GET request if the endpoint is '/FilmAPI/filmapi'
    const handleFormat = async (event) => {
      console.log("Dropdown: ", event.target.value);
      FormatContext.format = event.target.value;
      
      setFormat(event.target.value);

      if (location.pathname === '/FilmAPI/filmapi') {
        await handleSubmit(event, event.target.value);
      }

    };



   /**
   * Performs a GET request in different formats based on the selected value of the dropdown.
   * After the GET request is completed, it navigates to the root route.
   * 
   * @async
   * @param {Event} event - The submit event triggered by the form.
   * @param {string} selectedFormat - The selected format for the GET request - JSON, XML or Text
   * 
   * @returns {Promise} - to send GET request to the API
   * 
   */
    const handleSubmit = async (event, selectedFormat) => {
      let accept = "application/json";
      event.preventDefault();  
  
      if (selectedFormat === "xml")
      {
        accept = "application/xml";
      }
      else if (selectedFormat === "text")
      {
        accept = "text/plain";
      }
      else 
      {
        accept = "application/json";
      }


    await fetch(`/FilmAPI/filmapi`,{
        method: "GET",
        headers: {
          Accept: accept,
          "Content-Type": accept,
        },
        
      })
    

    navigate("/");

  };
 
  return (
    <div className = "ms-4 me-3 row pt-4"> 
      <FormGroup>
        <Input 
          type="select" 
          name="formatType" 
          value={format} 
          onChange={handleFormat}
          className='mb-2'
        >
            <option value="json">JSON</option>
            <option value="xml">XML</option>
            <option value="text">Text</option>
        </Input>
      </FormGroup>
    </div>
    
  );
};

export { FormatRoutes,Format };
