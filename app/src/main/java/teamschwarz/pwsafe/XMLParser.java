package teamschwarz.pwsafe;

import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitsch on 09.01.15.
 */
public class XMLParser {

    static final String xmlFile = "PWSafe.xml";
    static final String description = "description";
    static final String userName = "username";
    static final String password = "password";


    public static void writeXML(List<PasswordItem> passwords){
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "PWSafe" + "/" + xmlFile);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileos = new FileOutputStream (file);
            //FileOutputStream fos = new  FileOutputStream(xmlFile);
            //FileOutputStream fileos = getApplicationContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);

            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            for (int i = 0; i < passwords.size(); i++){
                xmlSerializer.startTag(null, "loginData");
                xmlSerializer.startTag(null, description);
                xmlSerializer.text(passwords.get(i).getDescription());
                xmlSerializer.endTag(null, description);
                xmlSerializer.startTag(null, userName);
                xmlSerializer.text(passwords.get(i).getUsername());
                xmlSerializer.endTag(null, userName);
                xmlSerializer.startTag(null, password);
                xmlSerializer.text(passwords.get(i).getPassword());
                xmlSerializer.endTag(null, password);
                xmlSerializer.endTag(null, "loginData");
            }

            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<PasswordItem> readXML(){
        List<PasswordItem> userData = new ArrayList<PasswordItem>();
        String data = "";
        try {
            FileInputStream fis = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "PWSafe" + "/" + xmlFile));
            //FileInputStream fis = getApplicationContext().openFileInput(xmlFile);

            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            isr.close();
            fis.close();
        }
        catch (FileNotFoundException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
        }
        catch (XmlPullParserException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
        try {
            xpp = factory.newPullParser();
        }
        catch (XmlPullParserException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            xpp.setInput(new StringReader(data));
        }
        catch (XmlPullParserException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = xpp.getEventType();
        }
        catch (XmlPullParserException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            }
            else if (eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag "+xpp.getName());
            }
            else if (eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag "+xpp.getName());
            }
            else if(eventType == XmlPullParser.TEXT) {

                //Erstmal ein Objekt mit leeren Strings erzeugen und Strings nachher füllen
                PasswordItem pwi = new PasswordItem("", "", "");
                if (xpp.getName() == description){
                    pwi.setDescription(xpp.getText());
                }
                else if (xpp.getName() == userName){
                    pwi.setDescription(xpp.getText());
                }
                else if (xpp.getName() == password){
                    pwi.setDescription(xpp.getText());
                }

                //Der Liste das zuvor erstellte Objekt hinzufügen
                userData.add(pwi);
            }
            try {
                eventType = xpp.next();
            }
            catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return userData;
    }
}
