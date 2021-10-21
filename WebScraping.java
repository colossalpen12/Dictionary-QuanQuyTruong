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
            System.out.println(type.text());
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
        String url = "https://www.niftyword.com/prefix-suffix-derived/";
        List<String> Suggestions = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url+word+'/').get();
            Elements suggestions = doc.select("ul li div h4");
            for (Element element : suggestions) {
                Suggestions.add(element.text());
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return Suggestions;
    }
}