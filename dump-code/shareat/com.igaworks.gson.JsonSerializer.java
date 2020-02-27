package com.igaworks.gson;

import java.lang.reflect.Type;

public interface JsonSerializer<T> {
    JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext);
}