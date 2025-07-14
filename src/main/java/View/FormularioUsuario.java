/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import DAO.UsuarioDAO;
import Model.Usuario;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FormularioUsuario {

    private TextField txtLogin;
    private PasswordField txtSenha;
    private ComboBox<String> comboTipoUsuario;
    private Usuario usuarioAtual;
    private TelaGerenciarUsuarios telaPai;

    public FormularioUsuario(TelaGerenciarUsuarios telaPai) {
        this.telaPai = telaPai;
        this.usuarioAtual = null;
    }

    public FormularioUsuario(TelaGerenciarUsuarios telaPai, Usuario usuario) {
        this.telaPai = telaPai;
        this.usuarioAtual = usuario;
    }

    public void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(usuarioAtual == null ? "Novo Usuário" : "Editar Usuário");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Campos do formulário
        grid.add(new Label("Login:"), 0, 0);
        txtLogin = new TextField();
        grid.add(txtLogin, 1, 0);

        grid.add(new Label("Senha:"), 0, 1);
        txtSenha = new PasswordField();
        grid.add(txtSenha, 1, 1);

        grid.add(new Label("Tipo de Usuário:"), 0, 2);
        comboTipoUsuario = new ComboBox<>(FXCollections.observableArrayList("administrador", "caixa"));
        grid.add(comboTipoUsuario, 1, 2);

        // Preenche os campos se estiver editando
        if (usuarioAtual != null) {
            txtLogin.setText(usuarioAtual.getNomeDeLogin());
            // A senha não é preenchida por segurança. O admin deve digitar uma nova senha se quiser alterar.
            comboTipoUsuario.setValue(usuarioAtual.getTipoDeUsuario());
        }

        // Botões
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> salvarUsuario(stage));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        VBox layout = new VBox(10, grid, btnSalvar, btnCancelar);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 250);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void salvarUsuario(Stage stage) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            if (txtLogin.getText().isEmpty() || txtSenha.getText().isEmpty() || comboTipoUsuario.getValue() == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Vazios", "Todos os campos são obrigatórios.");
                return;
            }

            if (usuarioAtual == null) { // Novo
                Usuario novoUsuario = new Usuario();
                novoUsuario.setNomeDeLogin(txtLogin.getText());
                novoUsuario.setSenha(txtSenha.getText());
                novoUsuario.setTipoDeUsuario(comboTipoUsuario.getValue());
                usuarioDAO.adiciona(novoUsuario);
            } else { // Edição
                usuarioAtual.setNomeDeLogin(txtLogin.getText());
                usuarioAtual.setSenha(txtSenha.getText());
                usuarioAtual.setTipoDeUsuario(comboTipoUsuario.getValue());
                usuarioDAO.altera(usuarioAtual);
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