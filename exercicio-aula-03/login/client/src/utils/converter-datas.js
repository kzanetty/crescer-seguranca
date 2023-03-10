export function exibirDataEHora(dia) {
  return `${getDia(dia)} as ${getHora(dia)}`;
}

function getDia(dataEmTimestamp) {
  const dataEmString = dataEmTimestamp.split("T");
  return dataEmString[0].slice(0, 10).split("-").reverse().join("/");
}

function getHora(horaEmTimestamp) {
  const dataEmString = horaEmTimestamp.split("T");
  return dataEmString[1].slice(0, 5);
}
