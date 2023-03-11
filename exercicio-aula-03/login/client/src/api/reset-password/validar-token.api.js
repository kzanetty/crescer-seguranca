import { instanceAxios } from "../_base/axios.instance";

export async function validarTokenApi(token) {
  let response = await instanceAxios.get(`reset?token=${token}`);
  return response.data;
}
