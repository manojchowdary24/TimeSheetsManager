import React from "react";
import { useQuery } from "@apollo/react-hooks";
import Login from "../Login";
import IsAuthenticated from "../../constants/graphql/queries/isAuthenticated.graphql";

const App: React.FC = () => {
  const { data: { isAuthenicated = false } = {}, client } = useQuery(
    IsAuthenticated,
  );

  return isAuthenicated ? (
    <div>
      Hello <button onClick={async () => client.resetStore()}>Sign out</button>
    </div>
  ) : (
    <Login />
  );
};

export default App;
