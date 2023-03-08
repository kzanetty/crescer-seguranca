import './nav-list.component.css'
import { NavLink } from 'react-router-dom'
import useGlobalUsuario from '../../../context/usuario/usuario.context'
import { useLogoutHook } from '../../../hook/logout.hook';
import { showToast } from "../";

export function NavListComponent() {
  const [usuario, setUsuario] = useGlobalUsuario();
  const logout = useLogoutHook();

  async function logoutButton() {
    if (usuario == null) {
      showToast({ type: "error", message: "Você já saiu da sua conta." });
    } else {
      showToast({ type: "default", message: "Você saiu da sua conta. Até breve" });
      // await logout.logout()
    }
  }

  const activeStyle = {
    color: '#e0cd69',
    textDecoration: 'none',
  }

  return (
    <nav className="navlist_container">
      <ul>

        {
          (usuario) ?
            <>
              <li className="options_nav_list_component">
                <NavLink
                  className="button_navlist"
                  to="/profile"
                  style={({ isActive }) => (isActive ? activeStyle : undefined)}
                >
                  <p className="button_nav_list">Perfil</p>
                </NavLink>
              </li>

              <li className="options_nav_list_component">
                <NavLink
                  className="button_navlist"
                  to="/edit"
                  style={({ isActive }) => (isActive ? activeStyle : undefined)}
                >
                  <p className="button_nav_list">Editar</p>
                </NavLink>
              </li>
            </>
            :
            null
        }


        {
          (usuario) ? null :
            <>
              <li className="options_nav_list_component">
                <NavLink
                  className="button_navlist"
                  to="/"
                  style={({ isActive }) => (isActive ? activeStyle : undefined)}
                >
                  <p className="button_nav_list">login</p>
                </NavLink>
              </li>

              <li className="options_nav_list_component">
                <NavLink
                  className="button_navlist"
                  to="/create"
                  style={({ isActive }) => (isActive ? activeStyle : undefined)}
                >
                  <p className="button_nav_list">Cadastro</p>
                </NavLink>
              </li>
            </>
        }


        <li className="options_nav_list_component">
          <NavLink
            onClick={logoutButton}
            className="button_navlist"
            to="/"
            style={({ isActive }) => (isActive ? activeStyle : undefined)}
          >
            Logout
          </NavLink>
        </li>
      </ul>
    </nav>
  )
}
