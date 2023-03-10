import { instanceAxios } from "../_base/axios.instance";

export async function atualizarUsuarioApi(nome, telefone, foto) {
  let response = await instanceAxios.post("/usuarios/atualizar", {
    nome,
    telefone,
    foto,
  });
  return response.data;
}
