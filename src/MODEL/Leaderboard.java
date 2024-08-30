package MODEL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Leaderboard {
    
    //crea il file se non è presente(devo porre la condizione se no credo me li continua a creare di nuovi)
    public static void createFile(){
        File txtFile = new File("Bubble Bobble Resources/SaveFile.txt");
    }


    //scrive il file
    public static void writeFile() throws FileNotFoundException {

        File txtfile = new File("Bubble Bobble Resources/SaveFile.txt");

        try {
            PrintWriter pw = new PrintWriter(txtfile);
            pw.println("prova");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    //leggere contenuti su file

    public static void readSaveFile(){
        File txtfile = new File("Bubble Bobble Resources/SaveFile.txt");
        try {
            Scanner scan = new Scanner(txtfile);
            while(scan.hasNextLine()){
                System.out.println(scan.hasNextLine());
            }
        } catch (FileNotFoundException e ){
            e.printStackTrace();
        }

    }
}
