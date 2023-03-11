import { useState } from "react";
import { esqueceuSenhaApi } from "../../../api";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import { ButtonComponent, NavListComponent, showToast } from "../../components";
import "./reset-password.css"

export function EsqueceuSenhaScreen() {
    const [email, setEmail] = useState();
    const [usuario, setUsuario] = useGlobalUsuario();
    const [emailEnviado, setEmailEnviado] = useState(false)


    async function enviarEmail() {
        try {
            const response = await esqueceuSenhaApi(email, document.location.origin)
            setEmailEnviado(true)
            showToast({ type: "success", message: "Email enviado com sucesso." });
        } catch (error) {
            showToast({ type: "error", message: "Erro ao tentar enviar email." });
        }
    }

    return (
        <>
            <NavListComponent />
            {
                (emailEnviado) ?
                    <div className="reset-password-container">
                        <p> Check seu email. Foi enviado um link de recuperação para {email}</p>
                    </div>
                    :
                    <div className="reset-password-container">
                        <h1>Esqueceu sua senha</h1>
                        <p>Nós iremos mandar um link de redefinição de senha para o seu email.</p>
                        <div className="container-formulario-login-screen">
                            <label htmlFor="email">Email </label>
                            <input
                                className="input-formulario"
                                id="email"
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                            <ButtonComponent onClick={enviarEmail} texto="Enviar" />
                        </div>
                    </div>
            }
        </>
    )
}
