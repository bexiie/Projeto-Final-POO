package Model;

public class ItemVenda {
    private int id;
    private Venda idVenda;
    private Produto produto;
    private int quantVendida;
    private float precoVenda;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venda getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Venda idVenda) {
        this.idVenda = idVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantVendida() {
        return quantVendida;
    }

    public void setQuantVendida(int quantVendida) {
        this.quantVendida = quantVendida;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    // Adicione este método dentro da classe ItemVenda
    public float getSubtotal() {
        return this.getQuantVendida() * this.getPrecoVenda();
}
    
    
}
