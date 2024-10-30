import React, { useEffect, useState, useContext } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { Button, Container, Form, FormGroup, Input, Label } from "reactstrap";
import AppNavbar from "./Navbar";
import FormatContext from '../util/FormatContext';
import { parseXmlData, parseTextData } from '../util/Parser';
import '../css/film-add-edit.css';

/**
 *
 * This component provides a form for adding or editing a film's details. It parses data depending on the format value selected.
 *
 * @returns {React.Component} The FilmAddEdit component which includes a form and associated handling logic.
 */
const FilmAddEdit = () => {

  // add form
  const initialFormState = {
    id: "",
    title: "",
    year: "",
    director: "",
    stars: "",
    review: "",
  };

  const [group, setGroup] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();
  const { format } = useContext(FormatContext);


  // setting values on the form for edit in json, xml and text

  useEffect(() => {
    if(id) {
      
      let accept = "application/json";

      if (format === "xml") {
        accept = "application/xml";
      } 
      else if (format === "text") {
        accept = "text/plain";
      }
    
      // setting values in different formats
      fetch(`/FilmAPI/filmapi?id=${id}`, {
        headers: {
          Accept: accept,
        },
        mode: "cors",
      })
      .then(async (response) => {
        const text = await response.text();

        if (format === "text") {
          return parseTextData(text)[0];
        } 

        else if (format === "xml") {
          return parseXmlData(text)[0];
        } 
        
        else {
          const [jsonData] = JSON.parse(text);
          return jsonData;
        }
      })
      .then((film) => {
        console.log('Film ID:', id);
        console.log("Setting Values...\n", film)
        setGroup(film);
      });
    }
  }, [id, format]);
  

  // updates group state
  const handleChange = (event) => {
    const { name, value } = event.target;
    setGroup({ ...group, [name]: value });

  };


  /**
   * Event handler for the form submission. Depending on the format selected, it serialises the data accordingly in JSON, XML or Text
   * It sends a PUT request to update the film data if 'id' is present, 
   * otherwise it sends a POST request to add new film data.
   * 
   * It also performs validation so the correct data is submitted
   * 
   * @async
   * @param {Event} event - The event object
   */
  const handleSubmit = async (event) => {
    let body = Request.body;
    let accept = "application/json";
    event.preventDefault();

    // validation
    if (!group.title || !group.year || !group.director || !group.stars || !group.review) {
      alert('Please fill in all required fields.');
      return;
    }

    if (format === "xml") {
      accept = "application/xml";
      const React = require('react');
      const ReactDomServer = require("react-dom/server")
      const Film = (group) => React.createElement("Film", group);

      body = id ? ReactDomServer.renderToStaticMarkup( // edit
        <Film>
          <id>{group.id}</id>
          <title>{group.title}</title>
          <year>{group.year}</year>
          <director>{group.director}</director>
          <stars>{group.stars}</stars>
          <review>{group.review}</review>
        </Film>)
        : ReactDomServer.renderToStaticMarkup( // add
          <Film>
            <title>{group.title}</title>
            <year>{group.year}</year>
            <director>{group.director}</director>
            <stars>{group.stars}</stars>
            <review>{group.review}</review>
          </Film>);
    }

    else if (format === "text") {
      accept = "text/plain";
      body = id ? `${id}#${group.title}#${group.year}#${group.director}#${group.stars}#${group.review}|` // edit
        : `#${group.title}#${group.year}#${group.director}#${group.stars}#${group.review}|`; // add
    }

    else {
      body = JSON.stringify(id ? group // edit
        : { ...group, id: undefined }); // add
    }

    await fetch("/FilmAPI/filmapi", {
      method: id ? "PUT" : "POST",
      headers: {
        Accept: accept,
        "Content-Type": accept,
      },
      body: body,
    });

    setGroup(initialFormState);
    navigate("/");
  };

  const ptitle = <h2 className="d-flex justify-content-center pt-4 display-6">{id ? "Edit Film" : "Add Film"}</h2>;
  return (
    <div>
      <AppNavbar />
      <Container>
        {ptitle}
        <div className="targetForm">
          <Form onSubmit={handleSubmit} id="targetForm" className="was-validated">


            {id && (
              <FormGroup className="FormGroup">
                <Label for="id">ID</Label>
                <Input
                  type="text"
                  name="id"
                  id="id"
                  value={group.id || ""}
                  onChange={handleChange}
                  autoComplete="id"
                  readOnly
                  style={{ backgroundColor: '#e6e6e6' }}
                  className="required"
                />
              </FormGroup>
            )}

            <FormGroup className="FormGroup">
              <Label for="title">Title</Label>
              <Input
                type="text"
                name="title"
                id="title"
                value={group.title || ""}
                onChange={handleChange}
                autoComplete="title"
                className="required"
              />
            </FormGroup>

            <FormGroup className="FormGroup">
              <Label for="year">Year</Label>
              <Input
                type="text"
                name="year"
                id="year"
                value={group.year || ""}
                onChange={handleChange}
                autoComplete="year"
                className="required"
                pattern="^[0-9]{4}$"
              />
            </FormGroup>

            <FormGroup className="FormGroup">
              <Label for="director">Director</Label>
              <Input
                type="text"
                name="director"
                id="director"
                value={group.director || ""}
                onChange={handleChange}
                autoComplete="director"
                className="required"
              />
            </FormGroup>

            <FormGroup className="FormGroup">
              <Label for="stars">Stars</Label>
              <Input
                type="text"
                name="stars"
                id="stars"
                value={group.stars || ""}
                onChange={handleChange}
                autoComplete="stars"
                className="required"
              />
            </FormGroup>

            <FormGroup className="FormGroup">
              <Label for="review">Review</Label>
              <Input
                type="textarea"
                name="review"
                id="review"
                value={group.review || ""}
                onChange={handleChange}
                autoComplete="review"
                rows="5"
                className="required"
              />
            </FormGroup>

            <FormGroup className="pt-2 ps-5 targetForm">
              <Button color="danger" type="submit" className="px-3 me-3">
                Save
              </Button>{" "}
              <Button color="danger" tag={Link} to="/">
                Cancel
              </Button>
            </FormGroup>


          </Form>
        </div>
      </Container>
    </div>
  );
};

export default FilmAddEdit;