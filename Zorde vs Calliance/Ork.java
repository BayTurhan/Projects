public class Ork extends Zorde{
    public Ork(String name, int row, int column,int HP) {
        super(name, row, column, HP);
    }

    @Override
    public String toString() {
        return getName();
    }
}
