import './card-usuario.component.css'
import { exibirDataEHora } from '../../../utils'

export function UsuarioComponent({ usuario }) {
    return (
        <div className="card-usuario">
            <div className="card-informacoes-usuario">
                <div className="informacoes-texto-card-usuario">
                    <p>id: {usuario?.id}</p>
                    <p>Nome: {usuario?.nome}</p>
                    <p>Email: {usuario?.email}</p>
                    <p>Telefone: {usuario?.telefone}</p>
                    <p>Criando em: {exibirDataEHora(usuario?.criadoEm)}</p>
                    <p>Atualizado em: {usuario?.atualizadoEm ? exibirDataEHora(usuario.atualizadoEm) : "Nunca atualizado"}</p>
                    <p>Função: {usuario?.funcao}</p>
                </div>
                <div className="imagem-usuario">
                    <img src={usuario.foto} alt="imagem de usuario" />
                </div>
            </div>
        </div>
    )
}