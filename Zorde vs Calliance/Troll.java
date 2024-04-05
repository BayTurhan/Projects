public class Troll extends Zorde{
    public Troll(String name, int row, int column,int HP) {
        super(name, row, column, HP);
    }
    @Override
    public String toString() {
        return getName();
    }
}
