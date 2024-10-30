import React, { useEffect, useState, useContext } from 'react';
import { Button, Container, Form, FormGroup, Input } from 'reactstrap';
import AppNavbar from './Navbar';
import { Link } from 'react-router-dom';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import DeleteIcon from '@mui/icons-material/Delete';
import CircularProgress from '@mui/material/CircularProgress';
import FormatContext from '../util/FormatContext';
import { parseXmlData, parseTextData } from '../util/Parser';
import '../css/button-styles.css';


/**
 * The FilmTable component contains the functionality of the film table
 *
 * @component
 * @returns {ReactElement} JSX that renders the Navigation Bar, a table listing the films, and a form for searching films.
 *
 * The FilmTable component performs the following:
 * 
 * Search:
 * handleSearch - to update the search input.
 * handleSearchSubmit -  validates search input, sends a search request to the API based on the format and updates the filtered films state.
 * clear - to clear the search input and reset the filtered films state.
 * 
 * Get Films:
 * useEffect -  for fetching films from the API in different formats
 * fetchFilms -  (inside the `useEffect` hook) to fetch films from the API in different formats, update the films and filtered films state.
 * 
 * Film Options:
 * remove -  to send a delete request to the API in different formats and updates the states of the film table.
 * edit - redirects to the edit page to send a put request to the API to update a film
 */
