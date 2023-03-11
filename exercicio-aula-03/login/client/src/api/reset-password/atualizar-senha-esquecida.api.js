import { instanceAxios } from "../_base/axios.instance";

export async function atualizarSenhaEsquecidaApi(senha, token) {
  let response = await instanceAxios.post("/reset/update", {
    senha,
    token,
  });
  console.log(response.data);
  return response.data;
}
