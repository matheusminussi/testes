<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Clientes</title>
    <link rel="stylesheet" href="estilo.css" >

</head>
<body>
<%@ include file="WEB-INF/menu.jsp" %>
<div class="conteudo">
    <h1>Relatorio das ordens de serviço</h1>
<table>
    <thead>
        <tr>
            <th>Id da Os</th>
            <th>Cliente</th>
            <th>Aparelho</th>
            <th>Observação</th>
            <th>data de entrada</th>
            <th>Data de saida</th>
            <th> Serviços </th>
            <th>Deletar</th>
            <th>Editar</th>
        </tr>
    </thead>
    <tbody>

    <c:forEach var="os" items="${requestScope.os}">
    <tr>
        <td><c:out value="${os.id}" /></td>
        <td><c:out value="${os.cliente.nome}" /></td>
        <td><c:out value="${os.aparelho.nome}" /></td>
        <td><c:out value="${os.observacao}" /></td>
        <td><c:out value="${os.dataEntradaFormatada}" /></td>

        <td>

            <c:choose>
                <c:when test="${empty os.dataSaida}">
                    <form action="finalizaOs" method="post">
                        <input type="hidden" name="id" value="${os.id}">
                        <input type="submit" value="Finalizar O.S">
                    </form>
                </c:when>
                <c:otherwise>
                   ${os.dataSaidaFormatada}
                </c:otherwise>
            </c:choose>

        </td>
        <td>
            <form action="buscarServicoDaOs" method="post">
                <input type="hidden" name="id" value="${os.id}">
                <input type="submit" value="Mostrar Servicos">
            </form>
        </td>

        <td>
            <form action="deletar" method="post">
                <input type="hidden" name="id" value="${os.id}">
                <input type="hidden" name="tipo" value="os">
                <input type="submit" value="Deletar">
            </form>
        </td>
        <td>
            <form action="buscar" method="post">
                <input type="hidden" name="id" value="${os.id}">
                <input type="hidden" name="tipo" value="os">
                <input type="submit" value="Editar">
            </form>
        </td>

    </tr>
    </c:forEach>


    </tbody>
</table>
</div>
</body>
</html>
