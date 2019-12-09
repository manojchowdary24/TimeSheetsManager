import React from "react";
import { Route } from "react-router-dom";
// import Create from "../sidebar/sidebarcomponents/create/Create";
// import Update from "../sidebar/sidebarcomponents/Update";
// import TotalHours from "../sidebar/sidebarcomponents/TotalHours";
// import Alerts from "../sidebar/sidebarcomponents/Alerts";
// import Copy from "../sidebar/sidebarcomponents/Copy";
// import Admin from "../sidebar/sidebarcomponents/Admin";
// import HomePage from "../homepage/HomePage";
// import PrivateRoute from "./PrivateRoute";
// import Register from "../components/Register/Register";
import Login from "../Login/Login";

const Routes = props => {
  return (
    <div>
      {/* <Route path="/create" component={Create} />
      <Route path="/" exact component={HomePage} />
      <Route path="/update" exact component={Update} />
      <Route path="/totalhours" exact component={TotalHours} />
      <Route path="/alerts" exact component={Alerts} />
      <Route path="/copy" exact component={Copy} />
      <Route path="/register" exact component={Register} /> */}
      <Route path="/login" exact component={Login} />
      {/* <PrivateRoute path="/admin" exact component={Admin} /> */}
    </div>
  );
};

export default Routes;
