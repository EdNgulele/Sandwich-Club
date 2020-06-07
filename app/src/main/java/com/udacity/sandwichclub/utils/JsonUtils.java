package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();


    public static Sandwich parseSandwichJson(String json, Context context) {

        String nameMain = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;
        List<String> alsoKnownAs = null;


        try {
            //creating the Json Object
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject(context.getString(R.string.name));

            nameMain = name.getString(context.getString(R.string.name_main));
            placeOfOrigin = sandwichJson.getString(context.getString(R.string.place_origin));
            description = sandwichJson.getString(context.getString(R.string.description));
            image = sandwichJson.getString(context.getString(R.string.image));

            JSONArray arrayAlsonKnown = name.getJSONArray(context.getString(R.string.alsoKnownAs));
            JSONArray arrayIngredients = sandwichJson.getJSONArray(context.getString(R.string.ingredients));

            alsoKnownAs = jsonToLis(arrayAlsonKnown);
            ingredients = jsonToLis(arrayIngredients);


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return new Sandwich(nameMain, alsoKnownAs, placeOfOrigin, description, image, ingredients);

    }

    private static List<String> jsonToLis(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
