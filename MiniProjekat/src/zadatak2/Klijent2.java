package zadatak2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Klijent2 extends Application implements EventHandler<ActionEvent> {

    public static void main(String[] args) {
        launch(args);
    }
    Label naslov;
    Font font;
    Label tekst;
    Button izaberi;
    Label izlaz;
    Button preuzmi;
    ObservableList<String> listaFajlova;
    ListView<String> listView = new ListView<>(listaFajlova);
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Klijent");
        primaryStage.setWidth(550.0);
        primaryStage.setHeight(700.0);
        font = new Font("Arial", 20);
        naslov = new Label("Klijent");
        tekst = new Label("Prikazi fajlove iz datoteke DATA_SERVER: ");
        izaberi = new Button("Prikazi");
        izlaz = new Label("Izabrane datoteke:");
        preuzmi = new Button("Preuzmi datoteku");
        preuzmi.setFont(font);
        preuzmi.setOnAction(event -> {
            try{
                InetAddress address = InetAddress.getByName("127.0.0.1");
                Socket soket = new Socket(address, 9000);
                BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())), true);
                String zahtev2 = in.readLine();
                String zahtev = listView.getSelectionModel().getSelectedItem();
                for (int i = 0; i < listaFajlova.size(); i++) {
                    if(zahtev != null){
                        out.println(zahtev);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        File prebacen = new File("C:\\Users\\Vladan\\IdeaProjects\\MiniProjekat\\src\\zadatak2\\DATA_KLIJENT\\" + zahtev);
                        System.out.println(zahtev);
                        if(prebacen.exists()) {
                            if (prebacen.length() < 1024) {
                                izlaz.setText(prebacen.getName() + " " + Math.round(1.0 * prebacen.length()) + " Bytes");
                            } else if (prebacen.length() < (1024 * 1024)) {
                                izlaz.setText(prebacen.getName() + " " + Math.round(1.0 * prebacen.length() / 1024) + " KB");
                            } else {
                                izlaz.setText(prebacen.getName() + " " + Math.round(1.0 * prebacen.length() / (1024 * 1024)) + " MB");
                            }
                        } else {
                            izlaz.setText("Fajl nije preuzet.");
                        }
                    } else {
                        izlaz.setText("Niste izabrali fajl.");
                    }
                }
                soket.close();
                in.close();
                out.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        });
        izlaz.setFont(font);
        listaFajlova = FXCollections.observableArrayList();
        izaberi.setOnAction(this);
        naslov.setFont(font);
        tekst.setFont(font);
        izaberi.setFont(font);
        listView.setPrefWidth(550.0);
        listView.setPrefHeight(300.0);
        AnchorPane anchorPane = new AnchorPane();
        ObservableList<Node> postavljeniElementi = anchorPane.getChildren();
        anchorPane.setPrefSize(550.0, 700.0);
        AnchorPane.setTopAnchor(naslov, 10.0);
        AnchorPane.setLeftAnchor(naslov, 250.0);
        postavljeniElementi.add(naslov);
        AnchorPane.setTopAnchor(tekst, 60.0);
        postavljeniElementi.add(tekst);
        AnchorPane.setTopAnchor(izaberi, 100.0);
        AnchorPane.setLeftAnchor(izaberi, 400.0);
        postavljeniElementi.add(izaberi);
        AnchorPane.setTopAnchor(listView, 150.0);
        postavljeniElementi.add(listView);
        AnchorPane.setTopAnchor(preuzmi, 100.0);
        AnchorPane.setLeftAnchor(preuzmi, 30.0);
        postavljeniElementi.add(preuzmi);
        AnchorPane.setTopAnchor(izlaz, 470.0);
        AnchorPane.setLeftAnchor(izlaz, 10.0);
        postavljeniElementi.add(izlaz);
        Scene scena = new Scene(anchorPane, 550.0, 700.0);
        primaryStage.setScene(scena);
        primaryStage.show();
    }
    public void handle(ActionEvent event){
        try{
            InetAddress address = InetAddress.getByName("127.0.0.1");
            Socket soket = new Socket(address, 9000);
            BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())), true);
            out.println("C:\\Users\\Vladan\\IdeaProjects\\MiniProjekat\\src\\zadatak2\\DATA_SERVER");

            String naziviFajlova;
            while(!(naziviFajlova = in.readLine()).isEmpty()){
                listaFajlova.add(naziviFajlova);

            }
            listView.setItems(listaFajlova);

            soket.close();
            in.close();
            out.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
