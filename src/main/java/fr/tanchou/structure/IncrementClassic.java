package fr.tanchou.structure;

public class IncrementClassic implements Increment {

    private int increment;

    public IncrementClassic() {
        this.increment = 0;
    }

    @Override
    public int increment() {
        increment++;
        return increment;
    }

    @Override
    public void decrement() {
        increment--;
    }

    @Override
    public int getIncrement() {
        return 0;
    }

    @Override
    public void setIncrement(int value) {
        increment = value;
    }
}
