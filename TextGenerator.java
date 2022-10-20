public class TextGenerator {
    public static void main(String[] args) {

        // command line arguements
        int k = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        String text = StdIn.readAll();

        // a MarkovModel object.
        MarkovModel txtGen = new MarkovModel(text, k);

        // string representation of the contents of Markov
        StringBuilder kgram = new StringBuilder();

        String output = text.substring(0, k);
        System.out.print(output);
        kgram.append(output);

        // repeatedly generates and prints a new random character
        // in referemce to the Markov Model and updates the kgram.
        for (int i = 0; i < t - k; i++) {
            kgram.append(txtGen.random(kgram.toString()));
            System.out.print(kgram.charAt(k));
            kgram.deleteCharAt(0);

        }


    }
}
