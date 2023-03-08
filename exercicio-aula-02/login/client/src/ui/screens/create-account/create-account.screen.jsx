import { useState } from "react";
import { NavListComponent } from "../../components"
import { CriarContaApi } from "../../../api/criar-conta/criar.conta.api";
import { showToast } from "../../components";
import useGlobalUsuario from "../../../context/usuario/usuario.context";
import './create-account.screen.css'
import { useNavigate } from "react-router-dom";

export function CreateAccountScreen() {
    const [nome, setNome] = useState("teste1");
    const [email, setEmail] = useState("teste1@cwi.com.br");
    const [senha, setSenha] = useState('12345');
    const [telefone, setApelido] = useState("testete");
    const [funcao, setDataNascimento] = useState("ADMIN");
    const [foto, setImageUrl] = useState("imagem aqui");

    const navigate = useNavigate()

    const [usuario, setUsuario] = useGlobalUsuario();

    async function criarConta() {
        try {
            const response = await CriarContaApi({ nome, email, senha, telefone, funcao, foto })
            setUsuario(response);
            showToast({ type: "success", message: "Personagem criado com sucesso." });
            navigate('/profile')
        } catch (error) {
            showToast({ type: "error", message: "erro ao criar personagem" });
        }
    }


    return (
        <>
            <NavListComponent />
            <button onClick={criarConta}>Criar Conta</button>
        </>
    )
}