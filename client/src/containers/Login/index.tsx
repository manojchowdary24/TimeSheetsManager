import React from "react";
import { useMutation } from "@apollo/react-hooks";
import LoginMutation from "../../constants/graphql/mutations/login.graphql";
import Form from "../../components/Form";
import {
  FormInput,
  FormInputType,
} from "../../components/Form/utils/validationSchema";

const inputs: FormInput[] = [
  {
    id: "username",
    type: FormInputType.text,
    label: "Username",
    name: "username",
  },
  {
    id: "password",
    type: FormInputType.password,
    label: "Password",
    name: "password",
  },
];

const Login = () => {
  const [login] = useMutation(LoginMutation, {
    ignoreResults: true,
  });

  const onSubmit = (data: any) => login({ variables: { input: { ...data } } });
  return (
    <Form
      buttonProps={{ type: "submit", variant: "contained", color: "primary" }}
      inputs={inputs}
      onSubmit={onSubmit}
    />
  );
};

export default Login;
