import { NavListComponent, UsuarioComponent } from "../../components";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import './perfil.screen.css'

export function PerfilScreen() {
    const [usuario, setUsuario] = useGlobalUsuario();

    return (
        <>
            <NavListComponent />
            <UsuarioComponent usuario={usuario} />
        </>
    )
}