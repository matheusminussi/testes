package com.example.demo2.Controle;

import com.example.demo2.DAO.*;
import com.example.demo2.Modelo.Cliente;
import com.example.demo2.Modelo.Funcionario;
import com.example.demo2.Utils.Validador;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "cadastrarCliente", value = "/cadastrarCliente")
public class CadastrarCliente extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext aplicacao=getServletContext();
        request.setCharacterEncoding("utf-8");
        //pegar os dados
        String nome=request.getParameter("nome");
        String telefone=request.getParameter("telefone");
        String endereco=request.getParameter("endereco");

        HttpSession sessao=request.getSession();
        Funcionario funcionarioLogado=(Funcionario) sessao.getAttribute("funcionario");
        if(funcionarioLogado!=null) {
        //se os dados existirem
        if(Validador.temValor(nome)&&Validador.temValor(telefone)&&Validador.temValor(endereco)) {

            Cliente c=new Cliente(nome,telefone,endereco);
            try {
                ClienteDaoInterface dao=new ClienteDaoClasse();
                dao.inserir(c);
                dao.sair();
                //envia para o relatorio com a mensagem de sucesso
                response.sendRedirect("home.jsp?mensagem=cadastradocomsucesso");
            } catch (ErroDao e) {
                response.sendRedirect("home.jsp?mensagem=erroaotentarcadastrar");
            }


        }
        else //senão
        {
            //envia para o home com a mensagem de erro
            response.sendRedirect("home.jsp?mensagem=faltadados");
        }
        }else
            response.sendRedirect("login.jsp?mensagem=naoestavalogado");
    }
}
 