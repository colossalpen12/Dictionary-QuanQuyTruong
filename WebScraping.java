import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class WebScraping {
    public String getDescription(String word) {
        StringBuilder descriptions = new StringBuilder();
        String url = "https://www.merriam-webster.com/dictionary/";
        try {
            Document doc = Jsoup.connect(url+word).get();
            Element type = doc.select("span.fl").first();
            Elements definitions = doc.select("span.dtText");
            assert type != null;
            descriptions.append(type.text());
            descriptions.append("\n");
            for (Element element : definitions) {
                descriptions.append(element.text().substring(2));
                descriptions.append("\n");
            }
        } catch (Exception e) {
            return "Word not found!";
        }
        return descriptions.toString();
    }

   public List<String> getSuggestion(String word) {
        String url = "https://wordfind.com/starts-with/app/";
        List<String> Suggestions = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements suggestions = doc.select("section div ul li a");
            for (Element element : suggestions) {
                Suggestions.add(element.text());
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return Suggestions;
    }
}
