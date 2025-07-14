package View;

import DAO.ClienteDAO;
import Model.Cliente;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; // <<< --- IMPORT QUE ESTAVA FALTANDO
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioCliente {

    private TextField txtNome, txtCpf, txtEndereco, txtCidade, txtCep, txtUf;
    private Cliente clienteAtual;
    private TelaGerenciarClientes telaPai;

    public FormularioCliente(TelaGerenciarClientes telaPai) {
        this.telaPai = telaPai;
        this.clienteAtual = null;
    }

    public FormularioCliente(TelaGerenciarClientes telaPai, Cliente cliente) {
        this.telaPai = telaPai;
        this.clienteAtual = cliente;
    }

    public void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(clienteAtual == null ? "Novo Cliente" : "Editar Cliente");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Campos do formulário
        grid.add(new Label("Nome:"), 0, 0);
        txtNome = new TextField();
        grid.add(txtNome, 1, 0);
        
        grid.add(new Label("CPF/CNPJ:"), 0, 1);
        txtCpf = new TextField();
        grid.add(txtCpf, 1, 1);

        grid.add(new Label("Endereço:"), 0, 2);
        txtEndereco = new TextField();
        grid.add(txtEndereco, 1, 2);

        grid.add(new Label("Cidade:"), 0, 3);
        txtCidade = new TextField();
        grid.add(txtCidade, 1, 3);
        
        grid.add(new Label("CEP:"), 0, 4);
        txtCep = new TextField();
        grid.add(txtCep, 1, 4);

        grid.add(new Label("UF:"), 0, 5);
        txtUf = new TextField();
        grid.add(txtUf, 1, 5);

        // Preenche os campos se estiver editando
        if (clienteAtual != null) {
            txtNome.setText(clienteAtual.getNome());
            txtCpf.setText(clienteAtual.getCpf());
            txtEndereco.setText(clienteAtual.getEndereco());
            txtCidade.setText(clienteAtual.getCidade());
            txtCep.setText(clienteAtual.getCep());
            txtUf.setText(clienteAtual.getUF());
        }

        // Botões
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> salvarCliente(stage));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        // HBox para os botões
        HBox hboxBotoes = new HBox(10, btnSalvar, btnCancelar);

        VBox layout = new VBox(10, grid, hboxBotoes);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void salvarCliente(Stage stage) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            
            if (clienteAtual == null) { // Modo "Novo"
                Cliente novoCliente = new Cliente();
                novoCliente.setNome(txtNome.getText());
                novoCliente.setCpf(txtCpf.getText());
                novoCliente.setEndereco(txtEndereco.getText());
                novoCliente.setCidade(txtCidade.getText());
                novoCliente.setCep(txtCep.getText());
                novoCliente.setUF(txtUf.getText());
                clienteDAO.adiciona(novoCliente);
            } else { // Modo "Editar"
                clienteAtual.setNome(txtNome.getText());
                clienteAtual.setCpf(txtCpf.getText());
                clienteAtual.setEndereco(txtEndereco.getText());
                clienteAtual.setCidade(txtCidade.getText());
                clienteAtual.setCep(txtCep.getText());
                clienteAtual.setUF(txtUf.getText());
                clienteDAO.altera(clienteAtual);
            }
            
            telaPai.atualizarTabela();
            stage.close();

        } catch (RuntimeException e) {
             mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
        }
    }
    
    private void mostrarAlerta(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}