const FilmTable = () => {

  const { format } = useContext(FormatContext);

  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(false);

  // search variables
  const [searchInput, setSearchInput] = useState('');
  const [filteredFilms, setFilteredFilms] = useState([]);
  const [searchType, setSearchType] = useState("title");

  // updates the search input
  const handleSearch = (e) => {
    setSearchInput(e.target.value);
  };


  // Validates search input, sends a search request to the API based on the format and updates the filtered films state
  const handleSearchSubmit = (e) => {
    e.preventDefault();

    let searchText;

    // Validation
    // Check if year or ID are integers or is blank/has spaces
    if (searchType === 'id' || searchType === 'year') {
      searchText = parseInt(searchInput);
      if (isNaN(searchText)) {
        console.log('Search input is not valid');
        setFilteredFilms([]);
        return;
      }
    }

    // Check if text input is blank/has spaces
    else {
      searchText = searchInput.trim().toLowerCase();
      if (searchText === "") {
        setFilteredFilms([]);
        return;
      }
    }

    let accept = "application/json";

    if (format === "xml") {
      accept = "application/xml";
    } 

    else if (format === "text") {
      accept = "text/plain";
    }

    fetch(`FilmAPI/filmapi?${searchType}=${searchText}&format=${format}`, {
      headers: {
        Accept: accept,
      },
      mode: "cors",
    })
      .then(async (response) => {
        if (!response.ok) {
          throw new Error(`Request failed with status ${response.status}`);
        }

        const text = await response.text();

        // Checking valid json - check if the response body is empty or contains only spaces
        if (text.trim().length === 0) {
          return [];
        }


        if (format === "json") {
          return JSON.parse(text);
        } 

        else if (format === "xml") {
          return parseXmlData(text);
        } 

        else {
          return parseTextData(text);
        }
      })
      .then((data) => {
        setFilteredFilms(data);
      })
      .catch((error) => {
        console.error('Error:', error.message);
        setFilteredFilms([]);
      });
  };


  // clear search 
  const clear = () => {
    setSearchInput('');
    setFilteredFilms(groups);
  };


  // Fetch films and parse for the table for json, xml and text
  useEffect(() => {
    console.log("Fetching Films in", format);

    const fetchFilms = async (format) => {
      setLoading(true);

      // set accept
      let accept = "application/json";

      if (format === "xml") {
        accept = "application/xml";
      } 

      else if (format === "text") {
        accept = "text/plain";
      }

      // parse in different formats
      fetch("FilmAPI/filmapi", {
        headers: {
          Accept: accept,
        },
        mode: "cors",
      })
        .then(async (response) => {

          const text = await response.text();

          if (format === "text") {
            return parseTextData(text);
          } 

          else if (format === "xml") {
            return parseXmlData(text);
          } 

          else {
            return JSON.parse(text);
          }
        })
        .then((data) => {
          setGroups(data);
          setFilteredFilms(data);
          setLoading(false);
        })
        .catch((error) => {
          console.error('Error:', error.message);
          setFilteredFilms([]);
          setLoading(false);
        });
    };

    fetchFilms(format);
  }, [format]);


  // delete
  const remove = async (id, format) => {
    let body = Request.body;
    let accept = "application/json";

    console.log("Deleted in ", FormatContext.format);
    format = FormatContext.format

    if (format === "xml") {
      console.log("format === xml");
      accept = "application/xml";
      body = `<Film><id>${id}</id></Film>`;
    }

    else if (format === "text") {
      console.log("format === text");
      accept = "text/plain";
      body = `${id}#`;
    }

    else {
      console.log("format === json");
      accept = "application/json";
      body = JSON.stringify({ 'id': id });
    }

    
    await fetch(`/FilmAPI/filmapi`, {
      method: 'DELETE',
      headers: {
        'Accept': accept,
        'Content-Type': accept
      },
      body: body
    }).then(() => {
      let updatedGroups = [...groups].filter(i => i.id !== id);
      setGroups(updatedGroups);
      setFilteredFilms(updatedGroups);
    });
  }


  if (loading) {

    return (
      <div className='d-flex justify-content-center d-flex align-items-center'>
        <CircularProgress color="error" />
      </div>
    );
  }

  return (
    <div>
      <AppNavbar />
      <Container fluid className="pt-3">

        <TableContainer component={Paper} className='shadow'>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead
              sx={{
                "& th": {
                  color: "white",
                  backgroundColor: "#212529"
                }
              }}>
              <TableRow>
                <TableCell><b>ID</b></TableCell>
                <TableCell align="left"><b>Title</b></TableCell>
                <TableCell align="left"><b>Year</b></TableCell>
                <TableCell align="left"><b>Director</b></TableCell>
                <TableCell align="left"><b>Stars</b></TableCell>
                <TableCell align="left"><b>Review</b></TableCell>
                <TableCell style={{ width: "400px" }} align="right" className="justify-content-right align-items-center pb-0 pe-0">

                  <Form onSubmit={handleSearchSubmit} className="row">

                    <FormGroup className="row d-flex justify-content-end align-items-center pe-0">
                      <div className="col-3">
                        <Input
                          type="select"
                          value={searchType}
                          onChange={(e) => setSearchType(e.target.value)}
                          className='pe-0 ps-2'>
                          <option value="title">Title</option>
                          <option value="year">Year</option>
                          <option value="id">ID</option>
                          <option value="all">All</option>
                        </Input>
                      </div>

                      <div className="col-4 ms-0 ps-0">
                        <Input
                          type="text"
                          name="searchTerm"
                          id="searchTerm"
                          value={searchInput}
                          onChange={handleSearch} placeholder="Search..."
                        />
                      </div>

                      <Button className="col-auto me-2 pb-6 pb-2 align-items-center ps-3 pe-3" type="submit" color="danger">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-search-heart-fill" viewBox="0 0 16 16">
                          <path d="M6.5 13a6.474 6.474 0 0 0 3.845-1.258h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.008 1.008 0 0 0-.115-.1A6.471 6.471 0 0 0 13 6.5 6.502 6.502 0 0 0 6.5 0a6.5 6.5 0 1 0 0 13Zm0-8.518c1.664-1.673 5.825 1.254 0 5.018-5.825-3.764-1.664-6.69 0-5.018Z" />
                        </svg>
                      </Button>

                      <Button className="col-auto me-2 pb-6 pb-2 align-items-center ps-3 pe-3" onClick={clear} color="danger">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-x-circle-fill" viewBox="0 0 16 16">
                          <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z" />
                        </svg>
                      </Button>

                    </FormGroup>
                  </Form>

                </TableCell>

                <TableCell style={{ backgroundColor: "#212529", width: "2px" }} className='ps-0 pe-0'></TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {filteredFilms.map((row) => (
                <TableRow
                  key={row.id}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {row.id}
                  </TableCell>
                  <TableCell align="left">{row.title}</TableCell>
                  <TableCell align="left">{row.year}</TableCell>
                  <TableCell align="left">{row.director}</TableCell>
                  <TableCell align="left">{row.stars}</TableCell>
                  <TableCell align="left" colSpan={2}>{row.review}</TableCell>

                  <TableCell align="right">
                    <Button className="optionsShadow" title="Update" size="sm" color="white" tag={Link} to={"/films/" + row.id}>
                      <h5>&#9997;</h5>
                    </Button>


                    <Button className="optionsShadow" title="Delete" size="sm" color="white" starticon={<DeleteIcon />} onClick={() => remove(row.id)}>
                      <h5>&#10060;</h5>
                    </Button>

                  </TableCell>


                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Container>
      { }
    </div>
  );
};

export default FilmTable;