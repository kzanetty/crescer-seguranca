import { instanceAxios } from "../_base/axios.instance";

export async function esqueceuSenhaApi(email, siteUrl) {
  let response = await instanceAxios.post("/reset", {
    email,
    siteUrl,
  });
  return response.data;
}
