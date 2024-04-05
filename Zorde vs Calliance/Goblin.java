public class Goblin extends Zorde{
    public Goblin(String name, int row, int column,int HP) {
        super(name, row, column, HP);
    }

    @Override
    public String toString() {
        return getName();
    }
}
