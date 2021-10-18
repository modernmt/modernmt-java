package com.modernmt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpVersion;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

class HttpClient {

    private final String baseUrl;
    private final Map<String, String> headers;
    private final JsonResponseHandler responseHandler;
    private final Gson gson;

    HttpClient(String baseUrl, Map<String, String> headers) {
        this.baseUrl = baseUrl;
        this.headers = headers;
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
        this.responseHandler = new JsonResponseHandler();
    }

    <T> T send(Class<T> cls, String method, String path, Map<String, Object> data) throws IOException {
        return this.send(cls, method, path, data, null);
    }

    <T> T send(Class<T> cls, String method, String path, Map<String, Object> data,
               Map<String, File> files) throws IOException {
        Request request = Request.post(this.baseUrl + path)
                .version(HttpVersion.HTTP_1_1)
                .setHeader("X-HTTP-Method-Override", method);

        for (Map.Entry<String, String> el : this.headers.entrySet())
            request.setHeader(el.getKey(), el.getValue());

        if (files != null) {
            MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();

            for (Map.Entry<String, Object> el : data.entrySet()) {
                Object value = el.getValue();

                if (value instanceof List)
                    value = ((List<?>) value).stream().map(String::valueOf).collect(Collectors.joining(","));

                if (value instanceof long[])
                    value = LongStream.of((long[])value).mapToObj(Long::toString).collect(Collectors.joining(","));

                multipartBuilder.addTextBody(el.getKey(), value.toString());
            }

            for (Map.Entry<String, File> el : files.entrySet())
                multipartBuilder.addBinaryBody(el.getKey(), el.getValue());

            request.body(multipartBuilder.build());
        }
        else if (data != null) {
            String body = this.gson.toJson(data);
            request.bodyString(body, ContentType.APPLICATION_JSON);
        }

        String response = request.execute().handleResponse(this.responseHandler);
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();

        int status = json.get("status").getAsInt();
        if (status >= 300 || status < 200) {
            JsonObject error = json.get("error").getAsJsonObject();
            throw new ModernMTException(status, error.get("type").getAsString(), error.get("message").getAsString());
        }

        return this.gson.fromJson(json.get("data"), cls);
    }

    private static class JsonResponseHandler implements HttpClientResponseHandler<String> {

        @Override
        public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
            return EntityUtils.toString(response.getEntity());
        }

    }

}
