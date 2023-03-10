import { instanceAxios } from "../_base/axios.instance";

export async function atualizarSenhaApi(senha) {
  let response = await instanceAxios.post("/usuarios/atualizar/senha", {
    senha,
  });
  return response.data;
}
