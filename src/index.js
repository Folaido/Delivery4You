import React from "react";
import ReactDOM from "react-dom";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";
import "./assets/css/style.css";
import Form from "./components/Form";
import registerServiceWorker from "./registerServiceWorker";
import { BrowserRouter } from "react-router-dom";
import App from "./App";


// ReactDOM.render(<Form />, document.getElementById("root"));

ReactDOM.render(
<<<<<<< HEAD
  <BrowserRouter>
    <Form />
  </BrowserRouter>,
  document.getElementById("root")
);
=======
    <BrowserRouter>
        <App />
    </BrowserRouter>,
    document.getElementById("root"));

>>>>>>> d847a5e3e0c3b2ceef99b30e5013a46a6c03c6db

registerServiceWorker();
