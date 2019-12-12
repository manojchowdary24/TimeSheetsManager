import React from "react";
import useForm from "react-hook-form";
import Button, { ButtonProps } from "@material-ui/core/Button";
import { FormInput, createFormSchema } from "./utils/validationSchema";
import { mapInputToFormField } from "./utils";

interface FormProps {
  mode?: "onBlur" | "onChange" | "onSubmit";
  onSubmit: (data: any) => void;
  inputs: FormInput[];
  buttonProps: ButtonProps;
  buttonText?: string;
}

const Form: React.FC<FormProps> = ({
  mode = "onBlur",
  onSubmit,
  inputs,
  buttonProps,
  buttonText = "Submit",
}) => {
  const { register, handleSubmit, errors } = useForm({
    validationSchema: createFormSchema(inputs),
    mode,
  });

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      {inputs.map((input: FormInput) => (
        <div key={input.id}>
          {mapInputToFormField(input, register, errors)}
          {errors[input.name] && <p>{String(errors[input.name].message)}</p>}
        </div>
      ))}
      <Button {...buttonProps}>{buttonText}</Button>
    </form>
  );
};

export default Form;
