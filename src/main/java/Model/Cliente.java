package Model;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String cidade;
    private String cep;
    private String UF;
    
    public void setId (int id){
        this.id = id;
    }
    public int getId () {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
     public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
     public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }   
    
    public String getUF() {
        return UF;
    }

    public void setUF(String uf) {
        this.UF = uf;
    }
    
}
