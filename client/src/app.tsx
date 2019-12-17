import React, { useEffect, useState } from "react";
import Application from "./containers/App";
import { ApolloProvider } from "@apollo/react-hooks";
import apolloClient from "./constants/graphql/client";
import { persistCache } from "apollo-cache-persist";
import CssBaseline from "@material-ui/core/CssBaseline";

const App: React.FC = () => {
  const [client, setClient] = useState(null);
  const [err, setError] = useState(null);

  useEffect(() => {
    const { cache } = apolloClient;
    const { localStorage } = window;
    persistCache({ cache, storage: localStorage })
      .then(() => {
        setClient(apolloClient);
      })
      .catch((err) => setError(err));
  }, []);

  if (err) {
    return <div>An error occurred</div>;
  }

  if (!client) {
    return <div>Loading...</div>;
  }
  return (
    <ApolloProvider client={client}>
      <CssBaseline />
      <Application />
    </ApolloProvider>
  );
};

export default App;
