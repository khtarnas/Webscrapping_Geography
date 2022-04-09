import java.util.ArrayList;

public class nameAndNumberLists {
    private ArrayList<String> names;
    private ArrayList<Integer> numbers;

    public nameAndNumberLists(ArrayList<String> names, ArrayList<Integer> numbers) {
        this.names = names;
        this.numbers = numbers;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    /**
     * Sorts both lists with respect to the numbers list (smallest to largest)
     */
    public void sort() {

        int n = names.size();

        // Brute force it!
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {

                    // switcheroo time
                    int tempNum = numbers.get(j);
                    numbers.set(j, numbers.get(j + 1));
                    numbers.set(j+1, tempNum);

                    //switcheroo time for names!
                    String tempName = names.get(j);
                    names.set(j, names.get(j + 1));
                    names.set(j+1, tempName);
                }
            }
        }
    }
}
