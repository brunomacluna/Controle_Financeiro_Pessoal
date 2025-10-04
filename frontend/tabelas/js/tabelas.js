// tabelas.js
export let corpoTabelaCustos = document.getElementById("corpo-tabela-custos");
export let linhaTotalCustos = document.getElementById("linha-total-custos");
export let botaoAdicionarLinhaCustos = document.getElementById("adicionar-linha-custos");

export function adicionarLinhaTabelaCustos(event) {
    event.preventDefault();
    let novaLinha = document.createElement("tr");
    novaLinha.innerHTML = `
        <td><input type="text" name="descricao" placeholder="Escreva aqui"></td>
        <td><input type="number" name="valor" placeholder="Valor"></td>
        <td><button class="botao-deletar">Deletar</button></td>
    `;
    corpoTabelaCustos.appendChild(novaLinha, linhaTotalCustos);
}
botaoAdicionarLinhaCustos.addEventListener('click', adicionarLinhaTabelaCustos);

export let corpoTabelaRenda = document.getElementById("corpo-tabela-renda");
export let linhaTotalRenda = document.getElementById("linha-total-renda");
export let botaoAdicionarLinhaRenda = document.getElementById("adicionar-linha-renda");

export function adicionarLinhaTabelaRenda(event) {
    event.preventDefault();
    let novaLinha2 = document.createElement("tr");
    novaLinha2.innerHTML = `
        <td><input type="text" name="descricao" placeholder="Escreva aqui"></td>
        <td><input type="number" name="valor" placeholder="Valor"></td>
        <td><button class="botao-deletar">Deletar</button></td>
    `;
    corpoTabelaRenda.appendChild(novaLinha2, linhaTotalRenda);
}
botaoAdicionarLinhaRenda.addEventListener('click', adicionarLinhaTabelaRenda);

document.addEventListener('click', function(event) {
    if (event.target.classList.contains('botao-deletar')) {
        event.preventDefault();
        let linha = event.target.closest('tr');
        if (linha) {
            linha.remove();
        }
    }
});
