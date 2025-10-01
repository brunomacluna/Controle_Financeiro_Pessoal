document.getElementById("botao-voltar").addEventListener("click", function() {
    window.location.href = "./login.html";
});

document.getElementById("form-cadastro").addEventListener("submit", function(event){
    event.preventDefault();

    const login = event.target.cadastro.value;
    const senha = event.target.senha.value;

    // Validação do login: só letras e números
    const loginValido = /^[a-zA-Z0-9]+$/.test(login);

    // Validação da senha: exatamente 6 dígitos numéricos
    const senhaValida = /^\d{6}$/.test(senha);

    if (!loginValido) {
        alert("O login deve conter apenas letras e números, sem espaços ou símbolos.");
        event.target.reset();
        return;
    }

    if (!senhaValida) {
        alert("A senha deve conter exatamente 6 números.");
        event.target.reset();
        return;
    }

    fetch("/cadastro", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ login, senha })
    })
    .then(response =>{
        if(!response.ok){
            throw new Error("Erro ao cadastrar");
        }
        return response.json();
    })
    .then(data =>{
        alert("Cadastro realizado com sucesso!");
        window.location.href = "./login.html";
    })
    .catch(error =>{
        alert(erro.message);
    })
})