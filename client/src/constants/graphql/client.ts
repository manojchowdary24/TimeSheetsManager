import { ApolloClient } from "apollo-client";
import { InMemoryCache } from "apollo-cache-inmemory";
import { RestLink } from "apollo-link-rest";

const restLink = new RestLink({ uri: "http://localhost:8080" });

const client = new ApolloClient({
  link: restLink,
  cache: new InMemoryCache(),
});

export default client;
