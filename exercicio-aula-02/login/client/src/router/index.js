import { createBrowserRouter } from "react-router-dom";
import { LoginScreen, CreateAccountScreen, PerfilScreen } from "../ui/screens";
import { PrivateRoute } from "./private-router.component";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <LoginScreen />,
  },
  {
    path: "/create",
    element: <CreateAccountScreen />,
  },
  {
    path: "/profile",
    element: <PrivateRoute Screen={PerfilScreen} />,
  },
]);
