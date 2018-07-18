package com.android.framework.jc.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/6/23 11:27
 * @describe
 * @update
 */
public class FkJsonConverterFactory extends Converter.Factory {
    public static FkJsonConverterFactory create() {
        return create(new Gson());
    }

    public static FkJsonConverterFactory create(Gson gson) {
        return new FkJsonConverterFactory(gson);
    }

    private final Gson gson;

    private FkJsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    public static class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final static MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final static Charset UTF_8 = Charset.forName("UTF-8");
        private final Gson mGson;
        private final TypeAdapter<T> mAdapter;

        public GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.mGson = gson;
            this.mAdapter = adapter;
        }

        @Override
        public RequestBody convert(@NonNull T value) throws IOException {
            Buffer buffer = new Buffer();
            OutputStreamWriter writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = mGson.newJsonWriter(writer);
            mAdapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    public static class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final static Charset UTF_8 = Charset.forName("UTF-8");
        private final Gson mGson;
        private final TypeAdapter<T> mAdapter;

        public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.mGson = gson;
            this.mAdapter = adapter;
        }

        @Override
        public T convert(@NonNull ResponseBody responseBody) throws IOException {
            JsonReader jsonReader = mGson.newJsonReader(responseBody.charStream());
            jsonReader.setLenient(true);
            try {
                return mAdapter.read(jsonReader);
            }  finally {
                responseBody.close();
            }

        }
    }

}
