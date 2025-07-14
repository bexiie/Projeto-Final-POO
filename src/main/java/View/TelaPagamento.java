package View;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Optional;

public class TelaPagamento {

    private Integer formaPagamentoSelecionada = null;

    // O método retorna um Optional<Integer> que conterá o número da forma de pagamento
    public Optional<Integer> showAndWait() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Forma de Pagamento");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("Selecione a Forma de Pagamento");
        
        // ComboBox com as opções de pagamento
        ComboBox<String> comboPagamento = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Dinheiro", 
                        "PIX", 
                        "Cartão de Débito", 
                        "Cartão de Crédito"
                )
        );
        comboPagamento.setPromptText("Escolha uma opção");

        Button btnConfirmar = new Button("Confirmar Pagamento");
        btnConfirmar.setOnAction(e -> {
            int selectedIndex = comboPagamento.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                // O índice + 1 corresponderá ao nosso padrão (1-Dinheiro, 2-PIX, etc.)
                this.formaPagamentoSelecionada = selectedIndex + 1;
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione uma forma de pagamento.").showAndWait();
            }
        });

        vbox.getChildren().addAll(lblTitulo, comboPagamento, btnConfirmar);

        Scene scene = new Scene(vbox, 300, 150);
        stage.setScene(scene);
        stage.showAndWait();

        return Optional.ofNullable(formaPagamentoSelecionada);
    }
}