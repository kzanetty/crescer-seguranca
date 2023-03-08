import { useEffect, useState } from "react";
import { NavListComponent } from "../../components"
import { useNavigate } from "react-router";
import { LogarApi } from "../../../api/logar/logar.api";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import './login.screen.css'
import { ButtonComponent, showToast } from "../../components";

export function LoginScreen() {
    const [email, setEmail] = useState("puma@cwi.com.br");
    const [senha, setSenha] = useState("12345");
    const [usuario, setUsuario] = useGlobalUsuario();
    const navigate = useNavigate();

    async function handleLogar() {
        try {
            const response = await LogarApi(email, senha)
            setUsuario(response)
            showToast({ type: "success", message: "Você realizou login com sucesso." });
        } catch (error) {
            showToast({ type: "error", message: "Erro ao tentar fazer login." });
        }
    }


    // useEffect(() => {
    //     if (usuario) {
    //         navigate('/profile')
    //     }
    // }, [usuario])

    return (
        <>
            <NavListComponent />
            <div className="container-login-screen">
                <h1>Login</h1>
                <div className="container-formulario-login-screen">
                    <label htmlFor="email">Email </label>
                    <input
                        className="input-formulario"
                        id="email"
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <br />
                    <label htmlFor="senha">Senha </label>
                    <input
                        className="input-formulario"
                        id="senha"
                        type="password"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                    />
                    <br />
                    <ButtonComponent onClick={handleLogar} texto="login" />
                </div>

            </div>
        </>
    )
}
