import React, { useContext, useEffect } from "react";
import "./App.css";
import axios from "axios";

import { BrowserRouter as Router } from "react-router-dom";
import Routes from "./routes/Routes";

import "bootstrap/dist/css/bootstrap.min.css";

import { AppContext, actions } from "./context/AppContext";

import NavigationBar from "./Navigation/NavBar";

const BASEURL = "http://localhost:8080";
const GET_USERS = "/api/users";

const App = props => {
  const [{ loggedIn }, dispatchToAppState] = useContext(AppContext);

  useEffect(() => {
    axios.get(BASEURL + GET_USERS).then(resp => {
      dispatchToAppState({
        type: actions.LOG_USER_IN,
        payload: resp.data
      });
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [loggedIn]);
  return (
    <Router>
      <Routes />
      <NavigationBar />
    </Router>
  );
};

export default App;
