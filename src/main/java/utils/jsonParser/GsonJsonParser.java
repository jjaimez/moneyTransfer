package utils.jsonParser;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.text.DateFormat;

public enum GsonJsonParser implements JsonParser {
    INSTACE;

    final Gson parser = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    public <T> T fromJson(String json, Type typeOfT) {
        return parser.fromJson(json,typeOfT);
    }

    public String toJson(Object object) {
        return parser.toJson(object);
    }


}
