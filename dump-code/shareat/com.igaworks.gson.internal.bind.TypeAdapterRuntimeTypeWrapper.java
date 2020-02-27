package com.igaworks.gson.internal.bind;

import com.igaworks.gson.Gson;
import com.igaworks.gson.TypeAdapter;
import com.igaworks.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.igaworks.gson.reflect.TypeToken;
import com.igaworks.gson.stream.JsonReader;
import com.igaworks.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson context2, TypeAdapter<T> delegate2, Type type2) {
        this.context = context2;
        this.delegate = delegate2;
        this.type = type2;
    }

    public T read(JsonReader in) throws IOException {
        return this.delegate.read(in);
    }

    public void write(JsonWriter out, T value) throws IOException {
        TypeAdapter chosen = this.delegate;
        Type runtimeType = getRuntimeTypeIfMoreSpecific(this.type, value);
        if (runtimeType != this.type) {
            TypeAdapter runtimeTypeAdapter = this.context.getAdapter(TypeToken.get(runtimeType));
            if (!(runtimeTypeAdapter instanceof Adapter)) {
                chosen = runtimeTypeAdapter;
            } else if (!(this.delegate instanceof Adapter)) {
                chosen = this.delegate;
            } else {
                chosen = runtimeTypeAdapter;
            }
        }
        chosen.write(out, value);
    }

    private Type getRuntimeTypeIfMoreSpecific(Type type2, Object value) {
        if (value == null) {
            return type2;
        }
        if (type2 == Object.class || (type2 instanceof TypeVariable) || (type2 instanceof Class)) {
            return value.getClass();
        }
        return type2;
    }
}