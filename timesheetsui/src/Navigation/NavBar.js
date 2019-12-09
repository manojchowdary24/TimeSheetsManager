import React, { useContext } from "react";
import { Navbar, Button, Nav, Dropdown } from "react-bootstrap";
import { AppContext } from "../context/AppContext";

const NavBar = props => {
  const [{ loggedIn }] = useContext(AppContext);
  return (
    <Navbar expand="lg" bg="dark" variant="dark" fixed="top">
      {/* <Navbar.Toggle aria-controls="responsive-navbar-nav" /> */}
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr-auto">
          <Dropdown>
            <Dropdown.Toggle variant="info" id="dropdown-basic">
              Menu
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item href="#/action-1">Action</Dropdown.Item>
              <Dropdown.Divider />
              <Dropdown.Item href="#/action-2">Another action</Dropdown.Item>
              <Dropdown.Divider />
              <Dropdown.Item href="#/action-3">Something else</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Nav>
        {!loggedIn ? (
          <Nav>
            <Nav.Link href="/login">
              <Button variant="outline-info">Log In</Button>
            </Nav.Link>
            <Nav.Link>
              <Button variant="outline-info">Register</Button>
            </Nav.Link>
          </Nav>
        ) : (
          <Nav>
            <Nav.Link>
              <Button variant="outline-info">Log Out</Button>
            </Nav.Link>
          </Nav>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
};

export default NavBar;
