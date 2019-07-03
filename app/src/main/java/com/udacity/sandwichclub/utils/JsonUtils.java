package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject name = obj.getJSONObject("name");

            sandwich.setMainName(name.getString("mainName"));
            sandwich.setAlsoKnownAs( convertJsonArrayToList(name.getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(obj.getString("placeOfOrigin"));
            sandwich.setDescription(obj.getString("description"));
            sandwich.setImage(obj.getString("image"));
            sandwich.setIngredients( convertJsonArrayToList(obj.getJSONArray("ingredients")));

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
