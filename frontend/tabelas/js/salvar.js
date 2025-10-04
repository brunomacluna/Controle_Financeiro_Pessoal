// salvar.js
import { sanitizarTexto } from './validacoes.js';
import { corpoTabelaCustos, corpoTabelaRenda } from './tabelas.js';

let botaoSalvar = document.getElementById("button-salvar");
let linhasCustos = corpoTabelaCustos.getElementsByTagName("tr");
let linhasRenda = corpoTabelaRenda.getElementsByTagName("tr");

function salvarDados(event) {
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

    let totalCustos = 0;
    for (let custos of dadosCustos) {
        totalCustos += custos.valor;
    }
    document.getElementById("total-custo").textContent = totalCustos.toFixed(2);

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

    let totalRenda = 0;
    for (let renda of dadosRenda) {
        totalRenda += renda.valor;
    }
    document.getElementById("total-renda").textContent = totalRenda.toFixed(2);

    let totalResultado = totalRenda - totalCustos;
    let resultadoElemento = document.getElementById("valor-total");
    resultadoElemento.textContent = totalResultado.toFixed(2);
    resultadoElemento.style.color = totalResultado > 0 ? "green" : "red";

    fetch('/salvar-dados', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            custos: dadosCustos,
            renda: dadosRenda,
            totalCustos,
            totalRenda,
            diferencaFinal: totalResultado
        })
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao salvar os dados");
            return response.json();
        })
        .then(data => {
            console.log("Resposta do backend:", data);
            alert("Dados salvos com sucesso!");
        })
        .catch(error => {
            console.error("Erro na requisição:", error);
            alert("Falha ao salvar os dados.");
        });
}

botaoSalvar.addEventListener('click', salvarDados);
