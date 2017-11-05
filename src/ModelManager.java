import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by longman on 31.10.17.
 */
public class ModelManager {
    /**
     * Plansze prawdziwe
     */
    private List<Board> boards;
    private List<Double> probabilities;
    private int populationSize;
    private float mutationProbability;
    private int maxIteration;

    public void start(){

        int iteration = 0 ;
        int minFitness = 100;
        Board minBoard;
        initializePopulation();
        calculateProbabilities();

        while (iteration<maxIteration || minFitness==0){
            boards = newGeneration();
            for (Board board:boards) {
                mutation(board);
            }
            /**
             * liczy prawdopodobienstwa ktore bedą wykorzystane juz przy kolejnej iteracji !!!
             */
            calculateProbabilities();

            minBoard= getMinFitnessBoard();
            minFitness=minBoard.getFitness();
            iteration++;
        }
    }

    /**AR
     * read from file to integer list
     */
    public static List getBoardFromFile(String fileName) throws FileNotFoundException {
        // Board board = null;


        //List<Integer> boardRawNumberString = null;
        //int size=0;

        //board = new Board(boardRawNumberString,size);

        Scanner scanner = new Scanner(new File(fileName));
        List<Integer> list = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }

        return list;
    }

    //ANTONI ZROB LOSOWNIE
    /**
     * wylosowanie punktu przeciecaia
     * @param size
     * @return
     */
    private int getCrossoverPoint(int size){
        int point = 0 ;

        return point;
    }
    //ANTONI ZROB MUTACJE
    /**
     * mutuje na podstawie prawdopodobienstwa globalnego mutationProbability
     * @param board
     */
    private void mutation(Board board){

    }

    private Board getMinFitnessBoard() {
        /**
         * znalezc plansze o najmniejszej  wartosci funkcji celu
         */
        Board board = new Board();
        int fitness = 100000;
        int minFitness = 100;
        for (Board board1 :
                boards ) {
            if((fitness=board1.getFitness())<minFitness){
                minFitness = fitness;
                board=board1;
            }
        }
        return board;
    }

    private List newGeneration(){
        /**
         * losowała nowa generacja korzysta z boards, nie ma jednak po co robic pomocniczej listy
         * z prawdopodobienstwem wyliczonym juz w poprzednim kroku i przechowywanym w probabilties
         * i krzyzowala
         *
         */
        //selection();
        List<Integer> list = new ArrayList<Integer>();
        return list;

    }

    /**
     * pomocnicza
     */
    private void selection(){

    }



    /**
     * utworzenie pierwszej populacji losowej
     */
    private void initializePopulation(){

    }


    private void calculateProbabilities(){


        /**
         * wylicza prawdopodobienstwa
         * i zapisuje do probabilities
         */
        probabilities.clear();
        double probability=0;
        int fitnessSum = getFitnessSum() ;
        int fitness =  0;

        for (Board board:
                boards) {
            fitness = board.getFitness();
            probability = 1.0-(fitness/fitnessSum);
            probabilities.add(probability);

        }

    }

    private int getFitnessSum(){
        int fitnessSum = 0 ;
        int fitness =  0;
        for (Board board :
                boards) {
            fitness = board.getFitness();
            fitnessSum+=fitness;

        }
        return fitnessSum;
    }


    /**
     * bierze dwie i zamienia miejscami w kazdej podsiatce
     * @param board1
     * @param board2
     * @return
     */
    private Board getCrossedBoard(Board board1,Board board2){
        Board newBoard;
        int size = board1.getSize();
        int crossOverpoint = 0 ;
        List<List<Field>> grid= new ArrayList<>(size);
        List<Field > subgrid ;


        for (int i = 0; i < size ; i++) {
            subgrid = new ArrayList<>(size);
            crossOverpoint=getCrossoverPoint(size);
            grid.add(subgrid);
            /**
             * w zaleznosci od wylosowanego punktu bierze az do punktu z planszy 1 a reszte z planszy 2 i tworzy na tej podstawie nową podsiatke
             */
            for (int j = 0; j < size; j++) {
                if(j<crossOverpoint){
                    subgrid.add(board1.getSubGrid(i).get(j));
                }
                else{
                    subgrid.add(board2.getSubGrid(i).get(j));
                }
            }
        }
        newBoard = new Board(grid);

        /**
         *
         * krzyzowka
         */
        return newBoard;
    }
}