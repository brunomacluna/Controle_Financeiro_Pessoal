document.getElementById("form-login").addEventListener("submit", function(event) {
    event.preventDefault();

    const login = event.target.login.value;
    const senha = event.target.senha.value;

    fetch ('/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ login, senha })
    })
    .then(response => {
        if(!response.ok){
            throw new Error("Login inválido");
        }
        return response.json();
    })
    .then(data =>{
        //redireciona para a tela principal
        window.location.href = "/tabelas/index.html";
    })
    .catch(error =>{
        alert(error.message);
    });
});

document.getElementById("btn-cadastro").addEventListener("click", function() {
    window.location.href = "./cadastro.html";
});

