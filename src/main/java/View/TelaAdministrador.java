package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TelaAdministrador {

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Painel do Administrador");

        // --- Layout Principal ---
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // --- Título ---
        Label lblTitulo = new Label("Painel de Controle");
        lblTitulo.setFont(new Font("Arial", 24));
        BorderPane.setAlignment(lblTitulo, Pos.CENTER);
        borderPane.setTop(lblTitulo);

        // --- Grid de Botões ---
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        // Botão Gerenciar Clientes
        Button btnClientes = new Button("Gerenciar Clientes");
        btnClientes.setPrefSize(180, 80); // Tamanho (largura, altura)
        btnClientes.setOnAction(e -> new TelaGerenciarClientes().show());
        grid.add(btnClientes, 0, 0);

        // Botão Gerenciar Produtos
        Button btnProdutos = new Button("Gerenciar Produtos");
        btnProdutos.setPrefSize(180, 80);
        btnProdutos.setOnAction(e -> new TelaGerenciarProdutos().show());
        grid.add(btnProdutos, 1, 0);

        // Botão Gerenciar Usuários
        Button btnUsuarios = new Button("Gerenciar Usuários");
        btnUsuarios.setPrefSize(180, 80);
        btnUsuarios.setOnAction(e -> new TelaGerenciarUsuarios().show());
        grid.add(btnUsuarios, 0, 1);

        // Botão Relatórios (ainda não implementado)
        // Novo bloco para o botão de relatórios
        Button btnRelatorios = new Button("Relatórios");
        btnRelatorios.setPrefSize(180, 80);
        btnRelatorios.setOnAction(e -> new TelaRelatorioVendas().show()); // Ação adicionada
        grid.add(btnRelatorios, 1, 1);
        
        borderPane.setCenter(grid);

        // --- Botão Sair ---
        Button btnSair = new Button("Sair (Logout)");
        btnSair.setOnAction(e -> {
            stage.close();
            new TelaLogin().show();
        });
        
        VBox bottomBox = new VBox(btnSair);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));
        borderPane.setBottom(bottomBox);


        Scene scene = new Scene(borderPane, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
}