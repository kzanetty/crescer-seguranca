import { useEffect, useState } from "react";
import { atualizarUsuarioApi, atualizarSenhaApi } from "../../../api";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import { NavListComponent, ButtonComponent, showToast } from "../../components";
import { validate, validateSenha } from '../../../utils'
import './edicao-perfil.screen.css'

export function EditarPerfilScreen() {
    const [usuario, setUsuario] = useGlobalUsuario();
    const [nome, setNome] = useState(usuario?.nome ? usuario?.nome : null)
    const [telefone, setTelefone] = useState(usuario?.telefone ? usuario?.telefone : null)
    const [foto, setFoto] = useState(usuario?.foto ? usuario?.foto : null)
    const [senha, setSenha] = useState('')
    const [visibilidade, setVisibilidade] = useState(false)

    async function handleSubmitDadosBasicos(event) {
        event.preventDefault();
        if (validate(nome, usuario.nome, telefone, usuario.telefone, foto, usuario.foto)) {
            try {
                const response = await atualizarUsuarioApi(nome, telefone, foto)
                showToast({ type: "success", message: "Alteração realizada com sucesso" });
                setUsuario(response)
            } catch (error) {
                showToast({ type: "error", message: "Erro ao tentar alterar dados do usuario" });
            }
        }
    }

    async function handleSubmitSenha(event) {
        event.preventDefault();
        if (validateSenha(senha)) {
            try {
                const response = await atualizarSenhaApi(senha)
                showToast({ type: "success", message: "Alteração de senha realizada com sucesso" });
                setUsuario(response)
                setVisibilidade(false);
            } catch (error) {
                showToast({ type: "error", message: "Erro ao tentar alterar senha do usuario" });
            }
        }
    }

    function mudarVisibilidade() {
        (visibilidade) ? setVisibilidade(false) : setVisibilidade(true)
    }

    return (
        <>
            <NavListComponent />
            <div className="container-screen-editar">


                {
                    (visibilidade) ?
                        <form onSubmit={handleSubmitSenha} className="formulario-alterar-usuario">
                            <div className="field senha">
                                <label htmlFor="senha" className="label-input-edicao">Senha </label>
                                <input
                                    className="input-formulario"
                                    id="senha"
                                    type="text"
                                    name="senha"
                                    value={senha}
                                    onChange={(e) => setSenha(e.target.value)}
                                    placeholder="Informe o senha."
                                />
                            </div>
                            <ButtonComponent texto="Alterar" />
                        </form>
                        :
                        <form onSubmit={handleSubmitDadosBasicos} className="formulario-alterar-usuario">
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
                }
                {
                    (visibilidade) ?
                        <ButtonComponent texto="Senha" onClick={mudarVisibilidade} />
                        :
                        <ButtonComponent texto="Dados básicos" onClick={mudarVisibilidade} />
                }
            </div>
        </>
    )
}