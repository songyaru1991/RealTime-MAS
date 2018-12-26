package com.foxlink.realtime.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
	/*Gson解決 null value*/
	@Override
	public <T> TypeAdapter<T> create(Gson arg0, TypeToken<T> type) {
		Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class) {
            return null;
        }
        return (TypeAdapter<T>) new StringAdapter();
	}

}
