document.addEventListener('DOMContentLoaded', function() {

let botaoGerarPDF = document.getElementById("button-gerar-pdf");
let tabelaCustos = document.getElementById("table-tabela-custos");
let tabelaRenda = document.getElementById("table-tabela-renda");

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

let linhasCustos = corpoTabelaCustos.getElementsByTagName("tr");
let botaoSalvar = document.getElementById("button-salvar");
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
}
botaoSalvar.addEventListener('click', salvarDados);




});//DOMContentLoaded