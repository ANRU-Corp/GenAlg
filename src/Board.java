import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by longman on 31.10.17.
 */
public class Board implements Iterable<Field>{
/**
 * poniewaz duzo rzeczy przechowuje to samo tak naprawde
 */
    /**
     * subGridsList lista podplansz , kazda podsiatka to lista o długosci size
     */

    private List<List<Field>> subGridsList = new ArrayList<>();
    //private Field[][] board;
    private int size;

    /**
     * Konstruktor jeszcze nie zrobiony
     * @param board
     */
    public Board(List<Integer> board,int size) {
        List<List<Field>> subGridsList = createFieldListFromInteger(board,size);

        this.subGridsList = subGridsList;
        this.size= subGridsList.size();

    }

    /**
     * przechowuje referencje nie tworzy kopii
     * @param
     */
    public Board(List<List<Field>> fieldList){

        this.subGridsList=fieldList;
        this.size = fieldList.size();

    }

    public Board() {
        this.size=0;
    }

    /**
     * strona 3 do rozwazenia
     */
    public void fillEasyFields(){

    }

    /**
     * Tworzy liste obiektow klasy Field z liczb całkowitych
     */
    private List<List<Field>> createFieldListFromInteger(List<Integer> boardList,int size){
        List<List<Field>> fieldsList= new ArrayList<>();

        Field field;
        Boolean constantField= true;
        int digit = 0 ;

        for(int i = 0 ; i<size ; i++){
            fieldsList.add(new ArrayList<>());
            for(int j = 0 ; j<size ; j++){

                digit = boardList.get(i*size+j);
                constantField = (digit!=0);
                field = new Field(constantField,digit);

                fieldsList.get(i).add(j,field);
            }
        }
        return fieldsList;
    }
    /**
     * do zaimplementowania ma zwracac wartosc funkcji celu
     * @return
     */
    private int calcualteFitnessFunction(){
        int fitnessValue=0;
        Field[][] fields = getArray();
        int[] digits = new int[size];
        int digit = 0 ;
        for (int i = 0 ; i<size ;i++){
            digits[i] = 0;
        }
        //wiersze
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                digit = fields[row][column].getDigit();
                if(digit==0){
                    continue;
                }
                digits[digit-1]++;
            }
            for (int dig:digits) {
                if(dig>1){
                    fitnessValue++;
                }
            }
            digits = new int[size];
        }
        //wiersze
        for (int column = 0; column < size; column++) {
            for (int row = 0; row < size; row++) {
                digit = fields[row][column].getDigit();
                if(digit==0){
                    continue;
                }
                digits[digit-1]++;
            }
            for (int dig:digits) {
                if(dig>1){
                    fitnessValue++;
                }
            }
            digits = new int[size];
        }

        return fitnessValue;
    }


    /**
     * caly interfejs
     *
     */
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
    public List<Field> getSingleFieldList(){
        List<Field> allFields = new ArrayList<>();
        for(List<Field> list :getSubgrids()){
            for (Field field : list){
                allFields.add(field);
            }
        }
        return allFields;
    }
    public static List<List<Field>> getSubgridsFromSingleFieldList(int size,List<Field> singleFieldList){
        List<List<Field>> subgrids = new ArrayList<>(size);
        for(int i = 0 ; i<size ;i++){
            subgrids.add(new ArrayList<>(size));
            for(int j = 0 ; j<size ; j++){
                subgrids.get(i).add(singleFieldList.get(i*size+j));
            }
        }
        return subgrids;
    }


    public Field[][] getArray(){
        Field[][] fields = new Field[size][size];
        int subgridSize = (int) Math.sqrt(size);
        int index1 = 0 ;
        int index2 = 0 ;

        for(int j = 0 ; j<size ; j++){

            for(int i  = 0 ; i<size ; i++){


                index1+=i/subgridSize;
                index2+=i%subgridSize;

                index1+=(j/subgridSize)*subgridSize;
                index2+=(j%subgridSize)*subgridSize;

                fields[j][i] = this.subGridsList.get(index1).get(index2);

                index1 = 0 ;
                index2=0;
            }
        }
        return fields;
    }

    public static Board getRandomBoard(Board boardTemplate) {
        int size = boardTemplate.getSize();
        List<Field> digits = new ArrayList<>();
        for(Field field : boardTemplate.getSingleFieldList()){
            if(!field.isConstant()){
                digits.add(new Field(false, (int) Math.ceil(Math.random()*(size))));
            }
            else {
                digits.add(new Field(field));
            }
        }
        return new Board(Board.getSubgridsFromSingleFieldList(size,digits));
    }

    @Override
    public Iterator<Field> iterator(){
        return getSingleFieldList().iterator() ;
    }

    @Override
    public String toString() {
        Field[][] fields = getArray();
        String string = "\n";
        for(int i = 0 ; i<size ;i++){
            if(i%(int)Math.sqrt(size)==0){
                string+="\n";
            }
            string+="\n";
            for (int j = 0 ; j<size ; j++){
                if(j%(int)Math.sqrt(size)==0){
                    string+=" ";
                }
                string+=" "+fields[i][j]+" ";

            }
        }
        return string;
    }
}