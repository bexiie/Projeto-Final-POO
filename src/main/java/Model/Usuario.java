package Model;

public class Usuario {
    private int id;
    private String nomeDeLogin;
    private String senha;
    private String tipoDeUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDeLogin() {
        return nomeDeLogin;
    }

    public void setNomeDeLogin(String nomeDeLogin) {
        this.nomeDeLogin = nomeDeLogin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(String tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }
    
    
}

