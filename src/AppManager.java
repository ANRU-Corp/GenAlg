import com.sun.org.apache.xpath.internal.operations.Mod;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppManager {
    /**
     * Plik wejsciowy na pierwszym miejscu zawiera wymiar sudoku
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Board board= getBoardFromFile("/home/longman/Documents/AlGeny/GenAlgeny/src/sudoku2.input");
        int populationSize = 10000;
        double mutationProbability = 0.01;
        int maxIteration = 10000;
        ModelManager modelManager = new ModelManager(populationSize ,mutationProbability,maxIteration,board);
        modelManager.start();
        //System.out.print(board.getSubGrid(1).get(1));
        //System.out.println(board.toString());
        //System.out.println("fitness : "+board.getFitness());


    }
    /**AR
     * read from file to integer list
     */
    private static Board getBoardFromFile(String fileName) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(fileName));
        List<Integer> list = new ArrayList<Integer>();
        int size = scanner.nextInt();
        int newInt = 0;

        while (scanner.hasNextInt()) {
            newInt = scanner.nextInt();
            //System.out.println(newInt);
            list.add(newInt);
        }

        return new Board(list,size);
    }





}
