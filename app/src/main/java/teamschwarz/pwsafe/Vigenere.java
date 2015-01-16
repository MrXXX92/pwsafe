package teamschwarz.pwsafe;

/**
 * Created by lhu on 16.01.2015.
 */
public class Vigenere {

    public static String verschluesseln(char[] plain, char[] key){
        char[] output = new char[plain.length];

        for(int i = 0; i < plain.length; i++){
            int result = (plain[i] + key[i % key.length])%128;
            output[i] = (char) result;
        }
        StringBuffer out = new StringBuffer();
        for(int j = 0; j < output.length; j++){
            out.append(output[j]);
        }
        return out.toString();
    }

    public static String entschluesseln(char[] text, char[] key){
        char[] output = new char[text.length];
        for(int i = 0; i < text.length; i++){
            int result;
            if (text[i] - key[i%key.length] < 0){
                result = (text[i] - key[i%key.length]) + 128;
            }else{
                result = (text[i] - key[i % key.length]) % 128;
            }
            output[i] = (char) result;
        }
        StringBuffer out = new StringBuffer();
        for(int j = 0; j < output.length; j++){
            out.append(output[j]);
        }
        return out.toString();
    }

}
