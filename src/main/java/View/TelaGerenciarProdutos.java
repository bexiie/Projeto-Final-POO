package View;

import DAO.ProdutoDAO;
import Model.Produto;
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

public class TelaGerenciarProdutos {

    private TableView<Produto> tableView = new TableView<>();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Gerenciamento de Produtos");

        // Colunas
        TableColumn<Produto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Produto, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDescricao.setPrefWidth(250);
        TableColumn<Produto, Float> colPreco = new TableColumn<>("Preço Venda");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        TableColumn<Produto, Float> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantEstoque"));
        TableColumn<Produto, String> colUnidade = new TableColumn<>("Unidade");
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("unidadeVenda"));
        
        tableView.getColumns().addAll(colId, colDescricao, colPreco, colEstoque, colUnidade);
        atualizarTabela();

        // Botões
        Button btnNovo = new Button("Novo");
        Button btnEditar = new Button("Editar");
        Button btnRemover = new Button("Remover");
        Button btnVoltar = new Button("Voltar"); // Botão Renomeado

        HBox hboxBotoes = new HBox(10, btnNovo, btnEditar, btnRemover, btnVoltar);
        hboxBotoes.setPadding(new Insets(10));

        // Ações dos botões
        btnNovo.setOnAction(e -> new FormularioProduto(this).show());
        btnEditar.setOnAction(e -> {
            Produto produtoSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (produtoSelecionado != null) {
                new FormularioProduto(this, produtoSelecionado).show();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Selecione um produto para editar.");
            }
        });
        btnRemover.setOnAction(e -> {
             Produto produtoSelecionado = tableView.getSelectionModel().getSelectedItem();
             if (produtoSelecionado != null) {
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remover '" + produtoSelecionado.getDescricao() + "'?", ButtonType.YES, ButtonType.NO);
                 alert.showAndWait().ifPresent(response -> {
                     if (response == ButtonType.YES) {
                         try {
                             new ProdutoDAO().remove(produtoSelecionado);
                             atualizarTabela();
                         } catch (RuntimeException ex) {
                             mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", ex.getMessage());
                         }
                     }
                 });
             } else {
                 mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Selecione um produto para remover.");
             }
        });
        btnVoltar.setOnAction(e -> stage.close()); // Ação do botão "Voltar"

        // Layout
        VBox vbox = new VBox(10, new Label("Produtos Cadastrados"), tableView, hboxBotoes);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void atualizarTabela() {
        try {
            List<Produto> produtosList = new ProdutoDAO().getLista();
            ObservableList<Produto> observableList = FXCollections.observableArrayList(produtosList);
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