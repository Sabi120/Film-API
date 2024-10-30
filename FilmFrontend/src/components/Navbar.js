import React, { useState } from 'react';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, Button} from 'reactstrap';
import { Link } from 'react-router-dom';
import {Format} from './FormatDropdown';
import 'animate.css';

import '../css/button-styles.css';

/**
 * AppNavbar renders the navigation bar for the application.
 * 
 * It includes the logo, title and 'Add Film' button, and a dropdown for selecting the format.
 * 
 * @returns {JSX.Element} The JSX Code for the navigation bar component.
 */
const AppNavbar = () => {

  const [isOpen, setIsOpen] = useState(false);

  return (
    <Navbar className = "navbar-expand-lg navbar-light bg-dark text-white fixed-top shadow pb-0 pt-0">
      <NavbarBrand tag={Link} to="/" className = "ps-2">
      <img
        className='animate__animated animate__bounce home' 
        alt = "logo"
        src = {require('../imgs/favicon/android-chrome-55x55.png')}
        title = "Home"
        style={{
          height: 50,
          width: 50
        }}
      />
      </NavbarBrand>

      <h2 className ="ps-2 pt-2 display-6 fs-3">Film Web Application</h2>

      <div className="float-end ps-5">
          <Button className = "ms-5" color="danger" tag={Link} to="/films/add">Add Film</Button>
        </div>

      <NavbarToggler onClick={() => { setIsOpen(!isOpen) }}/>
      <Collapse isOpen={isOpen} navbar className = "justify-content-end">

        <Nav className="ml-auto" navbar>
            <NavItem>
              <Format handleFormat={() => {}} />
            </NavItem>

        </Nav>
       
      </Collapse>
    </Navbar>
  );
};

export default AppNavbar;