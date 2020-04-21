import React from "react";
import { useQuery, useLazyQuery } from "@apollo/react-hooks";
import Login from "../Login";
import IsAuthenticated from "../../constants/graphql/queries/isAuthenticated.graphql";
import User from "../../constants/graphql/queries/users.graphql";

const App: React.FC = () => {
  const { data: { isAuthenicated = false } = {}, client } = useQuery(
    IsAuthenticated,
  );

  const [getUser, { data: { users = [] } = {} }] = useLazyQuery(User);

  return isAuthenicated ? (
    <div>
      Hello <button onClick={async () => client.resetStore()}>Sign out</button>
      <button onClick={async () => getUser()}>Get User</button>
      {users &&
        users.map(({ username }) => {
          return <p key={username}>{username}</p>;
        })}
      <ul></ul>
    </div>
  ) : (
    <Login />
  );
};

export default App;
