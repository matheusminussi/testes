package com.example.demo2.DAO;

import com.example.demo2.Modelo.Aparelho;
import com.example.demo2.Modelo.Servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SevicoDaoClasse implements ServicoDaoInterface{
    private Connection con;
    public SevicoDaoClasse() throws ErroDao{
        con=FabricaConexao.pegaConexao();
    }
    @Override
    public void inserir(Servico s) throws ErroDao {
        try {
            PreparedStatement stm=con.prepareStatement
                    ("insert into servico(nome,descricao,valor) values (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1,s.getNome());
            stm.setString(2,s.getDescricao());
            stm.setDouble(3,s.getValor());
            stm.executeUpdate();
            ResultSet rs=stm.getGeneratedKeys();
            if(rs.next())
            {
                s.setId(rs.getInt(1));
            }
            stm.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void deletar(Servico s) throws ErroDao {
        deletar(s.getId());

    }

    @Override
    public void deletar(int id) throws ErroDao {
        try {
            PreparedStatement stm=con.prepareStatement
                    ("delete from servico where id=?");
            stm.setInt(1,id);
            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public void editar(Servico s) throws ErroDao {
        try {
            PreparedStatement stm=con.prepareStatement
                    ("update servico set nome=?, descircao=?, valor=? where id=?");
            stm.setString(1,s.getNome());
            stm.setString(2,s.getDescricao());
            stm.setDouble(3,s.getValor());
            stm.setInt(5,s.getId());
            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public Servico buscar(int id, String nome) throws ErroDao {
        try {
            PreparedStatement stm=con.prepareStatement
                    ("select * from servico where id=? and nome=?");
            stm.setInt(1,id);
            stm.setString(2,nome);
            ResultSet rs= stm.executeQuery();
            if(rs.next()){
                Servico s=new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setValor(rs.getDouble("valor"));
                return s;
            }
            else
                return null;

        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public Set<Servico> buscar() throws ErroDao {
        try {
            Set<Servico> servicos=new HashSet();
            PreparedStatement stm=con.prepareStatement
                    ("select * from servico");
            ResultSet rs= stm.executeQuery();
            while (rs.next()){
                Servico s=new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setValor(rs.getDouble("valor"));
                servicos.add(s);
            }
            return servicos;
        } catch (SQLException e) {
            throw new ErroDao(e);
        }    }

    @Override
    public void sair() throws ErroDao {
        try {
            con.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }
}
