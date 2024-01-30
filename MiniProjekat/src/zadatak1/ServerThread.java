package zadatak1;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket soket;
    private BufferedReader in;
    private PrintWriter out;
    private String zahtev;
    public ServerThread(Socket soket){
        this.soket = soket;
        //this.zahtev = zahtev;
        try{
            in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soket.getOutputStream())), true);
        }catch (IOException exception){
            exception.printStackTrace();
        }
        start();
    }
    public void run(){
        StringBuilder odgovor = new StringBuilder();
        try{
            zahtev = in.readLine();
            //if(zahtev != null || !zahtev.isEmpty()) {
                String provera = zahtev.replace(" ", "").toLowerCase();
                String novaRec = "";
                for (int i = 0; i < provera.length(); i++) {
                    if (provera.charAt(i) != provera.charAt(provera.length() - 1 - i)) {
                        //System.out.println(zahtev + " nije palindrom");
                        break;
                    } else {
                        odgovor.append(provera.charAt(i));
                        novaRec += provera.charAt(i);
                        //System.out.println(proveraPalindroma[i]);
                    }
                }
                if (provera.equals(novaRec)) {
                    out.println(zahtev + " jeste palindrom");
                    Server1.odgovori.appendText(zahtev + " jeste palindrom\n");
                    System.out.println(zahtev + " jeste palindrom");
                } else {
                    out.println(zahtev + " nije palindrom");
                    Server1.odgovori.appendText(zahtev + " nije palindrom\n");
                    System.out.println(zahtev + " nije palindrom");
                }
            //}
            //Server1.odgovori.appendText(zahtev + "\n");
            in.close();
            out.close();
            soket.close();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}