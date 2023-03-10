import { showToast } from "../ui/components";

export function validateSenha(senha) {
  if (senha == null) {
    showToast({
      type: "error",
      message: "Você deve informar uma senha valida.",
    });
    return false;
  }
  if (senha.length < 5) {
    showToast({
      type: "error",
      message: "o minimo de caracteres é 5.",
    });
    return false;
  }
  if ((senha = "")) {
    showToast({
      type: "error",
      message: "Você deve informar uma senha valida.",
    });
    return false;
  }
  return true;
}
