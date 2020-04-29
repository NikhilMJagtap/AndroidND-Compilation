package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName, placeOfOrigin, description, image;
        ArrayList<String> alsoKnownAs= new ArrayList<String>();
        ArrayList<String> ingredients= new ArrayList<String>();
        try{
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject("name");
            mainName = nameObject.getString("mainName");
            JSONArray jsonArrayAlsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
            alsoKnownAs = jsonArrayToArrayList(jsonArrayAlsoKnownAs);
            placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            description = sandwichObject.getString("description");
            image = sandwichObject.getString("image");
            JSONArray jsonArrayIngredients = sandwichObject.getJSONArray("ingredients");
            ingredients = jsonArrayToArrayList(jsonArrayIngredients);
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String> jsonArrayToArrayList(final JSONArray jsonArray){
        ArrayList<String> arrayList = new ArrayList<>();
        if(jsonArray!= null){
            for(int i=0; i<jsonArray.length(); ++i){
                try{
                    arrayList.add(jsonArray.getString(i));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return arrayList;
        }
        return null;
    }
}
