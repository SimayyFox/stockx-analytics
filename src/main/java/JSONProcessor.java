import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JSONProcessor {

    /**
     * Prepares access to the json in the BufferedReader.
     * @param r - the BufferedReader with json
     * @return sizepricemap - a java.map with each price mapped to a size
     */
    public static SizePriceMap initialize(BufferedReader r) {
        String jsonstring = r.lines().collect(Collectors.joining());
        JsonArray ja = JSONProcessor.getProductActivity(jsonstring);

        TreeSet<Double> sizes = JSONProcessor.getSizes(ja);
        Map<Double, ArrayList<Integer>> sizepricemap = new SizePriceMap(sizes);

        ((SizePriceMap) sizepricemap).putAllPrices(ja);

        return (SizePriceMap) sizepricemap;
    }

    /**
     * Returns a TreeSet with all the sizes.
     * @param json of the StockX ProductActivity
     * @return set with all the sizes
     */
    public static TreeSet<Double> getSizes(JsonArray json) {
        TreeSet<Double> set = new TreeSet<Double>();
        JsonObject jo;
        for (int i = 0; i < json.size(); i++) {
            jo = json.get(i).getAsJsonObject();
            set.add(jo.get("shoeSize").getAsDouble());
        }
        return set;
    }

    /**
     * Transforms the input string to a JsonArray, member ProductActivity.
     * @param r - the input string
     * @return ja - JsonArray
     */
    public static JsonArray getProductActivity(String r) {
        JsonObject jo = JsonParser.parseString(r).getAsJsonObject();
        JsonArray ja = jo.getAsJsonArray("ProductActivity");
        return ja;
    }

    /**
     * Uses the input BufferedReader to extract the StockX SKU and product name.
     * @param r - BufferedReader of the search json file
     * @return array of two strings which contains product SKU and name
     */
    public static String[] getSKU(BufferedReader r) {
        String[] skuname = new String[2];
        String jsonstring = r.lines().collect(Collectors.joining());
        JsonObject jo = JsonParser.parseString(jsonstring).getAsJsonObject();
        JsonArray ja = jo.getAsJsonArray("Products");
        JsonElement je = ja.get(0);
        JsonObject jo2 = je.getAsJsonObject();
        skuname[0] = jo2.get("id").getAsString();
        skuname[1] = jo2.get("title").getAsString();
        return skuname;
    }
}
