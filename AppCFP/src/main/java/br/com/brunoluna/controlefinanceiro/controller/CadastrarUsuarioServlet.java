package br.com.brunoluna.controlefinanceiro.controller;

import br.com.brunoluna.controlefinanceiro.dao.UsuarioDAO;
import br.com.brunoluna.controlefinanceiro.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cadastro")
public class CadastrarUsuarioServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(CadastrarUsuarioServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse  response) throws ServletException, IOException {

        LOG.info("=== ROTEAMENTO /cadastro FUNCIONOU! ===");

        response.setContentType("application/json;charset=UTF-8");

        try {
            //ler o json do front
            StringBuilder stringBuilder = new StringBuilder();
            String linha;
            while ((linha = request.getReader().readLine()) != null) {
                stringBuilder.append(linha);
            }//while

            String json = stringBuilder.toString();
            LOG.info("JSON recebido: {}", json); // para debug

            // converter json para objeto
            Map<String, String> dados = objectMapper.readValue(json, Map.class);
            String login = dados.get("login");
            String senha = dados.get("senha");

            // criar e salvar usuário
            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSenha(senha);

            Usuario usuarioSalvo = usuarioDAO.cadastrarUsuario(usuario);

            //responder sucesso
            response.setStatus(HttpServletResponse.SC_CREATED);
            String jsonResposta = "{\"sucesso\": true, \"mensagem\": \"Usuário cadastrada com ID: "+ usuarioSalvo.getId()+ "\"}";
            response.getWriter().write(jsonResposta);
        }//try
        catch (IllegalArgumentException e) {
            //login duplicado
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String jsonErro = "{\"sucesso\": false, \"mensagem\": \""+ e.getMessage() + "\"}";
            response.getWriter().write(jsonErro);
        }//catch
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String jsonErro = "{\"sucesso\": false, \"mensagem\": \"Erro interno no servidor\"}";
            response.getWriter().write(jsonErro);
            e.printStackTrace();
        }//catch
    }//doPost


}//CadastrarUsuarioServlet
