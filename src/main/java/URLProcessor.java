import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class URLProcessor {

    /**
     * Returns a BufferedReader for the sale JSON of the SKU's product.
     * @param sku is the StockX product id
     * @return a bufferedreader
     * @throws IOException if method can not find page
     */
    public static BufferedReader getReader(String sku) throws IOException {
        InputStream is;
        URLConnection con;

        System.out.println("\nThank you, processing SKU " + sku.toString() + "...");

        URL url = new URL("https://stockx.com/api/products/" + sku + "/activity?state=480&currency=EUR&limit=1000&page=1&sort=createdAt&order=DESC&country=NL");

        try {
            con = url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
            is = con.getInputStream();
        }
        catch (IOException e) {
            throw new IOException("Cannot retrieve website. You have most likely been blocked by the bot-protection-system.\n" +
                    "Please open the following link in your browser and solve the captcha: https://stockx.com/api/products/" +
                    sku + "/activity?state=480&currency=EUR&limit=1000&page=1&sort=createdAt&order=DESC&country=NL");
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

        return r;
    }

    public static String encode(String query) {
        String encoded = "";
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ') {
                encoded += "%20";
            }
            encoded += query.charAt(i);
        }
        return encoded;
    }

    public static BufferedReader getSearchReader(String query) throws IOException {
        InputStream is;
        URLConnection con;

        URL url = new URL("https://stockx.com/api/browse?&_search=" + encode(query));

        try {
            con = url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
            is = con.getInputStream();
        }
        catch (IOException e) {
            throw new IOException("Cannot retrieve website. You have most likely been blocked by the bot-protection-system.\n" +
                    "Please open the following link in your browser and solve the captcha: https://stockx.com");
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

        return r;
    }
}
