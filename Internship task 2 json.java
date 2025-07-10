import java.util.HashMap;
import java.util.Map;

public class JSONObject {
    private final Map<String, Object> map = new HashMap<>();

    public JSONObject(String json) {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        }

        int braceDepth = 0;
        StringBuilder current = new StringBuilder();
        String key = null;

        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);
            if (ch == '{') braceDepth++;
            if (ch == '}') braceDepth--;
            if (ch == ':' && braceDepth == 0 && key == null) {
                key = current.toString().replaceAll("\"", "").trim();
                current.setLength(0);
            } else if (ch == ',' && braceDepth == 0) {
                String value = current.toString().trim();
                addPair(key, value);
                key = null;
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        if (key != null && current.length() > 0) {
            addPair(key, current.toString().trim());
        }
    }

    private void addPair(String key, String value) {
        try {
            if (value.startsWith("{")) {
                map.put(key, new JSONObject(value));
            } else if (value.startsWith("\"") && value.endsWith("\"")) {
                map.put(key, value.substring(1, value.length() - 1));
            } else if (value.contains(".")) {
                map.put(key, Double.parseDouble(value));
            } else {
                map.put(key, Integer.parseInt(value));
            }
        } catch (Exception e) {
            map.put(key, value);
        }
    }

    public String getString(String key) {
        Object value = map.get(key);
        return (value != null) ? value.toString() : null;
    }

    public int getInt(String key) {
        Object value = map.get(key);
        return (value instanceof Integer) ? (int) value : Integer.parseInt(value.toString());
    }

    public double getDouble(String key) {
        Object value = map.get(key);
        return (value instanceof Double) ? (double) value : Double.parseDouble(value.toString());
    }

    public JSONObject getJSONObject(String key) {
        return (JSONObject) map.get(key);
    }
}
