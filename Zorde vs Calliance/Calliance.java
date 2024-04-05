public class Calliance extends Characters {
    public Calliance(String name, int row, int column, int HP) {
        super(name, row, column, HP);
    }

    @Override
    public String toString() {
        return "Calliance{" +
                "name='" + getName() + '\'' +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
