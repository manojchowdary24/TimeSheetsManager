import React from "react";
import Login from "./containers/Login";
import { ApolloProvider } from "@apollo/react-hooks";
import client from "./constants/graphql/client";

const App: React.FC = () => {
  return (
    <ApolloProvider client={client}>
      <Login />
    </ApolloProvider>
  );
};

export default App;
