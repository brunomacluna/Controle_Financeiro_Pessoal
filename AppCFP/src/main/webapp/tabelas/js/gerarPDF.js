// gerarPDF.js
import { sanitizarTexto } from './validacoes.js';
import { corpoTabelaCustos, corpoTabelaRenda } from './tabelas.js';
import { totalCustos, totalRenda, totalResultado } from './calcular.js';

let botaoGerarPDF = document.getElementById("button-gerar-pdf");
let linhasCustos = corpoTabelaCustos.getElementsByTagName("tr");
let linhasRenda = corpoTabelaRenda.getElementsByTagName("tr");

function gerarPDF(event) {
    event.preventDefault();
    let dadosCustos = [];
    for (let linha of linhasCustos) {
        let descricao = sanitizarTexto(linha.querySelector('input[name="descricao"]').value);
        let valorStr = linha.querySelector('input[name="valor"]').value;
        let valor = parseFloat(valorStr);

        if ((descricao && !valorStr) || (!descricao && valorStr)) {
            alert("Preencha descrição e valor juntos ou deixe ambos vazios.");
            return;
        }

        if (descricao && valorStr) {
            if (isNaN(valor) || valor < 0) {
                alert("Valor deve ser um número positivo.");
                return;
            }
            dadosCustos.push({ descricao, valor });
        }
    }
    console.log(dadosCustos);

    const totalCustosValor = totalCustos(dadosCustos);
    document.getElementById("total-custo").textContent = totalCustosValor.toFixed(2);

    let dadosRenda = [];
    for (let linha of linhasRenda) {
        let descricao = sanitizarTexto(linha.querySelector('input[name="descricao"]').value);
        let valorStr = linha.querySelector('input[name="valor"]').value;
        let valor = parseFloat(valorStr);

        if ((descricao && !valorStr) || (!descricao && valorStr)) {
            alert("Preencha descrição e valor juntos ou deixe ambos vazios.");
            return;
        }

        if (descricao && valorStr) {
            if (isNaN(valor) || valor < 0) {
                alert("Valor deve ser um número positivo.");
                return;
            }
            dadosRenda.push({ descricao, valor });
        }
    }
    console.log(dadosRenda);

    const totalRendaValor = totalRenda(dadosRenda);
    document.getElementById("total-renda").textContent = totalRendaValor.toFixed(2);

    const resultadoFinal = totalResultado(totalRendaValor, totalCustosValor);
    const resultadoElemento = document.getElementById("valor-total");
    resultadoElemento.textContent = resultadoFinal.toFixed(2);
    resultadoElemento.style.color = resultadoFinal >= 0 ? "green" : "red";

    fetch('/gerar-relatorio', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            custos: dadosCustos,
            renda: dadosRenda,
            totalCustos: totalCustosValor,
            totalRenda: totalRendaValor,
            diferencaFinal: resultadoFinal
        })
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao salvar os dados");
            return response.blob();
        })
        .then(data => {
            console.log("Resposta do backend:", data);
            alert("PDF gerado com sucesso!");
        })
        .catch(error => {
            console.error("Erro na requisição:", error);
            alert("Falha ao salvar dados.");
        });
}

botaoGerarPDF.addEventListener('click', gerarPDF);
