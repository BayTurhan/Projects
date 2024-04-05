public class Zorde extends Characters{
    public Zorde(String name, int row, int column, int HP) {
        super(name, row, column, HP);
    }

    @Override
    public String toString() {
        return "Zorde{" +
                "name='" + getName() + '\'' +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
