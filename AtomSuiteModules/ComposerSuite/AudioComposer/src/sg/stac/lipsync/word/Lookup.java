package sg.stac.lipsync.word;

//Lookup.java
//Load phoneme dictionary into lookup table
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License
import sg.stac.utils.Utilities;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Lookup {

    HashMap hashMap = new HashMap();

    public Lookup() {

        DataInputStream dis = null;
        String text = null;

        try {
            // create a data input stream to read from
            InputStream fis = Utilities.openFile("dictionaries/cmudict.0.6d");
            BufferedInputStream bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // read line of text until end of file
            while ((text = dis.readLine()) != null) {

                // split the text into key and value
                int at = text.indexOf(" ");
                if (at > -1) {
                    // split into key and value
                    String key = text.substring(0, at);
                    String value = text.substring(at).trim();

                    // add to the hash map
                    hashMap.put(key, value);
                }
            }

        } catch (IOException e) {
            // catch io errors from FileInputStream or readLine() 
            Utilities.errMessage("Unable to open phonetic dictionary:\n" + e.toString());

        } finally {
            // was file opened? 
            if (dis != null) {
                try {
                    // close the file
                    dis.close();
                } catch (IOException ioe) {
                    Utilities.errMessage("File error while closing phonetic dictionary");
                }
            }
        }
    }

    public String get(String key) {
        if (hashMap.containsKey(key)) {
            return (String) hashMap.get(key);
        } else {
            return ("?");
        }

    }
}
