import { useEffect, useState } from "react";
import { atualizarUsuarioApi } from "../../../api/editar-usuario/editar-usuario.api";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import { NavListComponent, ButtonComponent, showToast } from "../../components";
import { validate } from '../../../utils'
import './edicao-perfil.screen.css'

export function EditarPerfilScreen() {
    const [usuario, setUsuario] = useGlobalUsuario();
    const [nome, setNome] = useState(usuario?.nome ? usuario?.nome : null)
    const [telefone, setTelefone] = useState(usuario?.telefone ? usuario?.telefone : null)
    const [foto, setFoto] = useState(usuario?.foto ? usuario?.foto : null)

    async function handleSubmit(event) {
        event.preventDefault();
        if (validate(nome, usuario.nome, telefone, usuario.telefone, foto, usuario.foto)) {
            try {
                const response = await atualizarUsuarioApi(usuario.id, nome, telefone, foto)
                showToast({ type: "success", message: "Alteração realizada com sucesso" });
                setUsuario(response)
            } catch (error) {
                showToast({ type: "error", message: "Erro ao tentar alterar dados do usuario" });
            }
        }
    }

    return (
        <div className="container-screen-editar">
            <NavListComponent />
            <div className="container-formulario">
                <form onSubmit={handleSubmit} className="formulario-alterar-usuario">
                    <div className="field nome">
                        <label htmlFor="nome" className="label-input-edicao">Nome </label>
                        <input
                            className="input-formulario"
                            id="nome"
                            type="text"
                            name="nome"
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                            placeholder="Informe o nome."
                        />
                    </div>

                    <div className="field telefone">
                        <label htmlFor="telefone" className="label-input-edicao">telefone </label>
                        <input
                            className="input-formulario"
                            id="telefone"
                            type="text"
                            name="telefone"
                            value={telefone}
                            onChange={(e) => setTelefone(e.target.value)}
                            placeholder="Informe o telefone."
                        />
                    </div>


                    <div className="field foto">
                        <label htmlFor="foto" className="label-input-edicao">URL foto </label>
                        <input
                            className="input-formulario"
                            id="foto"
                            type="text"
                            name="foto"
                            value={foto}
                            onChange={(e) => setFoto(e.target.value)}
                            placeholder="Informe a url da foto."
                        />
                    </div>
                    <ButtonComponent texto="Alterar" />
                </form>
            </div>
        </div>
    )
}