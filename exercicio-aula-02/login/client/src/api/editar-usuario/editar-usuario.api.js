import { instanceAxios } from "../_base/axios.instance";

export async function atualizarUsuarioApi(id, nome, telefone, foto) {
  let response = await instanceAxios.post("/usuarios/atualizar", {
    id,
    nome,
    telefone,
    foto,
  });
  return response.data;
}
