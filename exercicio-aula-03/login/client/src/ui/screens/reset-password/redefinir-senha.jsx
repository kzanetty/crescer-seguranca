import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { atualizarSenhaEsquecidaApi, validarTokenApi } from "../../../api";
import { ButtonComponent, NavListComponent, showToast } from "../../components";
import "./reset-password.css"

export function RedefinirSenhaScreen() {
    const [senha, setSenha] = useState("");
    const [valido, setValido] = useState(false)
    const navigate = useNavigate();


    function getToken() {
        const url = document.URL;
        const token = url.slice(url.indexOf("=") + 1)
        return token
    }

    async function atualizarSenhaEsquecida() {
        try {
            await atualizarSenhaEsquecidaApi(senha, getToken());
            navigate("/")
            showToast({ type: "success", message: "Senha alterada com sucesso." });
        } catch (error) {
            showToast({ type: "error", message: "Erro ao enviar nova senha." });
        }
    }

    useEffect(() => {
        async function loadValidateToken() {
            try {
                const response = await validarTokenApi(getToken())
                setValido(response)
            } catch (error) {
                showToast({ type: "error", message: "Erro ao processar token." });
            }
        }
        loadValidateToken()
    }, [])

    return (
        <>
            <NavListComponent />
            {
                (valido) ?

                    <div className="reset-password-container">
                        <h1>Redefinir senha</h1>
                        <p>Informe sua nova senha.</p>
                        <div className="container-formulario-login-screen">
                            <label htmlFor="senha">senha </label>
                            <input
                                className="input-formulario"
                                id="senha"
                                type="text"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                            />
                            <ButtonComponent onClick={atualizarSenhaEsquecida} texto="Alterar" />
                        </div>
                    </div>
                    :
                    <p>Token invalido</p>
            }

        </>
    )
}
