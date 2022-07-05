public class MyHashing {
    private int seed = 100;
    private void newInput;


    public int hash(String newInput) {
        int sum = 0;
        for (int i : newInput.toCharArray()) {
            if (Character.isDigit(newInput.toCharArray()[i])) {
                sum += newInput.toCharArray()[i];
            }

        }
        return sum;
    }

    public int hash(int newInput) {
        int oldSeed = seed;
        seed = newInput;
        return oldSeed;
    }

}