public class Human extends Calliance{
    public Human(String name, int row, int column,int HP) {
        super(name, row, column, HP);
    }

    @Override
    public String toString() {
        return getName();
    }
}
