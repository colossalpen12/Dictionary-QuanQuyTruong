package tudienbachkhoa.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslateAPI {
    /** testing case. **/
    public static void main(String[] args) throws IOException {
        String text = "Táo";
        System.out.println("Translated text: " + translate("vi", "en", text));
    }

    /** The API itself using Google Scripts. **/
    public static String translate(String langIn, String langOut, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbxzHxPsTGHkHf8gAIQ-B2RZBlQmDNy8mkmqNrAnIXFxOyTYgOr7ZraeU-T5VZvrj8ITtA/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langOut +
                "&source=" + langIn;
        URL url = new URL(urlStr);
        StringBuilder translatedText = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            translatedText.append(inputLine);
        }
        in.close();
        return translatedText.toString();
    }
}
