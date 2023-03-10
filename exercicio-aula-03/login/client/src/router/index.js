import { createBrowserRouter } from "react-router-dom";
import {
  LoginScreen,
  CreateAccountScreen,
  PerfilScreen,
  EditarPerfilScreen,
} from "../ui/screens";
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
    path: "/edit",
    element: <PrivateRoute Screen={EditarPerfilScreen} />,
  },
  {
    path: "/profile",
    element: <PrivateRoute Screen={PerfilScreen} />,
  },
]);
