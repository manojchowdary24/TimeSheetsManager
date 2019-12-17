import React from "react";
import { useMutation } from "@apollo/react-hooks";
import LoginMutation from "../../constants/graphql/mutations/login.graphql";
import LoginForm from "../../components/Login";

const Login = () => {
  const [login] = useMutation(LoginMutation, {
    ignoreResults: true,
    update: (
      cache,
      {
        data: {
          login: { accessToken = "" },
        },
      },
    ) => {
      cache.writeData({
        data: { isAuthenicated: !!accessToken },
      });
    },
  });

  const onSubmit = (data: any) => login({ variables: { input: { ...data } } });
  return <LoginForm onSubmit={onSubmit} />;
};

export default Login;
