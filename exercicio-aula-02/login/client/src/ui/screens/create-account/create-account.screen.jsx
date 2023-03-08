import { useState } from "react";
import { ButtonComponent, NavListComponent } from "../../components"
import { CriarContaApi } from "../../../api/criar-conta/criar.conta.api";
import { showToast } from "../../components";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import './create-account.screen.css'
import { useNavigate } from "react-router-dom";

export function CreateAccountScreen() {
    const [nome, setNome] = useState(null);
    const [email, setEmail] = useState(null);
    const [senha, setSenha] = useState(null);
    const [telefone, setTelefone] = useState(null);
    const [funcao, setFuncao] = useState(null);
    const [foto, setFoto] = useState(null);

    const navigate = useNavigate()

    const [usuario, setUsuario] = useGlobalUsuario();

    async function criarConta() {
        if (validacoes(nome, email, senha, telefone, funcao, foto)) {

            try {
                const response = await CriarContaApi({ nome, email, senha, telefone, funcao, foto })
                setUsuario(response);
                showToast({ type: "success", message: "Personagem criado com sucesso." });
                navigate('/profile')
            } catch (error) {
                console.log(error)
                showToast({ type: "error", message: "erro ao criar personagem" });
            }
        }
    }

    function validacoes(nome, email, senha, telefone, funcao, foto) {
        if (nome == null || nome == "") {
            showToast({ type: "error", message: "O nome é obrigatório." });
            return false
        }
        if (email == null || nome == "") {
            showToast({ type: "error", message: "O email é obrigatório." });
            return false
        }
        if (senha == null || nome == "") {
            showToast({ type: "error", message: "A senha é obrigatória." });
            return false
        }
        if (telefone == null || nome == "") {
            showToast({ type: "error", message: "A telefone é obrigatória." });
            return false
        }
        if (funcao == null || nome == "") {
            showToast({ type: "error", message: "A funcao é obrigatória." });
            return false
        }
        if (foto == null || nome == "") {
            showToast({ type: "error", message: "A foto é obrigatória." });
            return false
        }
        return true;
    }

    return (
        <>
            <NavListComponent />
            <div className="container-formulario-criar-conta">
                <h1>Cadastro</h1>

                <div className="formulario-criar-conta">

                    <div className="nome field-criar-conta">
                        <label htmlFor="nome">Nome: </label>
                        <input
                            className="input-formulario"
                            id="nome"
                            type="text"
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                        />
                    </div>

                    <div className="email field-criar-conta">
                        <label htmlFor="email">Email: </label>
                        <input
                            className="input-formulario"
                            id="email"
                            type="text"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div className="senha field-criar-conta">
                        <label htmlFor="senha">Senha: </label>
                        <input
                            className="input-formulario"
                            id="senha"
                            type="password"
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                        />
                    </div>

                    <div className="telefone field-criar-conta">
                        <label htmlFor="telefone">telefone: </label>
                        <input
                            className="input-formulario"
                            id="telefone"
                            type="text"
                            value={telefone}
                            onChange={(e) => setTelefone(e.target.value)}
                        />
                    </div>

                    <div className="funcao field-criar-conta">
                        <label htmlFor="funcao">ADMIN ou USER: </label>
                        <input
                            className="input-formulario"
                            id="funcao"
                            type="text"
                            value={funcao}
                            onChange={(e) => setFuncao(e.target.value)}
                        />
                    </div>

                    <div className="foto field-criar-conta">
                        <label htmlFor="foto">URL foto: </label>
                        <input
                            className="input-formulario"
                            id="foto"
                            type="text"
                            value={foto}
                            onChange={(e) => setFoto(e.target.value)}
                        />
                    </div>

                    <div className="button-criar-conta">
                        <ButtonComponent onClick={criarConta} texto="criar" />
                    </div>
                </div>
            </div>
        </>
    )
}

/*
import { useState } from "react";
import { NavListComponent } from "../../components"
import { CriarContaApi } from "../../../api/criar-conta/criar.conta.api";
import { ButtonComponent, showToast } from "../../components";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import './create-account.screen.css'
import { useNavigate } from "react-router-dom";

export function CreateAccountScreen() {
    const [nome, setNome] = useState(null);
    const [email, setEmail] = useState(null);
    const [senha, setSenha] = useState(null);
    const [apelido, setApelido] = useState(null);
    const [dataNascimento, setDataNascimento] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);

    const navigate = useNavigate()

    const [usuario, setUsuario] = useGlobalUsuario();

    async function criarConta() {
        if (validacoes(nome, email, senha, dataNascimento)) {
            try {
                const response = await CriarContaApi({ nome, email, apelido, dataNascimento, imageUrl, senha })
                setUsuario(response);
                showToast({ type: "success", message: "Personagem criado com sucesso." });
                navigate('/profile')
            } catch (error) {
                showToast({ type: "error", message: error.response.data.message });
            }
        }
    }

    function validacoes(nome, email, senha, dataNascimento) {
        if (nome == null || nome == "") {
            showToast({ type: "error", message: "O nome é obrigatório." });
            return false
        }
        if (email == null || nome == "") {
            showToast({ type: "error", message: "O email é obrigatório." });
            return false
        }
        if (senha == null || nome == "") {
            showToast({ type: "error", message: "A senha é obrigatória." });
            return false
        }
        if (dataNascimento == null || nome == "") {
            showToast({ type: "error", message: "A dataNascimento é obrigatória." });
            return false
        }
        return true;
    }

*/