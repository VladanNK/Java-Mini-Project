package zadatak3;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Klijent3 extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    Label naslov;
    Font font;
    Font font2;
    Button start;
    Label porukaCestitke;
    int trenutnaPozicija;
    Button[] buttonArray = new Button[9];
    int[] redosledObojenih = new int[9];
    public boolean daLiPostoji(int[] niz, int trazeniBroj){
        for (int i = 0; i < niz.length; i++) {
            if(trazeniBroj == niz[i]){
                return true;
            }
        }
        return false;
    }
    public void start(Stage primaryStage){
        primaryStage.setTitle("Igra Sequence 3x3");
        primaryStage.setWidth(550.0);
        primaryStage.setHeight(700.0);
        font = new Font("Times New Roman", 30);
        font2 = new Font("Arial", 20);
        naslov = new Label("Sequence 3x3");
        start = new Button("Start");
        porukaCestitke = new Label();
        naslov.setFont(font);
        start.setFont(font2);
        porukaCestitke.setFont(font);
        start.setOnAction(arg -> {
            for (Button button : buttonArray){
                button.setStyle("-fx-background-color: #5ee25e");
            }
            porukaCestitke.setText("");
            trenutnaPozicija = 0;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int[] nizDugmica = new int[9]; //automatski stavlja nulu na svim elementima niza
                    for (int i = 0; i < nizDugmica.length; i++) {
                        nizDugmica[i] = 20;
                    }
                    int brojac = 0;
                    int brojac2 = 0;
                    Random random = new Random();
                    int randomBroj;
                    for (int i = 0; i <= nizDugmica.length; i++) {
                        do {
                            randomBroj = random.nextInt(9);
                        }while (daLiPostoji(nizDugmica, randomBroj));
                        nizDugmica[brojac++] = randomBroj;

                        System.out.println(randomBroj);
                        System.out.println("Ovo je i: " + i);
                        redosledObojenih[brojac2] = randomBroj; //pakuje redosled obojenih dugmica
                        brojac2++; //uvecava brojac od niza redosledObojenih
                        Button button = buttonArray[randomBroj];
                        button.setStyle("-fx-background-color: #e3d109");
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            thread.start();
        });
        //Button provera = new Button("provera");

        /*provera.setOnAction(arg -> {
            for (int i = 0; i < redosledObojenih.length; i++) {
                System.out.println("Ovo je od redosled obojenih: " + redosledObojenih[i]);
            }
        });*/
        AnchorPane anchorPane = new AnchorPane();
        ObservableList<Node> postavljeniElementi = anchorPane.getChildren();
        /*AnchorPane.setLeftAnchor(provera, 50.0);
        AnchorPane.setTopAnchor(provera, 30.0);
        postavljeniElementi.add(provera);*/
        anchorPane.setPrefSize(550.0, 700.0);
        AnchorPane.setLeftAnchor(naslov, 200.0);
        AnchorPane.setTopAnchor(naslov, 10.0);
        postavljeniElementi.add(naslov);
        AnchorPane.setLeftAnchor(start, 240.0);
        AnchorPane.setTopAnchor(start, 70.0);
        postavljeniElementi.add(start);
        for (int i = 0; i < 9; i++) {
            int red = i / 3;
            int kolona = i % 3;
            int index = i;
            buttonArray[i] = new Button(Integer.toString(i));
            buttonArray[i].setStyle("-fx-background-color: #5ee25e");
            buttonArray[i].setMinWidth(120.0);
            buttonArray[i].setMinHeight(50.0);
            buttonArray[i].setFont(font2);
            buttonArray[i].setShape(new Circle(3));
            buttonArray[i].setMaxSize(3, 3);
            buttonArray[i].setOnAction(e -> isClicked(index));
            AnchorPane.setTopAnchor(buttonArray[i], 170.0 + red * 100.0);
            AnchorPane.setLeftAnchor(buttonArray[i], 70.0 + kolona * 150.0);
            anchorPane.getChildren().add(buttonArray[i]);
        }
        AnchorPane.setLeftAnchor(porukaCestitke, 160.0);
        AnchorPane.setTopAnchor(porukaCestitke, 550.0);
        postavljeniElementi.add(porukaCestitke);
        Scene scena = new Scene(anchorPane, 550.0, 700.0);
        primaryStage.setScene(scena);
        primaryStage.show();
    }
    public void isClicked(int index1){
        if(redosledObojenih[trenutnaPozicija] == index1){
            buttonArray[index1].setStyle("-fx-background-color: #5ee25e");
            trenutnaPozicija++;
        } else {
            buttonArray[index1].setStyle("-fx-background-color: #e3d109");
            System.out.println("Nije to dugme!");
        }
        if (trenutnaPozicija == redosledObojenih.length) {
            System.out.println("Odlicno odigrano!");
            porukaCestitke.setText("Odlicno odigrano!");
        }
    }
}