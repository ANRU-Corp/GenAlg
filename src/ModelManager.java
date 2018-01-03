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
    private double mutationProbability;
    private int maxIteration;
    private Board boardTemplate;

    public ModelManager(int populationSize, double mutationProbability, int maxIteration,Board boardTemplate) {
        this.populationSize = populationSize;
        this.mutationProbability = mutationProbability;
        this.maxIteration = maxIteration;
        this.boardTemplate = boardTemplate;
    }

    /**
     * Głowna funckja
     */
    public void start(){

        int iteration = 0 ;
        int minFitness = 100;
        Board minBoard;

        initializePopulation();
        calculateProbabilities();

        //Pracuje az znajdzie rozwiazanie lub skonczy sie czas
        while (iteration<maxIteration && minFitness!=0){
            boards = newGeneration();
            for (Board board:boards) {
                mutation(board);
            }

            //liczy prawdopodobienstwa ktore bedą wykorzystane juz przy kolejnej iteracji !!!
            calculateProbabilities();

            minBoard= getMinFitnessBoard();
            minFitness=minBoard.getFitness();
            iteration++;

            System.out.println("iteration : "+iteration);
            System.out.println("fitness : "+minFitness);
           // System.out.println();
          // System.out.println("board : \n"+minBoard.toString());
            System.out.println();
        }
    }



    //ANTONI ZROB LOSOWNIE
    /**
     * wylosowanie punktu przeciecaia
     * @param size
     * @return
     */
    private int getCrossoverPoint(int size){
        int point = 0 ;

        return (int)(Math.random()*size);
    }
    //ANTONI ZROB MUTACJE
    /**
     * mutuje na podstawie prawdopodobienstwa globalnego mutationProbability
     * @param board
     */
    private void mutation(Board board){
        int newDigit = 0 ;
        for (Field field:board) {
            if(Math.random()<mutationProbability){
                if(!field.isConstant()) {
                    newDigit=(int) Math.ceil(Math.random() * (board.getSize()));
                    field.setDigit(newDigit);
                }
            }
        }
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
            System.out.println("fitness : "+ board1.getFitness());
            if((fitness=board1.getFitness())<minFitness){
                minFitness = fitness;
                board=board1;
            }
        }
        return board;
    }

    private List<Board> newGeneration(){
        /**
         * losowała nowa generacja korzysta z boards, nie ma jednak po co robic pomocniczej listy
         * z prawdopodobienstwem wyliczonym juz w poprzednim kroku i przechowywanym w probabilties
         * i krzyzowala
         *
         */
        List<Board> newBoards = new ArrayList<>(populationSize);
        Board board1,board2;
        while(newBoards.size()<populationSize){
            board1 = getRandomBoard();
            board2 = getRandomBoard();
            /**
             * tutaj mozna zmienic metode krzyzowania
             */
            newBoards.add(getCrossedBoard2(board1,board2));
        }

        return newBoards;

    }
    private Board getRandomBoard(){
        Board board;
        int index=0;
        while (true){
            index=(int)(Math.random()*(populationSize));
            if(Math.random()<probabilities.get(index)){
                return boards.get(index);
            }
        }
    }

    /**
     * utworzenie pierwszej populacji losowej
     */
    private void initializePopulation(){
        int populationSize = this.populationSize;
        boards = new ArrayList<>(populationSize);
        Board newBoard;
        for (int i = 0; i <populationSize; i++) {
            newBoard=Board.getRandomBoard(boardTemplate);
            boards.add(newBoard);
        }
    }


    private void calculateProbabilities(){


        /**
         * wylicza prawdopodobienstwa
         * i zapisuje do probabilities
         */
        probabilities=new ArrayList<>();
        double probability=0;
        //int fitnessSum = getFitnessSum() ;
        int fitnessNorm = getFitnessNorm();
        int fitness =  0;

        for (Board board:
                boards) {
            fitness = board.getFitness();
            probability = 1.0-(fitness/fitnessNorm);
            probabilities.add(probability);

        }

    }

    private int getFitnessNorm(){
        int size = boardTemplate.getSize();
        return size*(size-1);
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
     * implementacja jednej z mozliwych metod krzyzowania
     * To jest metoda ktorą w przyszlosci mozna zamienic na inna
     * @param board1
     * @param board2
     * @return
     */
    private Board getCrossedBoard(Board board1,Board board2){
        Board newBoard;
        int size = board1.getSize();
        int crossOverpoint = 0 ;
        List<List<Field>> grid= new ArrayList<>(size);
        List<Field > subgrid =new ArrayList<>();


        for (int i = 0; i < size ; i++) {
            subgrid = new ArrayList<>(size);
            crossOverpoint=getCrossoverPoint(size);
            grid.add(subgrid);
            /**
             * w zaleznosci od wylosowanego punktu bierze az do punktu z planszy 1 a reszte z planszy 2 i tworzy na tej podstawie nową podsiatke
             */
            for (int j = 0; j < size; j++) {
                if(j<crossOverpoint){
                    subgrid.add(new Field(board1.getSubGrid(i).get(j)));
                }
                else{
                    subgrid.add(new Field(board2.getSubGrid(i).get(j)));
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

    /**
     * bierze dwie i zamienia miejscami kilka podsiatek bez ingerencji w same podsiatki
     * @param board1
     * @param board2
     * @return
     */
    private Board getCrossedBoard2(Board board1,Board board2){
        Board newBoard;
        int size = board1.getSize();
        int crossOverpoint = 0 ;
        List<List<Field>> grid= new ArrayList<>(size);

            crossOverpoint=getCrossoverPoint(size);
            /**
             * w zaleznosci od wylosowanego punktu bierze az do punktu z planszy 1 a reszte z planszy 2 i tworzy na tej podstawie nową podsiatke
             */
            for (int j = 0; j < size; j++) {
                grid.add(new ArrayList<>());
                if(j<crossOverpoint){
                    for (Field field:board1.getSubgrids().get(j)) {
                        grid.get(j).add(new Field(field));
                    }
                }
                else{
                    for (Field field:board2.getSubgrids().get(j)) {
                        grid.get(j).add(new Field(field));
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