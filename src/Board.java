import java.util.ArrayList;
import java.util.List;

/**
 * Created by longman on 31.10.17.
 */
public class Board {
/**
 * poniewaz duzo rzeczy przechowuje to samo tak naprawde
 */
    /**
     * subGridsList lista podplansz , kazda podsiatka to lista o długosci size
     */

    private List<List<Field>> subGridsList;
    private Field[][] board;
    private int size;



    /**
     * Konstruktor jeszcze nie zrobiony
     * @param board
     */
    public Board(List<Integer> board,int size) {
        this.size = board.size();
        initSubgrids();
        createBoard(board);

    }

    /**
     * przechowuje referencje nie tworzy kopii
     * @param
     */
    public Board(List<List<Field>> fieldList){

        this.subGridsList=fieldList;
        this.size = fieldList.size();
        board = new Field[size][size];


        Field field;
        boolean constantField= true;
        int digit = 0 ;

        for(int i = 0 ; i<size ; i++){
            for(int j = 0 ; j<size ; j++){

                field = fieldList.get(i).get(j);
                digit = field.getDigit();
                constantField = field.isConstant();
                this.board[i][j] = field;

            }
        }



    }

    public Board() {
        this.size=0;
        initSubgrids();
    }

    /**
     * strona 3 do rozwazenia
     */
    public void fillEasyFields(){

    }


    /**
     * pomocnicza funkcja do konstruktora , zapewnia ze wszystkie pola są zaincjalizowane
     * @param boardList
     */
    private void createBoard(List<Integer> boardList){
        board = new Field[size][size];


        Field field;
        Boolean constantField= true;
        int digit = 0 ;

        for(int i = 0 ; i<size ; i++){
            for(int j = 0 ; j<size ; j++){

                digit = boardList.get(i*size+j);
                constantField = (digit==0);
                field = new Field(constantField,digit);

                board[i][j] = field;
                subGridsList.get(i).add(j,field);
            }
        }
    }

    /**
     * pomocnicza do createBoard inizjalizuje podsiatki
     */
    private void initSubgrids(){
        for (List<Field> fields : subGridsList = new ArrayList<>(size)) {
            fields= new ArrayList<>(size);
        }
    }
    /**
     * do zaimplementowania ma zwracac wartosc funkcji celu
     * @return
     */
    private int calcualteFitnessFunction(){
        int fitnessValue=0;

        return fitnessValue;
    }

    public int getFitness() {
        return calcualteFitnessFunction();
    }

    public List<Field> getSubGrid(int number){ return subGridsList.get(number);   }

    public List<List<Field>> getSubgrids(){
        return subGridsList;
    }

    public int getSize() {   return size;    }

    public List<Integer> getRawNumberString(){
        List<Integer> rawNumberString = new ArrayList<>(size*size);
        for (List<Field> fieldList: subGridsList) {
            for(Field field:fieldList){
                rawNumberString.add(field.getDigit());
            }
        }
        return rawNumberString;
    }


}