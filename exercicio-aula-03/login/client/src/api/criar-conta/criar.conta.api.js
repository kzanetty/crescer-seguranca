import { instanceAxios } from "../_base/axios.instance";

export async function CriarContaApi({
  nome,
  email,
  senha,
  telefone,
  funcao,
  foto,
}) {
  let response = await instanceAxios.post("/usuarios", {
    nome: nome,
    email: email,
    telefone: telefone,
    funcao: funcao,
    foto: foto,
    senha: senha,
  });
  return response.data;
}
