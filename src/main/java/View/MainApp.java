/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Cria uma instância da nossa tela de login e a exibe
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}