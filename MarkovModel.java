public class MarkovModel {
    private static final int ASCII = 128; // limited input text character sequence
    private int order; // the size of the kgrams
    private ST<String, Integer> st1; // represents kgram with frequency values
    private ST<String, int[]> st2; // represents kgrams and char that follow


    // creates a Markov model of order k based on the specified text
    public MarkovModel(String text, int k) {
        order = k;
        String textfile = text;


        // creates the two symbol tables.
        st1 = new ST<String, Integer>();
        st2 = new ST<String, int[]>();

        // creates a circular version of the input text.
        for (int i = 0; i < k; i++) {
            textfile = textfile + text.charAt(i);
        }
        // creates the kgrams and insets them on the first symbol table
        for (int i = 0; i < text.length(); i++) {
            String kgram = textfile.substring(i, i + order);

            // if such kgram does not exist, then insert it with a value of 1.
            if (!st1.contains(kgram)) {
                st1.put(kgram, 1);
            }
            // if such kgram does exist, then increment kgram value by 1.
            else {
                st1.put(kgram, (st1.get(kgram) + 1));
            }
        }
        // notes the character frequencies for the kgram.
        for (int i = 0; i < text.length(); i++) {
            String kgram = textfile.substring(i, i + order);
            char c = textfile.charAt(i + order);
            // if such kgram doesn't exist, then add it.
            if (!st2.contains(kgram)) {
                int[] array = new int[ASCII];
                array[c]++;
                st2.put(kgram, array);
            }
            else {
                // increment the kgram with its respective char.
                add(kgram, c);
            }
        }
    }

    // adds to the second symbol table if the frequnecy of char exists.
    private void add(String kgram, char c) {
        int[] array = st2.get(kgram);
        array[c]++;
        st2.put(kgram, array);

    }


    // returns the order of the model (also known as k)
    public int order() {
        return order;

    }

    // returns a String representation of the model (more info below)
    public String toString() {
        // this involves the second symbol table.
        String result = "";
        for (String key : st2) {
            // sets up a line with the specified kgram
            result += key + ": ";

            // get the character frequency array
            int[] frequency = st2.get(key);

            // for each non-zero entry, append the character and the frequency
            // trailing space is allowed
            for (int i = 0; i < frequency.length; i++) {
                if (frequency[i] != 0) {
                    result += (char) i;
                    result += " ";
                    result += frequency[i];
                    result += " ";
                }
            }


            // append a newline character
            result += ("\n");

        }
        return result;
    }

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram) {
        // does the kgram even fit the condition of order size?
        if (kgram.length() != order)
            throw new IllegalArgumentException("kgram is not of length k");
            // if such kgram does not exist, then frequency is 0.
        else if (!st1.contains(kgram))
            return 0;
        else
            // get the value of kgram.
            return (st1.get(kgram));

    }

    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException("kgram is not of length");
        }
        else if (!st2.contains(kgram)) {
            return 0;
        }
        else
            return st2.get(kgram)[c];

    }

    // returns a random character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char random(String kgram) {

        if (!st2.contains(kgram) || kgram.length() != order) {
            throw new IllegalArgumentException("kgram does not appear in the"
                                                       + "text");
        }
        else {
            // retrives the value of the kgram.
            int[] value = st2.get(kgram);
            return (char) StdRandom.discrete(value);
        }

    }

    // tests all instance methods to make sure they're working as expected
    public static void main(String[] args) {
        String text1 = "banana";
        MarkovModel model1 = new MarkovModel(text1, 2);
        StdOut.println("freq(\"an\", 'a')    = " + model1.freq("an", 'a'));
        StdOut.println("freq(\"na\", 'b')    = " + model1.freq("na", 'b'));
        StdOut.println("freq(\"na\", 'a')    = " + model1.freq("na", 'a'));
        StdOut.println("freq(\"na\")         = " + model1.freq("na"));
        StdOut.println();

        String text3 = "one fish two fish red fish blue fish";
        MarkovModel model3 = new MarkovModel(text3, 4);
        StdOut.println("freq(\"ish \", 'r') = " + model3.freq("ish ", 'r'));
        StdOut.println("freq(\"ish \", 'x') = " + model3.freq("ish ", 'x'));
        StdOut.println("freq(\"ish \")      = " + model3.freq("ish "));
        StdOut.println("freq(\"tuna\")      = " + model3.freq("tuna"));


    }
}
