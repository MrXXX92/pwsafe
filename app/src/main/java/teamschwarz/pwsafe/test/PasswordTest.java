package teamschwarz.pwsafe.test;

import android.test.InstrumentationTestCase;

import teamschwarz.pwsafe.utils.Vigenere;

/**
 * Created by Lukas on 19.01.2015.
 */
public class PasswordTest extends InstrumentationTestCase{

    public void testVerfahrenKorrekt(){
        String original = "Hallo Welt, ich werde verschluesselt.";
        String passwort = "TestPasswort";

        String verschluesselt = Vigenere.verschluesseln(original.toCharArray(), passwort.toCharArray());

        String entschluesselt = Vigenere.entschluesseln(verschluesselt.toCharArray(), passwort.toCharArray());

        assertTrue("Sind gleich", entschluesselt.equals(original));
    }

    public void testVerfahrenMitZweiSchluesseln(){
        String original = "Hallo Welt, ich werde verschluesselt.";
        String passwortVer = "TestPasswort";
        String passwortEnt = "FalschesPasswort";

        String verschluesselt = Vigenere.verschluesseln(original.toCharArray(), passwortVer.toCharArray());

        String entschluesselt = Vigenere.entschluesseln(verschluesselt.toCharArray(), passwortEnt.toCharArray());



        assertTrue("Sind nicht gleich", !entschluesselt.equals(original));
    }
}
