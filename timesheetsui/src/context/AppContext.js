import React, { useReducer } from "react";

const initialState = {
  loggedIn: false,
  data: []
};

// generate context to be shared across application
const AppContext = React.createContext(initialState);

// define actions that can be performed on state
const actions = {
  LOG_USER_IN: "LOG_USER_IN"
};

// determines how state is updated when an action is dispatched
const reducer = (state = initialState, action) => {
  switch (action.type) {
    case actions.LOG_USER_IN: {
      return { ...state, loggedIn: true };
    }
    default: {
      return state;
    }
  }
};

// generate context provider to wrap components requiring the state defined above
const AppContextProvider = props => {
  const [appState, dispatchToAppState] = useReducer(reducer, initialState);
  const value = [appState, dispatchToAppState];

  return (
    <AppContext.Provider value={value}>{props.children}</AppContext.Provider>
  );
};

export { AppContext, AppContextProvider, actions };
