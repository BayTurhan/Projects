public class Elf extends Calliance {
    public Elf(String name, int row, int column, int HP) {
        super(name, row, column, HP);
    }

    @Override
    public String toString() {
        return getName();
    }

}
