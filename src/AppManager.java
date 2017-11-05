import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppManager {
    public static void main(String[] args) throws IOException {


        List<Integer> list = new ArrayList<Integer>();
        list = ModelManager.getBoardFromFile("/home/anru/Documents/PW/GenAl/src/sudoku.txt");
        System.out.print(list.get(15));


    }





}
