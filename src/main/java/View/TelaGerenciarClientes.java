package View;

import DAO.ClienteDAO;
import Model.Cliente;
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

public class TelaGerenciarClientes {
    private TableView<Cliente> tableView = new TableView<>();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Clientes");

        // Colunas da tabela
        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(200);
        TableColumn<Cliente, String> colCpf = new TableColumn<>("CPF/CNPJ");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setPrefWidth(120);
        TableColumn<Cliente, String> colEndereco = new TableColumn<>("Endereço");
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colEndereco.setPrefWidth(250);

        tableView.getColumns().addAll(colId, colNome, colCpf, colEndereco);
        atualizarTabela();

        // Botões de Ação
        Button btnNovo = new Button("Novo");
        Button btnEditar = new Button("Editar");
        Button btnRemover = new Button("Remover");
        Button btnVoltar = new Button("Voltar"); // Botão Renomeado

        HBox hboxBotoes = new HBox(10, btnNovo, btnEditar, btnRemover, btnVoltar);
        hboxBotoes.setPadding(new Insets(10));

        // Ações dos Botões
        btnNovo.setOnAction(e -> new FormularioCliente(this).show());
        btnEditar.setOnAction(e -> {
            Cliente clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                new FormularioCliente(this, clienteSelecionado).show();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Cliente Selecionado", "Por favor, selecione um cliente na tabela para editar.");
            }
        });
        btnRemover.setOnAction(e -> {
            Cliente clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja remover o cliente " + clienteSelecionado.getNome() + "?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        try {
                            new ClienteDAO().remove(clienteSelecionado);
                            atualizarTabela();
                        } catch (RuntimeException ex) {
                            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", "Não foi possível remover o cliente: " + ex.getMessage());
                        }
                    }
                });
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Cliente Selecionado", "Por favor, selecione um cliente na tabela para remover.");
            }
        });
        btnVoltar.setOnAction(e -> stage.close()); // Ação do botão "Voltar"

        VBox vbox = new VBox(10, new Label("Clientes Cadastrados"), tableView, hboxBotoes);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizarTabela() {
        try {
            ObservableList<Cliente> observableList = FXCollections.observableArrayList(new ClienteDAO().getLista());
            tableView.setItems(observableList);
        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Banco de Dados", "Não foi possível carregar os clientes: " + e.getMessage());
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