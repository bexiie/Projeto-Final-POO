package View;

import DAO.ItemVendaDAO;
import DAO.ProdutoDAO;
import DAO.VendaDAO;
import Model.Cliente;
import Model.ItemVenda;
import Model.Produto;
import Model.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Date;

public class TelaVendas {

    private ObservableList<ItemVenda> itensVenda = FXCollections.observableArrayList();
    private Label lblTotal;
    private double totalVenda = 0.0;
    private Cliente clienteAtual = null;
    private Label lblClienteInfo;
    private TableView<ItemVenda> tableView;

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Formulário de Vendas");

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        // --- Seção Superior: Cliente e Logout ---
        lblClienteInfo = new Label("Cliente: Consumidor Padrão");
        Button btnSelecionarCliente = new Button("Selecionar Cliente");
        btnSelecionarCliente.setOnAction(e -> selecionarCliente());
        
        Button btnSair = new Button("Sair (Logout)"); // BOTÃO NOVO
        btnSair.setOnAction(e -> {
            stage.close();
            new TelaLogin().show();
        });

        HBox hboxCliente = new HBox(10, lblClienteInfo, btnSelecionarCliente);
        HBox hboxSair = new HBox(btnSair);
        hboxSair.setAlignment(Pos.CENTER_RIGHT);
        
        BorderPane topPane = new BorderPane();
        topPane.setLeft(hboxCliente);
        topPane.setRight(hboxSair);
        borderPane.setTop(topPane);

        // --- Seção Esquerda: Adicionar Produto ---
        TextField txtCodProduto = new TextField();
        TextField txtQuantidade = new TextField("1");
        Button btnAdicionarProduto = new Button("Adicionar Produto");
        btnAdicionarProduto.setPrefWidth(150);
        btnAdicionarProduto.setOnAction(e -> adicionarProduto(txtCodProduto.getText(), txtQuantidade.getText(), txtCodProduto));
        
        GridPane gridAdicionar = new GridPane();
        gridAdicionar.setVgap(10);
        gridAdicionar.setHgap(10);
        gridAdicionar.add(new Label("Cód. Produto:"), 0, 0);
        gridAdicionar.add(txtCodProduto, 1, 0);
        gridAdicionar.add(new Label("Quantidade:"), 0, 1);
        gridAdicionar.add(txtQuantidade, 1, 1);
        VBox vboxAdicionar = new VBox(10, new Label("Adicionar Item"), gridAdicionar, btnAdicionarProduto);
        vboxAdicionar.setPadding(new Insets(10));
        borderPane.setLeft(vboxAdicionar);

        // --- Seção Central: Itens da Venda (Tabela) ---
        tableView = new TableView<>(itensVenda);
        TableColumn<ItemVenda, String> colProduto = new TableColumn<>("Produto");
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colProduto.setPrefWidth(250);
        TableColumn<ItemVenda, Integer> colQtd = new TableColumn<>("Qtd");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantVendida"));
        TableColumn<ItemVenda, Float> colPrecoUnit = new TableColumn<>("Preço Unit.");
        colPrecoUnit.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        TableColumn<ItemVenda, Float> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tableView.getColumns().addAll(colProduto, colQtd, colPrecoUnit, colSubtotal);
        borderPane.setCenter(new VBox(10, new Label("Itens da Venda"), tableView));

        // --- Seção Inferior: Total e Botões de Ação ---
        VBox vboxFinalizar = new VBox(10);
        vboxFinalizar.setPadding(new Insets(10));
        vboxFinalizar.setAlignment(Pos.CENTER_RIGHT);

        lblTotal = new Label("TOTAL: R$ 0,00");
        lblTotal.setFont(new Font("Arial", 24));
        
        Button btnFinalizar = new Button("Finalizar Venda");
        btnFinalizar.setPrefSize(180, 50);
        btnFinalizar.setOnAction(e -> finalizarVenda());

        Button btnCancelarVenda = new Button("Cancelar Venda");
        btnCancelarVenda.setPrefSize(180, 50);
        btnCancelarVenda.setStyle("-fx-background-color: #f8d7da;");
        btnCancelarVenda.setOnAction(e -> limparTela());

        HBox hboxBotoesAcao = new HBox(20, btnCancelarVenda, btnFinalizar);
        hboxBotoesAcao.setAlignment(Pos.CENTER_RIGHT);

        vboxFinalizar.getChildren().addAll(lblTotal, hboxBotoesAcao);
        borderPane.setBottom(vboxFinalizar);

        Scene scene = new Scene(borderPane, 900, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private void selecionarCliente() {
        new TelaSelecaoCliente().showAndWait().ifPresent(cliente -> {
            this.clienteAtual = cliente;
            lblClienteInfo.setText("Cliente: " + cliente.getNome());
        });
    }

    private void adicionarProduto(String codigo, String quantidadeStr, TextField fieldToFocus) {
        try {
            if(codigo.isEmpty()) return;
            int quantidade = Integer.parseInt(quantidadeStr);
            if(quantidade <= 0) return;
            
            Produto produto = new ProdutoDAO().getProdutoPorCodigo(codigo);
            if (produto != null) {
                if(produto.getQuantEstoque() >= quantidade){
                    ItemVenda item = new ItemVenda();
                    item.setProduto(produto);
                    item.setQuantVendida(quantidade);
                    item.setPrecoVenda(produto.getPrecoVenda());
                    itensVenda.add(item);
                    atualizarTotal();
                    fieldToFocus.clear();
                    fieldToFocus.requestFocus();
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Estoque Insuficiente", "Estoque disponível: " + produto.getQuantEstoque());
                }
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Produto não encontrado", "Nenhum produto com o código informado.");
            }
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "A quantidade deve ser um número.");
        } catch (RuntimeException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Banco de Dados", ex.getMessage());
        }
    }

    private void finalizarVenda() {
        if (itensVenda.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Venda vazia", "Adicione pelo menos um item para finalizar a venda.");
            return;
        }

        new TelaPagamento().showAndWait().ifPresent(formaPagamento -> {
            try {
                VendaDAO vendaDAO = new VendaDAO();
                Venda novaVenda = new Venda();
                novaVenda.setDataVenda(new Date());
                novaVenda.setIdCli(clienteAtual);
                novaVenda.setFormaPag(formaPagamento);
                novaVenda.setValorVenda((float) totalVenda);
                
                int idVenda = vendaDAO.adiciona(novaVenda);
                if (idVenda == 0) throw new RuntimeException("Não foi possível obter o ID da venda salva.");
                novaVenda.setId(idVenda);

                ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
                ProdutoDAO produtoDAO = new ProdutoDAO();
                for (ItemVenda item : itensVenda) {
                    item.setIdVenda(novaVenda);
                    itemVendaDAO.adiciona(item);
                    
                    Produto p = item.getProduto();
                    p.setQuantEstoque(p.getQuantEstoque() - item.getQuantVendida());
                    produtoDAO.atualizaEstoque(p);
                }
                
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Venda finalizada com sucesso!");
                limparTela();

            } catch (RuntimeException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar Venda", "Ocorreu um erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void limparTela() {
        clienteAtual = null;
        lblClienteInfo.setText("Cliente: Consumidor Padrão");
        itensVenda.clear();
        atualizarTotal();
    }

    private void atualizarTotal() {
        totalVenda = 0;
        for (ItemVenda item : itensVenda) {
            totalVenda += item.getSubtotal();
        }
        lblTotal.setText(String.format("TOTAL: R$ %.2f", totalVenda));
    }

    private void mostrarAlerta(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}