document.addEventListener('DOMContentLoaded', function() {

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

let corpoTabelaCustos = document.getElementById("corpo-tabela-custos");
let linhaTotalCustos = document.getElementById("linha-total-custos");
let botaoAdicionarLinhaCustos = document.getElementById("adicionar-linha-custos");

function adicionarLinhaTabelaCustos(event) {
    event.preventDefault();
    let novaLinha = document.createElement("tr");
    novaLinha.innerHTML = `
        <td><input type="text" name="descricao" placeholder="Escreva aqui"></td>
        <td><input type="number" name="valor" placeholder="Valor"></td>
        <td><button class="botao-deletar">Deletar</button></td>
    `
    corpoTabelaCustos.appendChild(novaLinha, linhaTotalCustos);
}
botaoAdicionarLinhaCustos.addEventListener('click', adicionarLinhaTabelaCustos);

let corpoTabelaRenda = document.getElementById("corpo-tabela-renda");
let linhaTotalRenda = document.getElementById("linha-total-renda");
let botaoAdicionarLinhaRenda = document.getElementById("adicionar-linha-renda");

function adicionarLinhaTabelaRenda(event) {
    event.preventDefault();
    let novaLinha2 = document.createElement("tr");
    novaLinha2.innerHTML = `
        <td><input type="text" name="descricao" placeholder="Escreva aqui"></td>
        <td><input type="number" name="valor" placeholder="Valor"></td>
        <td><button class="botao-deletar">Deletar</button></td>
    `
    corpoTabelaRenda.appendChild(novaLinha2, linhaTotalRenda);
}
botaoAdicionarLinhaRenda.addEventListener('click', adicionarLinhaTabelaRenda);

let botoesDeletar = document.getElementsByClassName("botao-deletar");
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('botao-deletar')) {
        event.preventDefault();
        
        // Apaga a <tr> mais próxima do botão clicado
        let linha = event.target.closest('tr');
        if (linha) {
            linha.remove();
        }
    }
});

let botaoSalvar = document.getElementById("button-salvar");
let linhasCustos = corpoTabelaCustos.getElementsByTagName("tr");
let linhasRenda = corpoTabelaRenda.getElementsByTagName("tr");
function salvarDados(event){
    event.preventDefault();
    let dadosCustos = [];
    for (let linha of linhasCustos){
        let descricao = linha.querySelector('input[name="descricao"]').value;
        let valor = linha.querySelector('input[name="valor"]').value;

        dadosCustos.push({
            descricao: descricao,
            valor: parseFloat(valor) || 0
        });
    }
    console.log(dadosCustos);   

    let totalCustos = 0;
    for (let custos of dadosCustos){
        totalCustos += custos.valor;
    }
    document.getElementById("total-custo").textContent = totalCustos.toFixed(2);

    let dadosRenda = [];
    for (let linha of linhasRenda){
        let descricao = linha.querySelector('input[name="descricao"]').value;
        let valor = linha.querySelector('input[name="valor"]').value;

        dadosRenda.push({
            descricao: descricao,
            valor: parseFloat(valor) || 0
        });
    }
    console.log(dadosRenda);

    let totalRenda = 0;
    for (let renda of dadosRenda){
        totalRenda += renda.valor;
    }
    document.getElementById("total-renda").textContent = totalRenda.toFixed(2);

    let totalResultado = totalRenda - totalCustos;
    let resultadoElemento = document.getElementById("valor-total");
    resultadoElemento.textContent = totalResultado.toFixed(2);
    if (totalResultado > 0) {
        resultadoElemento.style.color = "green";
    } else {
        resultadoElemento.style.color = "red";
    }


    fetch('/salvar-dados', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        custos: dadosCustos,
        renda: dadosRenda
    })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro ao salvar os dados");
        }
        return response.json(); // ou .text() se o backend não devolver JSON
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

let botaoGerarPDF = document.getElementById("button-gerar-pdf");
function gerarPDF (event){
    event.preventDefault();
    let dadosCustos = [];
    for(let linha of linhasCustos){
        let descricao = linha.querySelector('input[name="descricao"]').value;
        let valor = linha.querySelector('input[name="valor"]').value;

        dadosCustos.push({
            descricao: descricao,
            valor: parseFloat(valor) || 0         
        });
    }
    console.log(dadosCustos);

    let dadosRenda = [];
    for(let linha of linhasRenda){
        let descricao = linha.querySelector('input[name="descricao"]').value;
        let valor = linha.querySelector('input[name="valor"]').value;

        dadosRenda.push({
            descricao: descricao,
            valor: parseFloat(valor) || 0
        });
    }
    console.log(dadosRenda);

    fetch ('/gerar-relatorio', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            custos: dadosCustos,
            renda: dadosRenda
        })
    })
    .then(response => {
        if(!response.ok){
            throw new Error("Erro ao salvar os dados");
        }
        return response.blob();
    })
    .then(data => {
        console.log("Resposta dp backend:", data);
        alert("PDF gerado com sucesso!");
    })
    .catch(error => {
        console.error("Erro na requisição:", error);
        alert("Falha ao salvar dados.");
    })
}
botaoGerarPDF.addEventListener('click', gerarPDF);



});//DOMContentLoaded