public abstract class Characters implements Comparable<Characters> {
    private String name;
    public int row;
    public int column;
    public int HP;

    public Characters(String name, int row, int column, int HP) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.HP = HP;
    }

    public String getName() {
        return name;
    }
    @Override
    public int compareTo(Characters ch) {
        return this.getName().compareTo(ch.getName()); //compares names of characters
    }
}
