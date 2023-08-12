
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    public ProdutosDAO() {
        this.conexao = new conectaDAO();
        this.conn = this.conexao.connectDB();
    }
    conectaDAO conexao;
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos(nome, valor, status) VALUES" + "(?,?,?)";
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.execute();

            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir empresa: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro.");
        }
    }

    public List<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        try {
            prep = this.conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            List<ProdutosDTO> lista = new ArrayList<>();
            while (resultset.next()) {
                ProdutosDTO produtos = new ProdutosDTO();
                produtos.setId(resultset.getInt("id"));
                produtos.setNome(resultset.getString("nome"));
                produtos.setValor(resultset.getInt("valor"));
                produtos.setStatus(resultset.getString("status"));

                lista.add(produtos);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
            return null;
        }

    }

    public void venderProduto(ProdutosDTO produto) {
        String sql = "UPDATE produtos SET status=? WHERE id=?";
        try {
            prep = this.conn.prepareStatement(sql);
            prep.setString(1, produto.getStatus());
            prep.setInt(2, produto.getId());
            prep.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar venda: " + e.getMessage());
        }
    }
    
    public List<ProdutosDTO> listarProdutosVendidos(){
        String sql = "SELECT * FROM produtos WHERE status='Vendido'";
         try{
             prep = this.conn.prepareStatement(sql);
             resultset = prep.executeQuery();
             List <ProdutosDTO> lista = new ArrayList<>();
             while (resultset.next()) {
                 ProdutosDTO produtos = new ProdutosDTO ();
                 produtos.setId(resultset.getInt("id"));
                 produtos.setNome(resultset.getString("nome"));
                 produtos.setValor(resultset.getInt("valor"));
                 produtos.setStatus(resultset.getString("status"));
                 
                 lista.add(produtos);
             }
             return lista;
         }
         catch (SQLException e){
              System.out.println("Erro ao listar produtos: " + e.getMessage());
              return null;
         }
}

}
