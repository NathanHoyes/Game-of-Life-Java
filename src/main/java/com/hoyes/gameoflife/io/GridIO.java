package main.java.com.hoyes.gameoflife.io;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GridIO {
    public enum Type {
        GLIDER("./Data/gliders.json"),
        GAME("./Data/game");

        public final String filePath;

        private Type(String filePath) {
            this.filePath = filePath;
        }
    }

    private final JSONObject jsonObject;
    private final Iterator<String> iterator;

    public GridIO (Type dataType) {
        jsonObject = getObjectFromFile(dataType.filePath);
        iterator = jsonObject.keys();
    }

    /*
     * Accessors
     */

    public JSONObject getJsonObject() {
        return this.jsonObject;
    }

    public Iterator<String> getIterator() {
        return this.iterator;
    }

    /*
     * Mutators
     */

    /*
     * JSON Helpers
     */

    public JSONObject getObjectFromFile(String filePath) {
        String jsonString = null;
        try {
            jsonString = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new JSONObject(jsonString);
    }

    public boolean[][] jsonArrayToArray(JSONArray jsonArray) {

        System.out.println("Input jsonArray: " + jsonArray);

        boolean[][] outArray = null;
        if (jsonArray != null) {
            outArray = new boolean[jsonArray.length()][jsonArray.getJSONArray(0).length()];
            for (int y = 0; y < jsonArray.length(); y++) {
                for (int x = 0; x < jsonArray.getJSONArray(y).length(); x++) {
                    outArray[y][x] = binaryToBoolean(jsonArray.getJSONArray(y).getInt(x));
                }
            }
        }

        System.out.println("Output Array: " + Arrays.deepToString(outArray));
        return outArray;
    }

    public boolean binaryToBoolean(int number) {
        return number == 1;
    }

    /*
     * Iterator Helpers
     */

    public GridData nextGrid() {
        GridData output = null;
        Iterator<String> iterator = getIterator();
        JSONObject jsonObject = getJsonObject();
        if ( iterator.hasNext() ) {
            String key = iterator.next();
            boolean[][] grid = jsonArrayToArray(jsonObject.getJSONArray(key));
            output = new GridData(key, grid);
        }
        return output;
    }

    public boolean hasNext() {
        return getIterator().hasNext();
    }
}
