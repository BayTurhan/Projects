import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Printer {
    /*prints the board*/
    public static void printBoard(Object[] board, ArrayList<Characters> allCharacters, String fileName)
            throws IOException {
        FileWriter writer = new FileWriter(fileName,true);
        String formattedString = Arrays.deepToString(board)
                .replace("[[", "")  //remove most left bracket
                .replace("]]", "")  //remove most right bracket
                .replace(" [", "")  //remove left brackets
                .replace("],", "\n") //replace right brackets with newline
                .replace(", ", "");//remove commas between elements
        writer.write(formattedString);
        writer.write("\n" + "\n");
        for (Object character : allCharacters){
            if (character.getClass().getSuperclass().getSimpleName().equalsIgnoreCase("calliance")){
                writer.write(character + "\t");
                writer.write(((Calliance) character).HP + "\t");
                if(character.getClass().getSimpleName().equalsIgnoreCase("elf")){
                    writer.write("(" + Constants.elfHP + ")" + "\n");
                }
                else if(character.getClass().getSimpleName().equalsIgnoreCase("human")){
                    writer.write("(" + Constants.humanHP + ")" + "\n");
                }
                else if(character.getClass().getSimpleName().equalsIgnoreCase("dwarf")){
                    writer.write("(" + Constants.dwarfHP + ")" + "\n");
                }
            }
            else if (character.getClass().getSuperclass().getSimpleName().equalsIgnoreCase("zorde")){
                writer.write(character + "\t");
                writer.write(((Zorde) character).HP + "\t");
                if(character.getClass().getSimpleName().equalsIgnoreCase("ork")){
                    writer.write("(" + Constants.orkHP + ")" + "\n");
                }
                else if(character.getClass().getSimpleName().equalsIgnoreCase("troll")){
                    writer.write("(" + Constants.trollHP + ")" + "\n");
                }
                else if(character.getClass().getSimpleName().equalsIgnoreCase("goblin")){
                    writer.write("(" + Constants.goblinHP + ")" + "\n");
                }
            }
        }
        writer.write("\n");
        writer.close();
    }
    /*prints error messages*/
    public static void Error(String type, String filename)throws IOException{
        FileWriter writer = new FileWriter(filename,true);
        if(type.equals("Move")){
            writer.write("Error : Move sequence contains wrong number of move steps. Input line ignored." + "\n" + "\n");
            writer.close();
        }
        else if(type.equals("Boundary")){
            writer.write("Error : Game board boundaries are exceeded. Input line ignored." + "\n" + "\n");
            writer.close();
        }

    }
    /*prints who won the game after it is over, takes the winner class as parameter*/
    public static void GameOver(String winner, String filename)throws IOException{
        FileWriter writer = new FileWriter(filename,true);
        writer.write("Game Finished" + "\n");
        writer.write(winner + " Wins");
        writer.close();
    }
    /*moves for calliance characters, map1 is ally map, map2 is enemy characters' map*/
    public static void moves(String name, int[] xy, HashMap<String,Calliance> map1, HashMap<String,Zorde> map2,
                             Object[][] board, ArrayList<Characters> allCharacters, String outputFile) throws BoundaryException, IOException {
        for(int i = 0; i<xy.length; i+=2){
            //boundary error check
            if(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].equals("**")
                    || board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].equals("*")){
                if(i != 0){
                    printBoard(board,allCharacters, outputFile);
                }
                throw new BoundaryException();
            }
            /*if position is empty*/
            else if(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].equals("  ")){
                board[map1.get(name).row][map1.get(name).column] = "  ";
                board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                map1.get(name).row+= xy[i+1];
                map1.get(name).column+= xy[i];
                /*will check every (a,b) ,in a square size of 5x5, to see If there is an enemy*/
                if(name.charAt(0) == 'E' && i == xy.length-2){
                    for(int a = (map1.get(name).row-2); a< (map1.get(name).row+3); a++){
                        for(int b = (map1.get(name).column-2); b<(map1.get(name).column+3);b++){
                            if(a>=0 && a<=board.length-1 && b>=0 && b<=board.length-1 ){
                                if (map2.containsKey(board[a][b].toString())){
                                    map2.get(board[a][b].toString()).HP-= Constants.elfAP;
                                    if(map2.get(board[a][b].toString()).HP <=0){
                                        allCharacters.remove(map2.get(board[a][b].toString()));
                                        map2.remove(board[a][b].toString());
                                        board[a][b] = "  ";
                                    }
                                }
                            }

                        }

                    }
                }
                /*will check every (a,b) ,in a square size of 3x3, to see If there is an enemy*/
                else if((name.charAt(0) != 'H') || (name.charAt(0) == 'H' && i == xy.length-2)){
                    for(int a = (map1.get(name).row-1); a< (map1.get(name).row+2); a++){
                        for(int b = (map1.get(name).column-1); b<(map1.get(name).column+2);b++){
                            if(a>=0 && a<=board.length-1 && b>=0 && b<=board.length-1 ){
                                if (map2.containsKey(board[a][b].toString())){
                                    if(name.charAt(0) == 'E'){
                                        map2.get(board[a][b].toString()).HP-= Constants.elfAP;
                                    }
                                    if(name.charAt(0) == 'D'){
                                        map2.get(board[a][b].toString()).HP-= Constants.dwarfAP;
                                    }
                                    if(name.charAt(0) == 'H'){
                                        map2.get(board[a][b].toString()).HP-= Constants.humanAP;
                                    }
                                    if(map2.get(board[a][b].toString()).HP <=0){
                                        allCharacters.remove(map2.get(board[a][b].toString()));
                                        map2.remove(board[a][b].toString());
                                        board[a][b] = "  ";
                                    }

                                }
                            }

                        }
                    }
                }
            }
            /*if position is occupied by an ally do nothing*/
            else if(map1.containsKey(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString())){
                break;
            }
            /*if position is occupied by an enemy, battle to death*/
            else if(map2.containsKey(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString())){
                /*attacking character will make it's attack*/
                if(name.charAt(0) == 'E'){map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=Constants.elfAP;}

                if(name.charAt(0) == 'D'){map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=Constants.dwarfAP;}

                if(name.charAt(0) == 'H'){map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=Constants.humanAP;}
                /*after that character with the most HP will stay at the position, other character will be removed
                from the board and other lists*/
                if(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP<=0){
                        allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                        map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                        board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                        board[map1.get(name).row][map1.get(name).column] = "  ";
                        map1.get(name).row+=xy[i+1];
                        map1.get(name).column+=xy[i];
                        break;
                    }


                if(map1.get(name).HP>map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP){
                    map1.get(name).HP-=map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP;
                    allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                    map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                    board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    map1.get(name).row+=xy[i+1];
                    map1.get(name).column+=xy[i];
                    break;
                }
                else if(map1.get(name).HP<map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP){
                    map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=map1.get(name).HP;
                    allCharacters.remove(map1.get(name));
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    map1.remove(name);
                    break;

                }
                /*if HPs are equal after a fight, but characters will be removed*/
                else if(map1.get(name).HP==map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP){
                    board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = "  ";
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                    map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                    allCharacters.remove(map1.get(name));
                    map1.remove(name);
                    break;
                }
            }
        }
    }
    public static void movesZorde(String name, int[] xy, HashMap<String,Zorde> map1, HashMap<String,Calliance> map2, Object[][] board, ArrayList<Characters> allCharacters,String outputFile) throws BoundaryException, IOException {
        for(int i = 0; i<xy.length; i+=2){
            //error check
            if(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].equals("**")
                    || board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].equals("*")){
                if(i != 0){
                    printBoard(board,allCharacters, outputFile);
                }
                throw new BoundaryException();
            }
            /*if position is empty
            * will heal allys if the character is ork and then check every (a,b) to see if there is an enemy
            * that it can attack on a 3*3 square*/
            else if(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].equals("  ")){
                if(name.charAt(0) == 'O'){
                    if(map1.get(name).HP + Constants.orkHealPoints <= Constants.orkHP){
                        map1.get(name).HP += Constants.orkHealPoints;
                    }
                    for(int a = (map1.get(name).row-1); a< (map1.get(name).row+2); a++){
                        for(int b = (map1.get(name).column-1); b<(map1.get(name).column+2);b++){
                            if (map1.containsKey(board[a][b].toString())) {
                                if ((board[a][b].toString()).charAt(0) == 'T' &&
                                        (map1.get(board[a][b].toString()).HP)+Constants.orkHealPoints<=Constants.trollHP){
                                    map1.get(board[a][b].toString()).HP+=Constants.orkHealPoints;
                                }
                                if ((board[a][b].toString()).charAt(0) == 'G' &&
                                        (map1.get(board[a][b].toString()).HP)+Constants.orkHealPoints<=Constants.goblinHP){
                                    map1.get(board[a][b].toString()).HP+=Constants.orkHealPoints;
                                }
                            }
                        }
                    }
                }
                board[map1.get(name).row][map1.get(name).column] = "  ";
                board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                map1.get(name).row+= xy[i+1];
                map1.get(name).column+= xy[i];
                /*will check every (a,b) to see If there is an enemy*/
                for(int a = (map1.get(name).row-1); a< (map1.get(name).row+2); a++){
                    for(int b = (map1.get(name).column-1); b<(map1.get(name).column+2);b++){
                        if(a>=0 && a<=board.length-1 && b>=0 && b<=board.length-1 ) {
                            if (map2.containsKey(board[a][b].toString())){
                                if(name.charAt(0) == 'O'){
                                    map2.get(board[a][b].toString()).HP-= Constants.orkAP;
                                }
                                if(name.charAt(0) == 'T'){
                                    map2.get(board[a][b].toString()).HP-= Constants.trollAP;
                                }
                                if(name.charAt(0) == 'G'){
                                    map2.get(board[a][b].toString()).HP-= Constants.goblinAP;
                                }
                                if(map2.get(board[a][b].toString()).HP <=0){
                                    allCharacters.remove(map2.get(board[a][b].toString()));
                                    map2.remove(board[a][b].toString());
                                    board[a][b] = "  ";
                                }

                            }
                        }

                        }
                    }
                }
            /*if position has an ally*/
            else if(map1.containsKey(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString())){
                if(name.charAt(0) == 'O'){
                    if(map1.get(name).HP + Constants.orkHealPoints <= Constants.orkHP){
                        map1.get(name).HP += Constants.orkHealPoints;
                    }
                    for(int a = (map1.get(name).row-1); a< (map1.get(name).row+2); a++){
                        for(int b = (map1.get(name).column-1); b<(map1.get(name).column+2);b++){
                            if (map1.containsKey(board[a][b].toString())) {
                                if ((board[a][b].toString()).charAt(0) == 'T' &&
                                        (map1.get(board[a][b].toString()).HP)+Constants.orkHealPoints<=Constants.trollHP){
                                    map1.get(board[a][b].toString()).HP+=Constants.orkHealPoints;
                                }
                                if ((board[a][b].toString()).charAt(0) == 'G' &&
                                        (map1.get(board[a][b].toString()).HP)+Constants.orkHealPoints<=Constants.goblinHP){
                                    map1.get(board[a][b].toString()).HP+=Constants.orkHealPoints;
                                }
                            }
                        }
                    }
                }
                break;
            }
            else if(map2.containsKey(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString())){
                if(name.charAt(0) == 'O'){
                    if(map1.get(name).HP + Constants.orkHealPoints <= Constants.orkHP){
                        map1.get(name).HP += Constants.orkHealPoints;
                    }
                    for(int a = (map1.get(name).row-1); a< (map1.get(name).row+2); a++){
                        for(int b = (map1.get(name).column-1); b<(map1.get(name).column+2);b++){
                            if (map1.containsKey(board[a][b].toString())) {
                                if ((board[a][b].toString()).charAt(0) == 'T' &&
                                        (map1.get(board[a][b].toString()).HP)+Constants.orkHealPoints<=Constants.trollHP){
                                    map1.get(board[a][b].toString()).HP+=Constants.orkHealPoints;
                                }
                                if ((board[a][b].toString()).charAt(0) == 'G' &&
                                        (map1.get(board[a][b].toString()).HP)+Constants.orkHealPoints<=Constants.goblinHP){
                                    map1.get(board[a][b].toString()).HP+=Constants.orkHealPoints;
                                }
                            }
                        }
                    }
                    map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=Constants.orkAP;}

                if(name.charAt(0) == 'T'){map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=Constants.trollAP;}

                if(name.charAt(0) == 'G'){map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=Constants.goblinAP;}
                if(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP<=0){
                    allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                    map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                    board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    map1.get(name).row+=xy[i+1];
                    map1.get(name).column+=xy[i];
                    break;
                }

                if(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP<=0){
                    allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                    map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                    board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    map1.get(name).row+=xy[i+1];
                    map1.get(name).column+=xy[i];
                    break;
                }
                else if(map1.get(name).HP>map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP){
                    map1.get(name).HP-=map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP;
                    allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                    map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                    board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = map1.get(name);
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    map1.get(name).row+=xy[i+1];
                    map1.get(name).column+=xy[i];
                    break;
                }
                else if(map1.get(name).HP<map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP){
                    map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP-=map1.get(name).HP;
                    allCharacters.remove(map1.get(name));
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    map1.remove(name);
                    break;

                }
                else if(map1.get(name).HP==map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()).HP){
                    board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]] = "  ";
                    board[map1.get(name).row][map1.get(name).column] = "  ";
                    allCharacters.remove(map2.get(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString()));
                    map2.remove(board[map1.get(name).row + xy[i+1]][map1.get(name).column + xy[i]].toString());
                    allCharacters.remove(map1.get(name));
                    map1.remove(name);
                    break;
                }
            }
        }
    }
}
