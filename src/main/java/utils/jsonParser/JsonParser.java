package utils.jsonParser;

import java.lang.reflect.Type;

public interface JsonParser {

    <T> T fromJson(String json, Type typeOfT);

    String toJson(Object src);

}
