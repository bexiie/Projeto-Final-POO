package Util;

import Model.ResumoVenda;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeradorTXT {

    public static void gerar(List<ResumoVenda> resumos, Date data) throws IOException {
        // Define o caminho e o nome do arquivo
        String destFolder = System.getProperty("user.home") + "/Desktop/RelatoriosVendas/";
        new java.io.File(destFolder).mkdirs(); // Cria a pasta se não existir

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String destFile = destFolder + "Relatorio_" + sdf.format(data) + ".txt";

        // Usa FileWriter e PrintWriter para escrever no arquivo de texto
        try (FileWriter arq = new FileWriter(destFile);
             PrintWriter gravarArq = new PrintWriter(arq)) {

            gravarArq.printf("========================================%n");
            gravarArq.printf("      RELATÓRIO SINTÉTICO DE VENDAS     %n");
            gravarArq.printf("========================================%n%n");
            gravarArq.printf("Data: %s%n%n", sdf.format(data));
            
            gravarArq.printf("----------------------------------------%n");
            gravarArq.printf("%-25s %15s%n", "Forma de Pagamento", "Valor Total");
            gravarArq.printf("----------------------------------------%n");

            float totalGeral = 0;
            for (ResumoVenda resumo : resumos) {
                gravarArq.printf("%-25s %15.2f%n", resumo.getFormaPagStr(), resumo.getValorFormaPag());
                totalGeral += resumo.getValorFormaPag();
            }
            
            gravarArq.printf("----------------------------------------%n%n");
            gravarArq.printf("TOTAL GERAL DO DIA: R$ %.2f%n", totalGeral);
            gravarArq.printf("========================================%n");
            
            System.out.println("Arquivo TXT gerado com sucesso em: " + destFile);
        }
    }
}
