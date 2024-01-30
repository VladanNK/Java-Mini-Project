package zadatak2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import zadatak1.ServerThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Server2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    static TextArea odgovori2;
    Label naslov;
    Font font;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Server");
        primaryStage.setHeight(500.0);
        primaryStage.setWidth(700.0);
        odgovori2 = new TextArea();
        odgovori2.setPrefWidth(700.0);
        odgovori2.setPrefHeight(430.0);
        naslov = new Label("Server");
        font = new Font("Arial", 20);
        odgovori2.setFont(font);
        odgovori2.appendText("Server je pokrenut...\n");
        naslov.setFont(font);
        AnchorPane anchorPane = new AnchorPane();
        ObservableList<Node> unetiElementi = anchorPane.getChildren();
        AnchorPane.setTopAnchor(naslov, 10.0);
        AnchorPane.setLeftAnchor(naslov, 300.0);
        unetiElementi.add(naslov);
        AnchorPane.setTopAnchor(odgovori2, 50.0);
        unetiElementi.add(odgovori2);
        Scene scena = new Scene(anchorPane, 400, 400);
        primaryStage.setScene(scena);
        primaryStage.show();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket ss2 = new ServerSocket(9000);
                    System.out.println("Server je pokrenut...");
                    while(true){
                        Socket soket = ss2.accept();
                        ServerThread2 st2 = new ServerThread2(soket);
                    }
                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        });
        thread2.start();
    }
}
