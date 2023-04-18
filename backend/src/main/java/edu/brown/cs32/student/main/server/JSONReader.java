package edu.brown.cs32.student.main.server;
import java.io.IOException;
import java.util.Map;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

public class JSONReader {
    public JSONReader(String jsonString) {
    }

    public Map<String, Object> readJSON (String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(Types.newParameterizedType(Map.class, String.class, Object.class));
        return jsonAdapter.fromJson(json);
    }
}
