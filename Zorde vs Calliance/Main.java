import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        String outputFile = args[2];//command line argument 3 which is the name of output file
        /*All the lists and maps below are benefited from polymorphism. Lists are created by parent class type,
        but they are using overridden subclass methods*/
        HashMap<String, Calliance> callianceMap = new HashMap<>(); //Calliance characters gathered in a map key=name
        HashMap<String, Zorde> zordeMap = new HashMap<>(); //Zorde characters gathered in a map key=name
        ArrayList<Characters> allCharacters = new ArrayList<>(); //To sort warriors by name I put them all in a list
        Object[][] board = new Object[1][1];//created the board outside the try block below. That way I can reach the
        //updated board wherever I want.
        FileWriter myWriter = new FileWriter(outputFile,false);
        myWriter.close(); //overwritten and cleared the file for multiple test

        /*read initials.txt*/
        try (Scanner scanner = new Scanner(Paths.get(args[0]))) {
            while (scanner.hasNextLine()) {
                String[] pm = scanner.nextLine().split(" "); //pm = parameters
                if (pm[0].equalsIgnoreCase("board")) {
                    String[] boardSize = scanner.nextLine().split("x");//omitted board line and read next line
                    int a = Integer.parseInt(boardSize[1]); //a=size of board
                    board = new Object[a + 2][a + 2];//to show the borders I created a+2xa+2 sized board
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board.length; j++) {
                            if ((i == 0 || i == a + 1) && (j != a + 1 && j != 0)) {
                                board[i][j] = "**";
                            } else if (j == 0 || j == a + 1) {
                                board[i][j] = "*";
                            } else {
                                board[i][j] = "  ";
                            }

                        }
                    }
                }
                //all objects are instantiated as variables to prevent copies. If objects were added to lists without
                // declaring them as variables they would only be different references to the same object. That means
                //if a change were to made in one lists object, it wouldn't be reflected on the other.
                if (pm[0].equalsIgnoreCase("elf")) {
                    /*arrays use [row][col] but given commands are col-row so, parameters are taken in reverse order*/
                    Calliance elf = new Elf(pm[1], Integer.parseInt(pm[3]) + 1, Integer.parseInt(pm[2]) + 1,
                            Constants.elfHP);
                    callianceMap.put(pm[1], elf);
                    allCharacters.add(elf);
                }
                if (pm[0].equalsIgnoreCase("dwarf")) {
                    Calliance dwarf = new Dwarf(pm[1], Integer.parseInt(pm[3]) + 1, Integer.parseInt(pm[2]) + 1,
                            Constants.dwarfHP);
                    callianceMap.put(pm[1], dwarf);
                    allCharacters.add(dwarf);
                }
                if (pm[0].equalsIgnoreCase("human")) {
                    Calliance human = new Human(pm[1], Integer.parseInt(pm[3]) + 1, Integer.parseInt(pm[2]) + 1,
                            Constants.humanHP);
                    callianceMap.put(pm[1], human);
                    allCharacters.add(human);
                }
                if (pm[0].equalsIgnoreCase("goblin")) {
                    Zorde goblin = new Goblin(pm[1], Integer.parseInt(pm[3]) + 1, Integer.parseInt(pm[2]) + 1,
                            Constants.goblinHP);
                    zordeMap.put(pm[1], goblin);
                    allCharacters.add(goblin);
                }
                if (pm[0].equalsIgnoreCase("troll")) {
                    Zorde troll = new Troll(pm[1], Integer.parseInt(pm[3]) + 1, Integer.parseInt(pm[2]) + 1,
                            Constants.trollHP);
                    zordeMap.put(pm[1], troll);
                    allCharacters.add(troll);
                }
                if (pm[0].equalsIgnoreCase("ork")) {
                    Zorde ork = new Ork(pm[1], Integer.parseInt(pm[3]) + 1, Integer.parseInt(pm[2]) + 1,
                            Constants.orkHP);
                    zordeMap.put(pm[1], ork);
                    allCharacters.add(ork);
                }
            }
        } catch (Exception e) {
        }
        for (String key : callianceMap.keySet()) {
            board[callianceMap.get(key).row][callianceMap.get(key).column] = callianceMap.get(key);
        } //adding characters to board
        for (String key : zordeMap.keySet()) {
            board[zordeMap.get(key).row][zordeMap.get(key).column] = zordeMap.get(key);
        } //adding characters to board

        Collections.sort(allCharacters);//sorting all characters list alphabetical
        Printer.printBoard(board,allCharacters, outputFile);//see printer class for method explanations

        //read commands txt//
        try (Scanner scanner = new Scanner(Paths.get(args[1]))) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                String name = command.substring(0,2); //name of character

                try{
                if (name.charAt(0) == 'E'){
                    /*commands should be double the max moves, since every move is represented by 2 points*/
                    if(command.split(";").length!=Constants.elfMaxMove*2){ throw new MoveSequenceException(); }
                    int[] xy = Arrays.stream(command.substring(3).split(";")).mapToInt(Integer::parseInt).toArray();
                    /*xy = moves x,y integer array,*/
                    Printer.moves(name,xy,callianceMap,zordeMap,board,allCharacters, outputFile);
                    Printer.printBoard(board,allCharacters, outputFile);
                }
                if (name.charAt(0) == 'D'){
                    if(command.split(";").length!=(Constants.dwarfMaxMove*2)){ throw new MoveSequenceException(); }
                    int[] xy = Arrays.stream(command.substring(3).split(";")).mapToInt(Integer::parseInt).toArray();
                    Printer.moves(name,xy,callianceMap,zordeMap,board,allCharacters, outputFile);
                    Printer.printBoard(board,allCharacters, outputFile);
                }
                if (name.charAt(0) == 'H'){
                    if(command.split(";").length!=(Constants.humanMaxMove*2)){ throw new MoveSequenceException(); }
                    int[] xy = Arrays.stream(command.substring(3).split(";")).mapToInt(Integer::parseInt).toArray();
                    Printer.moves(name,xy,callianceMap,zordeMap,board,allCharacters, outputFile);
                    Printer.printBoard(board, allCharacters, outputFile);
                }
                if (name.charAt(0) == 'O'){
                    if(command.split(";").length!=(Constants.orkMaxMove*2)){ throw new MoveSequenceException(); }
                    int[] xy = Arrays.stream(command.substring(3).split(";")).mapToInt(Integer::parseInt).toArray();
                    Printer.movesZorde(name,xy,zordeMap,callianceMap,board,allCharacters, outputFile);
                    Printer.printBoard(board,allCharacters, outputFile);
                }
                if (name.charAt(0) == 'G'){
                    if(command.split(";").length!=(Constants.goblinMaxMove*2)){ throw new MoveSequenceException(); }
                    int[] xy = Arrays.stream(command.substring(3).split(";")).mapToInt(Integer::parseInt).toArray();
                    Printer.movesZorde(name,xy,zordeMap,callianceMap,board,allCharacters, outputFile);
                    Printer.printBoard(board,allCharacters, outputFile);
                }
                if (name.charAt(0) == 'T'){
                    if(command.split(";").length!=(Constants.trollMaxMove*2)){ throw new MoveSequenceException(); }
                    int[] xy = Arrays.stream(command.substring(3).split(";")).mapToInt(Integer::parseInt).toArray();
                    Printer.movesZorde(name,xy,zordeMap,callianceMap,board,allCharacters, outputFile);
                    Printer.printBoard(board,allCharacters, outputFile);
                }
                if (allCharacters.size() == 1){
                    Printer.GameOver(allCharacters.get(0).getClass().getSuperclass().getName(), outputFile);
                    System.exit(0);
                }
                } catch (MoveSequenceException e ){
                    Printer.Error("Move", outputFile);
                }//move step catcher
                catch(BoundaryException e){
                    Printer.Error("Boundary", outputFile);
                    }
            }
        }

        catch (Exception e){}

}
}