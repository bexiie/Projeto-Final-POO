package Model;
import java.util.Date;

public class ResumoVenda {
    private Date dataVenda;
    private int formaPag;
    private float valorFormaPag;

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(int formaPag) {
        this.formaPag = formaPag;
    }

    public float getValorFormaPag() {
        return valorFormaPag;
    }

    public void setValorFormaPag(float valorFormaPag) {
        this.valorFormaPag = valorFormaPag;
    }
    
    // Adicione este método dentro da classe ResumoVenda

public String getFormaPagStr() {
    return switch (this.getFormaPag()) {
        case 1 -> "Dinheiro";
        case 2 -> "PIX";
        case 3 -> "Cartão de Débito";
        case 4 -> "Cartão de Crédito";
        default -> "Desconhecido";
    };
}
    
}
