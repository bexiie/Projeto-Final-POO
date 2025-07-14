package View;

import DAO.ResumoVendaDAO;
import Model.ResumoVenda;
import Util.GeradorTXT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TelaRelatorioVendas {

    private TableView<ResumoVenda> tableView = new TableView<>();
    private DatePicker datePicker;
    private Label lblTotalGeral;
    private ObservableList<ResumoVenda> observableList = FXCollections.observableArrayList();

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Relatório Sintético de Vendas");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        datePicker = new DatePicker(LocalDate.now());
        Button btnGerarRelatorio = new Button("Gerar Relatório");
        HBox hboxFiltro = new HBox(10, new Label("Data da Venda:"), datePicker, btnGerarRelatorio);
        
        TableColumn<ResumoVenda, String> colFormaPag = new TableColumn<>("Forma de Pagamento");
        colFormaPag.setCellValueFactory(new PropertyValueFactory<>("formaPagStr"));
        colFormaPag.setPrefWidth(200);
        TableColumn<ResumoVenda, Float> colValor = new TableColumn<>("Valor Total Vendido");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorFormaPag"));
        colValor.setPrefWidth(200);
        tableView.getColumns().addAll(colFormaPag, colValor);
        tableView.setItems(observableList);

        lblTotalGeral = new Label("Total Geral do Dia: R$ 0,00");
        lblTotalGeral.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Novos botões de ação na parte inferior
        Button btnExportar = new Button("Exportar para TXT");
        Button btnVoltar = new Button("Voltar"); // Botão Adicionado
        HBox hboxBotoesAcao = new HBox(10, btnExportar, btnVoltar);
        hboxBotoesAcao.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        vbox.getChildren().addAll(hboxFiltro, tableView, lblTotalGeral, hboxBotoesAcao);

        // Ações dos botões
        btnGerarRelatorio.setOnAction(e -> gerarRelatorio());
        btnExportar.setOnAction(e -> {
            if (observableList.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Nenhum dado", "Gere um relatório com dados antes de exportar.");
            } else {
                try {
                    Date data = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    GeradorTXT.gerar(observableList, data);
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Relatório TXT gerado com sucesso na sua Área de Trabalho, na pasta 'RelatoriosVendas'.");
                } catch (IOException ex) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Gerar Arquivo", "Não foi possível criar o arquivo de texto: " + ex.getMessage());
                }
            }
        });
        btnVoltar.setOnAction(e -> stage.close()); // Ação do botão "Voltar"
        
        gerarRelatorio();

        Scene scene = new Scene(vbox, 480, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void gerarRelatorio() {
        LocalDate localDate = datePicker.getValue();
        if (localDate != null) {
            observableList.clear();
            lblTotalGeral.setText("Total Geral do Dia: R$ 0,00");
            try {
                ResumoVendaDAO resumoDAO = new ResumoVendaDAO();
                List<ResumoVenda> listaResumos = resumoDAO.getResumoPorData(localDate);
                if (listaResumos.isEmpty()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Relatório Gerado", "Nenhuma venda foi encontrada para a data selecionada.");
                } else {
                    observableList.setAll(listaResumos);
                    float totalGeral = 0;
                    for (ResumoVenda resumo : listaResumos) {
                        totalGeral += resumo.getValorFormaPag();
                    }
                    lblTotalGeral.setText(String.format("Total Geral do Dia: R$ %.2f", totalGeral));
                }
            } catch (RuntimeException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Gerar Relatório", "Ocorreu um erro: " + e.getMessage());
                e.printStackTrace();
            }
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