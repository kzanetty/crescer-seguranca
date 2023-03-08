import { showToast } from "../ui/components";

export function validate(
  nome,
  nomeUsuario,
  telefone,
  telefoneUsuario,
  foto,
  fotoUsuario
) {
  return (
    validacao(nome) &&
    validacao(telefone) &&
    validacao(foto) &&
    validateRepeticao(
      nome,
      nomeUsuario,
      telefone,
      telefoneUsuario,
      foto,
      fotoUsuario
    )
  );
}

function validacao(dado) {
  if (dado == null) {
    showToast({ type: "error", message: "Os campos não podem estar vazios." });
    return false;
  }
  if (dado.length < 1) {
    showToast({
      type: "error",
      message: "Você deve preencher todas informações",
    });
    return false;
  }
  if ((dado = "")) {
    showToast({
      type: "error",
      message: "Você deve preencher todas informações",
    });
    return false;
  }
  return true;
}

function validateRepeticao(
  nome,
  nomeUsuario,
  telefone,
  telefoneUsuario,
  foto,
  fotoUsuario
) {
  if (
    nome === nomeUsuario &&
    telefone === telefoneUsuario &&
    foto === fotoUsuario
  ) {
    showToast({ type: "error", message: "Você não está alterando nada" });
    return false;
  }
  return true;
}
