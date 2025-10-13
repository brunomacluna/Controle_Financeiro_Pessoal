// validacoes.js
export function sanitizarTexto(texto) {
    return texto.replace(/<[^>]*>?/gm, '').trim();
}
