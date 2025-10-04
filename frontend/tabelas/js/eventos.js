// eventos.js
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
