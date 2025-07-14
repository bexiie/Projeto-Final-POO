package View;

import DAO.ProdutoDAO;
import Model.Produto;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Date;
// A linha de import inválida foi removida.

public class FormularioProduto {

    private TextField txtDescricao, txtCodigoBarras, txtUnidadeVenda, txtPrecoVenda, txtQuantEstoque;
    private DatePicker datePickerUltimaCompra;
    private Produto produtoAtual;
    private TelaGerenciarProdutos telaPai;

    public FormularioProduto(TelaGerenciarProdutos telaPai) {
        this.telaPai = telaPai;
        this.produtoAtual = null;
    }

    public FormularioProduto(TelaGerenciarProdutos telaPai, Produto produto) {
        this.telaPai = telaPai;
        this.produtoAtual = produto;
    }

    public void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(produtoAtual == null ? "Novo Produto" : "Editar Produto");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(new Label("Descrição:"), 0, 0);
        txtDescricao = new TextField();
        grid.add(txtDescricao, 1, 0);

        grid.add(new Label("Código de Barras:"), 0, 1);
        txtCodigoBarras = new TextField();
        grid.add(txtCodigoBarras, 1, 1);

        grid.add(new Label("Unidade de Venda:"), 0, 2);
        txtUnidadeVenda = new TextField();
        grid.add(txtUnidadeVenda, 1, 2);

        grid.add(new Label("Preço de Venda:"), 0, 3);
        txtPrecoVenda = new TextField();
        grid.add(txtPrecoVenda, 1, 3);

        grid.add(new Label("Qtd. em Estoque:"), 0, 4);
        txtQuantEstoque = new TextField();
        grid.add(txtQuantEstoque, 1, 4);

        grid.add(new Label("Última Compra:"), 0, 5);
        datePickerUltimaCompra = new DatePicker();
        grid.add(datePickerUltimaCompra, 1, 5);

        if (produtoAtual != null) {
            txtDescricao.setText(produtoAtual.getDescricao());
            txtCodigoBarras.setText(produtoAtual.getCodigoBarras());
            txtUnidadeVenda.setText(produtoAtual.getUnidadeVenda());
            txtPrecoVenda.setText(String.valueOf(produtoAtual.getPrecoVenda()));
            txtQuantEstoque.setText(String.valueOf(produtoAtual.getQuantEstoque()));
            
            if (produtoAtual.getUltimaCompra() != null) {
                // --- CORREÇÃO APLICADA AQUI ---
                // Usando o nome completo da classe java.sql.Date para a conversão
                java.sql.Date sqlDate = new java.sql.Date(produtoAtual.getUltimaCompra().getTime());
                datePickerUltimaCompra.setValue(sqlDate.toLocalDate());
            }
        }

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> salvarProduto(stage));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        VBox layout = new VBox(10, grid, btnSalvar, btnCancelar);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 350);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void salvarProduto(Stage stage) {
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            
            Date ultimaCompra = Date.from(datePickerUltimaCompra.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (produtoAtual == null) { // Novo
                Produto novoProduto = new Produto();
                novoProduto.setDescricao(txtDescricao.getText());
                novoProduto.setCodigoBarras(txtCodigoBarras.getText());
                novoProduto.setUnidadeVenda(txtUnidadeVenda.getText());
                novoProduto.setPrecoVenda(Float.parseFloat(txtPrecoVenda.getText()));
                novoProduto.setQuantEstoque(Float.parseFloat(txtQuantEstoque.getText()));
                novoProduto.setUltimaCompra(ultimaCompra);
                produtoDAO.adiciona(novoProduto);
            } else { // Edição
                produtoAtual.setDescricao(txtDescricao.getText());
                produtoAtual.setCodigoBarras(txtCodigoBarras.getText());
                produtoAtual.setUnidadeVenda(txtUnidadeVenda.getText());
                produtoAtual.setPrecoVenda(Float.parseFloat(txtPrecoVenda.getText()));
                produtoAtual.setQuantEstoque(Float.parseFloat(txtQuantEstoque.getText()));
                produtoAtual.setUltimaCompra(ultimaCompra);
                produtoDAO.altera(produtoAtual);
            }

            telaPai.atualizarTabela();
            stage.close();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Preço e Estoque devem ser números válidos.");
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