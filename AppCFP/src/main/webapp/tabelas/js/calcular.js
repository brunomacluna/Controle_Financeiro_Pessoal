export function totalCustos(dadosCustos) {
    return dadosCustos.reduce((soma, item) => soma + item.valor, 0);
}

export function totalRenda(dadosRenda) {
    return dadosRenda.reduce((soma, item) => soma + item.valor, 0);
}

export function totalResultado(totalRenda, totalCustos) {
    return totalRenda - totalCustos;
}
