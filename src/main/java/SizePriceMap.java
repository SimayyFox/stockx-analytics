import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.*;

public class SizePriceMap extends HashMap<Double, ArrayList<Integer>> {
    TreeSet<Double> sizeSet;

    public SizePriceMap(TreeSet<Double> sizes) {
        sizeSet = sizes;
        for (Double size : sizes) {
            this.put(size, new ArrayList<Integer>());
        }
    }

    public TreeSet<Double> getSizeSet() {
        return sizeSet;
    }

    public ArrayList<Integer> get(double size) {
        return this.get((Double) size);
    }

    public void addEntry(double size, int price) {
        this.get((Double) size).add((Integer) price);
    }

    public ArrayList<Integer> getPrices(double size) {
        Double sizeref = (Double) size;

        if (this.get(sizeref) == null) {
            throw new IllegalArgumentException("Size does not exist!");
        }

        return this.get(sizeref);
    }

    public void putAllPrices(JsonArray json) {
        for (Double size : sizeSet) {
            putPrices(json, size);
        }
    }

    public void putPrices(JsonArray json, double size) {
        JsonObject jo;

        for (int i = 0; i < json.size(); i++) {
            jo = json.get(i).getAsJsonObject();
            if (jo.get("shoeSize").getAsDouble() == size) {
                this.addEntry(size, jo.get("amount").getAsInt());
            }
        }
    }

    public double mean(Double size) {
        double sum = 0;
        int place = 0;
        int ignored = 0;

        double dsize = (double) size;

        for (Integer price : this.get(dsize)) {

            //Adding the first price:
            if (place == 0) {
                sum += price;
            }
            else {

                //Adding the second price:
                if (place == 1) {
                    if (1.4 * this.get(dsize).get(1) < sum) {
                        sum = this.get(dsize).get(1);
                    }
                    else {
                        ignored++;
                    }
                }

                //Adding third or more price:
                else {
                    if (1.4 * this.get(dsize).get(place) < (sum / (place - ignored + 1))) {
                        sum += this.get(dsize).get(place);
                    }
                    else {
                        ignored++;
                    }
                }
            }
            place++;
        }
        sum /= (this.get((double) size).size() - ignored);
        return sum;
    }
}
