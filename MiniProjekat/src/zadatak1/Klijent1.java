package zadatak1;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Klijent1 extends Application implements EventHandler<ActionEvent> {
    public static void main(String[] args) {
        launch(args);
    }
    Button posalji;
    Label naslov;
    Label tekst;
    TextArea odgovor;
    TextField unetiString;
    Font font;
    Font font2;
    @Override public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Klijent");
        primaryStage.setHeight(500.0);
        primaryStage.setWidth(700.0);
        posalji = new Button("Posalji");
        posalji.setOnAction(this);
        naslov = new Label("Klijent");
        tekst = new Label("Uneti neki string za proveru da li je palindrom: ");
        odgovor = new TextArea();
        unetiString = new TextField();
        font = new Font("Arial", 35);
        font2 = new Font("Times New Roman", 20);
        posalji.setFont(font2);
        naslov.setFont(font);
        tekst.setFont(font2);
        odgovor.setFont(font2);
        AnchorPane anchorPane = new AnchorPane();
        ObservableList<Node> postavljeniElementi = anchorPane.getChildren();
        anchorPane.setPrefSize(500.0, 700.0);
        AnchorPane.setTopAnchor(naslov, 10.0);
        AnchorPane.setLeftAnchor(naslov, 300.0);
        postavljeniElementi.add(naslov);
        AnchorPane.setLeftAnchor(tekst, 20.0);
        AnchorPane.setTopAnchor(tekst, 50.0);
        postavljeniElementi.add(tekst);
        AnchorPane.setLeftAnchor(unetiString, 400.0);
        AnchorPane.setTopAnchor(unetiString, 60.0);
        postavljeniElementi.add(unetiString);
        AnchorPane.setTopAnchor(posalji, 90.0);
        AnchorPane.setLeftAnchor(posalji, 300.0);
        postavljeniElementi.add(posalji);
        odgovor.setPrefHeight(350.0);
        odgovor.setPrefWidth(700.0);
        AnchorPane.setTopAnchor(odgovor, 140.0);
        postavljeniElementi.add(odgovor);
        primaryStage.show();
        Scene scena = new Scene(anchorPane, 500, 700);
        primaryStage.setScene(scena);

    }
    public void handle(ActionEvent e){
        try{
            InetAddress adresa = InetAddress.getByName("127.0.0.1");
            Socket soket = new Socket(adresa, 9000);
            BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())), true);

            String unos = unetiString.getText();
            if(!unos.isEmpty()) {
                out.println(unos);
                String zahtev = in.readLine();
                odgovor.appendText("Odgovor: " + zahtev + "\n");
            } else {
                odgovor.appendText("Niste uneli nijedan string.\n");
            }
            in.close();
            out.close();
            soket.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
