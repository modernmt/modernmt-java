package com.modernmt.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Model {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Override
    public String toString() {
        return gson.toJson(this);
    }

}
