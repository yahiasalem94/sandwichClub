package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_JSON_KEY = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject name = obj.optJSONObject(NAME_JSON_KEY);

            sandwich.setMainName(name.optString(MAIN_NAME, NAME_JSON_KEY));
            sandwich.setAlsoKnownAs( convertJsonArrayToList(name.optJSONArray(ALSO_KNOWN_AS)));
            sandwich.setPlaceOfOrigin(obj.optString(PLACE_OF_ORIGIN, NAME_JSON_KEY));
            sandwich.setDescription(obj.optString(DESCRIPTION, NAME_JSON_KEY));
            sandwich.setImage(obj.optString(IMAGE, NAME_JSON_KEY));
            sandwich.setIngredients( convertJsonArrayToList(obj.optJSONArray(INGREDIENTS)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    public static ArrayList<String> convertJsonArrayToList(JSONArray arr) throws JSONException {

        ArrayList<String> list = new ArrayList<>();
        if( arr != null && arr.length()>0 ) {
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getString(i));
            }
        }
        return list;
    }
}
