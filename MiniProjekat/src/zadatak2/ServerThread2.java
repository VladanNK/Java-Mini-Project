package zadatak2;

import zadatak1.Server1;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ServerThread2 extends Thread{
    private Socket soket;
    private BufferedReader in;
    private PrintWriter out;
    public ServerThread2(Socket soket){
        this.soket = soket;
        try{
            in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())), true);
        }catch (IOException exception){
            exception.printStackTrace();
        }
        start();
    }
    public void run(){
        try {
            File putanja = new File("C:\\Users\\Vladan\\IdeaProjects\\MiniProjekat\\src\\zadatak2\\DATA_SERVER");
            File[] listaFajlova = putanja.listFiles();

            StringBuilder odgovor = new StringBuilder();
            StringBuilder odgovor2 = new StringBuilder();
            for (File fajl : listaFajlova) {
                if(fajl.isFile()) {
                    odgovor.append(fajl.getName()).append("\n");
                }
            }
            System.out.println(odgovor);
            out.println(odgovor);
            
            String izabraniFajl = in.readLine();
            if(izabraniFajl != null && !izabraniFajl.isEmpty()){
                File fajlZaPreuzimanje = new File(putanja, izabraniFajl);
                File premestanjeUDrugiDirektorijum = new File("C:\\Users\\Vladan\\IdeaProjects\\MiniProjekat\\src\\zadatak2\\DATA_KLIJENT");

                if(fajlZaPreuzimanje.renameTo(new File(premestanjeUDrugiDirektorijum, izabraniFajl))){
                    System.out.println("Fajl je uspesno premesten");
                    Server2.odgovori2.appendText("Fajl je uspesno premesten\n");
                    odgovor2.append(izabraniFajl + " " + izabraniFajl.length() + " ispisano");
                    out.println(izabraniFajl);
                } else {
                    System.out.println("Fajl nije premesten");
                    //Server2.odgovori2.appendText("Fajl nije premesten\n");
                    out.println(izabraniFajl);
                }
            } else {
                System.out.println("Niste izabrali nijedan fajl.");
                Server2.odgovori2.appendText("Niste izabrali nijedan fajl\n");
                odgovor2.append("Nije izabran fajl.");
            }
            in.close();
            out.close();
            soket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
