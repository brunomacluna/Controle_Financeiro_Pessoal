// eventos.js
import { corpoTabelaCustos, corpoTabelaRenda } from './tabelas.js';
import { coletarDadosTabela } from './tabelas.js';
import { totalCustos, totalRenda, totalResultado } from './calcular.js';

document.addEventListener('keydown', function(event) {
    if (event.key === "Enter") {
        let target = event.target;
        if (target.tagName === "INPUT") {
            event.preventDefault();

            // Opcional: mover para o próximo input
            let inputs = Array.from(document.querySelectorAll('input[name="descricao"], input[name="valor"]'));
            let index = inputs.indexOf(target);
            if (index !== -1 && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        }
    }
});

export function atualizarTotaisNaTela() {
    const dadosCustos = coletarDadosTabela(corpoTabelaCustos);
    const dadosRenda = coletarDadosTabela(corpoTabelaRenda);

    const totalC = totalCustos(dadosCustos);
    const totalR = totalRenda(dadosRenda);
    const resultado = totalResultado(totalR, totalC);

    document.getElementById("total-custo").textContent = totalC.toFixed(2);
    document.getElementById("total-renda").textContent = totalR.toFixed(2);

    const resultadoElemento = document.getElementById("valor-total");
    resultadoElemento.textContent = resultado.toFixed(2);
    resultadoElemento.style.color = resultado >= 0 ? "green" : "red";
}

document.addEventListener('input', function(event) {
    const target = event.target;
    if (target.name === "valor") {
        atualizarTotaisNaTela();
    }
});


