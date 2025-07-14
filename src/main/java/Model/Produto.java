package Model;
import java.util.Date;

public class Produto {
    private int id;
    private String descricao;
    private String codigoBarras;
    private String unidadeVenda;
    private Date ultimaCompra;
    private float precoVenda;
    private float quantEstoque;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getUnidadeVenda() {
        return unidadeVenda;
    }

    public void setUnidadeVenda(String unidadeVenda) {
        this.unidadeVenda = unidadeVenda;
    }

    public Date getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(Date ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public float getQuantEstoque() {
        return quantEstoque;
    }

    public void setQuantEstoque(float quantEstoque) {
        this.quantEstoque = quantEstoque;
    }
    
    @Override
    public String toString() {
        return this.getDescricao(); // Retorna a descrição do produto
    }  
    
}
