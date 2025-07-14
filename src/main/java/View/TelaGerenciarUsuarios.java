package View;

import DAO.UsuarioDAO;
import Model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class TelaGerenciarUsuarios {

    private TableView<Usuario> tableView = new TableView<>();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Usuários");

        // Colunas
        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Usuario, String> colLogin = new TableColumn<>("Login");
        colLogin.setCellValueFactory(new PropertyValueFactory<>("nomeDeLogin"));
        colLogin.setPrefWidth(200);
        TableColumn<Usuario, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoDeUsuario"));
        colTipo.setPrefWidth(150);
        
        tableView.getColumns().addAll(colId, colLogin, colTipo);
        atualizarTabela();

        // Botões
        Button btnNovo = new Button("Novo");
        Button btnEditar = new Button("Editar");
        Button btnRemover = new Button("Remover");
        Button btnVoltar = new Button("Voltar"); // Botão Renomeado

        HBox hboxBotoes = new HBox(10, btnNovo, btnEditar, btnRemover, btnVoltar);
        hboxBotoes.setPadding(new Insets(10));

        // Ações dos botões
        btnNovo.setOnAction(e -> new FormularioUsuario(this).show());
        btnEditar.setOnAction(e -> {
            Usuario usuarioSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (usuarioSelecionado != null) {
                new FormularioUsuario(this, usuarioSelecionado).show();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Selecione um usuário para editar.");
            }
        });
        btnRemover.setOnAction(e -> {
             Usuario usuarioSelecionado = tableView.getSelectionModel().getSelectedItem();
             if (usuarioSelecionado != null) {
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remover o usuário '" + usuarioSelecionado.getNomeDeLogin() + "'?", ButtonType.YES, ButtonType.NO);
                 alert.showAndWait().ifPresent(response -> {
                     if (response == ButtonType.YES) {
                         try {
                             new UsuarioDAO().remove(usuarioSelecionado);
                             atualizarTabela();
                         } catch (RuntimeException ex) {
                             mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", ex.getMessage());
                         }
                     }
                 });
             } else {
                 mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Selecione um usuário para remover.");
             }
        });
        btnVoltar.setOnAction(e -> stage.close()); // Ação do botão "Voltar"

        // Layout
        VBox vbox = new VBox(10, new Label("Usuários Cadastrados"), tableView, hboxBotoes);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizarTabela() {
        try {
            List<Usuario> usuariosList = new UsuarioDAO().getLista();
            ObservableList<Usuario> observableList = FXCollections.observableArrayList(usuariosList);
            tableView.setItems(observableList);
        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Banco de Dados", e.getMessage());
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