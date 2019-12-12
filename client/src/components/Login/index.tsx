import React from "react";
import Form from "../../components/Form";
import {
  FormInput,
  FormInputType,
} from "../../components/Form/utils/validationSchema";

const CONTAINER_STYLES: React.CSSProperties = {
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  height: "100vh",
  flexDirection: "column",
};

const INPUT_STYLES: React.CSSProperties = {
  width: "100%",
};

const FORM_STYLES: React.CSSProperties = {
  width: "50%",
};

const LINK_CONTAINER_STYLES: React.CSSProperties = {
  display: "flex",
  justifyContent: "space-evenly",
  width: "50%",
  marginTop: "1rem",
};

const inputs: FormInput[] = [
  {
    id: "username",
    type: FormInputType.text,
    label: "Username",
    name: "username",
    style: INPUT_STYLES,
  },
  {
    id: "password",
    type: FormInputType.password,
    label: "Password",
    name: "password",
    style: INPUT_STYLES,
  },
];

interface LoginProps {
  onSubmit: (data: any) => void;
}

const Login: React.FC<LoginProps> = ({ onSubmit }) => {
  return (
    <div style={CONTAINER_STYLES}>
      <Form
        buttonProps={{
          type: "submit",
          variant: "contained",
          color: "primary",
          style: { marginTop: "1rem", width: "100%" },
        }}
        inputs={inputs}
        onSubmit={onSubmit}
        formStyles={FORM_STYLES}
      />
      <div style={LINK_CONTAINER_STYLES}>
        <a>Forgot Password</a>
        <a>Register</a>
      </div>
    </div>
  );
};

export default Login;
