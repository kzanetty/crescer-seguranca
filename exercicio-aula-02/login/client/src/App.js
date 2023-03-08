import "./app.css";
import { RouterProvider } from "react-router-dom";
import { router } from "./router/index";
import { GlobalUsuarioProvider } from "./context/usuario/usuario.context";
import ToastAnimated from "./ui/components/toastr/toastr.component";
import { Footer } from "./ui/components";

function App() {
  return (
    <div className="App">
      <GlobalUsuarioProvider>
        <ToastAnimated />
        <RouterProvider router={router} />
        <Footer />
      </GlobalUsuarioProvider>
    </div>
  );
}

export default App;
