package zadatak1;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    static TextArea odgovori;
    Label naslov;
    Font font;
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Server");
        primaryStage.setHeight(500.0);
        primaryStage.setWidth(700.0);
        odgovori = new TextArea();
        odgovori.setPrefWidth(700.0);
        odgovori.setPrefHeight(430.0);
        naslov = new Label("Server");
        font = new Font("Arial", 20);
        odgovori.setFont(font);
        odgovori.appendText("Server je pokrenut...\n");
        naslov.setFont(font);
        AnchorPane anchorPane = new AnchorPane();
        ObservableList<Node> unetiElementi = anchorPane.getChildren();
        AnchorPane.setTopAnchor(naslov, 10.0);
        AnchorPane.setLeftAnchor(naslov, 300.0);
        unetiElementi.add(naslov);
        AnchorPane.setTopAnchor(odgovori, 50.0);
        unetiElementi.add(odgovori);
        Scene scena = new Scene(anchorPane, 400, 400);
        primaryStage.setScene(scena);
        primaryStage.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket ss = new ServerSocket(9000);
                    System.out.println("Server je pokrenut");
                    while(true) {
                        Socket soket = ss.accept();
                        ServerThread serverThread = new ServerThread(soket);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
