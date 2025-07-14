package View;

import DAO.ClienteDAO;
import Model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

public class TelaSelecaoCliente {

    private Cliente clienteSelecionado = null;

    public Optional<Cliente> showAndWait() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Selecione um Cliente");

        // Tabela
        TableView<Cliente> tableView = new TableView<>();
        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(300);

        TableColumn<Cliente, String> colCpf = new TableColumn<>("CPF/CNPJ");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setPrefWidth(150);

        tableView.getColumns().addAll(colId, colNome, colCpf);

        // Carregar dados
        try {
            List<Cliente> clientesList = new ClienteDAO().getLista();
            ObservableList<Cliente> observableList = FXCollections.observableArrayList(clientesList);
            tableView.setItems(observableList);
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar clientes: " + e.getMessage()).showAndWait();
        }

        // Botões
        Button btnSelecionar = new Button("Selecionar");
        Button btnCancelar = new Button("Cancelar");

        btnSelecionar.setOnAction(e -> {
            clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Nenhum cliente selecionado.").showAndWait();
            }
        });

        btnCancelar.setOnAction(e -> {
            clienteSelecionado = null;
            stage.close();
        });

        HBox hboxBotoes = new HBox(10, btnSelecionar, btnCancelar);
        hboxBotoes.setAlignment(Pos.CENTER_RIGHT);
        
        VBox vbox = new VBox(10, tableView, hboxBotoes);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.showAndWait(); // Pausa a execução até esta janela ser fechada

        return Optional.ofNullable(clienteSelecionado);
    }
}