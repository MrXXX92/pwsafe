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


    public static boolean writeXML(List<PasswordItem> passwords){
        try {
            String state = Environment.getExternalStorageState();
            File file = null;

            if(Environment.MEDIA_MOUNTED.equals(state)) {
                File path = Environment.getExternalStorageDirectory();

                if (!path.exists()) {
                    path.mkdirs();
                }
                try {
                    file = new File(path, xmlFile);
                    file.createNewFile();
                }
                catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            }

            FileOutputStream fileos = new FileOutputStream (file);
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
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<PasswordItem> readXML(){
        List<PasswordItem> userData = new ArrayList<PasswordItem>();
        String data = "";
        try {
            File file = new File(Environment.getExternalStorageDirectory(), xmlFile);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            isr.close();
            fis.close();
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

        String lastTextElement = "";
        PasswordItem pwi = new PasswordItem("", "", "");
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = xpp.getName();
            switch (eventType){
                case XmlPullParser.START_TAG:
                    if(name.equals("loginData")){
                        pwi = new PasswordItem("", "", "");
                    }
                    break;
                case XmlPullParser.TEXT:
                    lastTextElement = xpp.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if(name.equals(description)){
                        pwi.setDescription(lastTextElement);
                    }
                    else if(name.equals(userName)){
                        pwi.setPUsername(lastTextElement);
                    }
                    else if(name.equals(password)){
                        pwi.setPassword(lastTextElement);
                    }
                    else if(name.equals("loginData")){
                        userData.add(pwi);
                    }
                    break;
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
