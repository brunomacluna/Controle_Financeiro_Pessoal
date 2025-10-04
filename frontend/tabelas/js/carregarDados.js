// carregarDados.js
import { corpoTabelaCustos, corpoTabelaRenda } from './tabelas.js';

fetch('/buscar-dados')
    .then(response => {
        if (!response.ok) throw new Error("Erro ao buscar dados do usuário");
        return response.json();
    })
    .then(data => {
        const { custos, renda, totalCustos, totalRenda, diferencaFinal } = data;

        corpoTabelaCustos.innerHTML = '';
        for (let item of custos) {
            let linha = document.createElement("tr");
            linha.innerHTML = `
                <td><input type="text" name="descricao" value="${item.descricao}"></td>
                <td><input type="number" name="valor" value="${item.valor}"></td>
                <td><button class="botao-deletar">Deletar</button></td>
            `;
            corpoTabelaCustos.appendChild(linha);
        }

        corpoTabelaRenda.innerHTML = '';
        for (let item of renda) {
            let linha = document.createElement("tr");
            linha.innerHTML = `
                <td><input type="text" name="descricao" value="${item.descricao}"></td>
                <td><input type="number" name="valor" value="${item.valor}"></td>
                <td><button class="botao-deletar">Deletar</button></td>
            `;
            corpoTabelaRenda.appendChild(linha);
        }

        document.getElementById("total-custo").textContent = totalCustos.toFixed(2);
        document.getElementById("total-renda").textContent = totalRenda.toFixed(2);

        let resultadoElemento = document.getElementById("valor-total");
        resultadoElemento.textContent = diferencaFinal.toFixed(2);
        resultadoElemento.style.color = diferencaFinal >= 0 ? "green" : "red";
    })
    .catch(error => {
        console.error("Erro ao carregar dados:", error);
        alert("Não foi possível carregar os dados salvos.");
    });
