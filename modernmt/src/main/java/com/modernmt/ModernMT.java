package com.modernmt;

import com.modernmt.model.TranslateOptions;
import com.modernmt.model.Translation;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ModernMT {

    private final HttpClient httpClient;
    public final MemoryServices memories;

    public ModernMT(String apiKey, String platform, String platformVersion) {
        Map<String, String> headers = new HashMap<>();

        headers.put("MMT-ApiKey", apiKey);
        headers.put("MMT-Platform", platform);
        headers.put("MMT-PlatformVersion", platformVersion);

        this.httpClient = new HttpClient("https://api.modernmt.com", headers);

        this.memories = new MemoryServices(this.httpClient);
    }

    public ModernMT(String apiKey, String platform) {
        this(apiKey, platform, "1.0.0");
    }

    public ModernMT(String apiKey) {
        this(apiKey, "modernmt-java");
    }

    public List<String> listSupportedLanguages() throws IOException {
        return Arrays.asList(this.httpClient.send(String[].class, "get", "/translate/languages", null));
    }

    // single sentence

    public Translation translate(String source, String target, String q) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), null, null, null).get(0);
    }

    public Translation translate(String source, String target, String q, long[] hints) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, null, null).get(0);
    }

    public Translation translate(String source, String target, String q, long[] hints,
                                 String contextVector) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, contextVector, null).get(0);
    }

    public Translation translate(String source, String target, String q, long[] hints, String contextVector,
                                 TranslateOptions options) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, contextVector, options).get(0);
    }

    // multiple sentences

    public List<Translation> translate(String source, String target, List<String> q) throws IOException {
        return this.translate(source, target, q, null, null, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, long[] hints) throws IOException {
        return this.translate(source, target, q, hints, null, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, long[] hints,
                                       String contextVector) throws IOException {
        return this.translate(source, target, q, hints, contextVector, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, long[] hints,
                                       String contextVector, TranslateOptions options) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("target", target);
        data.put("q", q);

        if (source != null)
            data.put("source", source);
        if (hints != null)
            data.put("hints", hints);
        if (contextVector != null)
            data.put("context_vector", contextVector);

        if (options != null) {
            String priority = options.getPriority();
            String projectId = options.getProjectId();
            Boolean multiline = options.getMultiline();
            Integer timeout = options.getTimeout();

            if (priority != null)
                data.put("priority", priority);
            if (projectId != null)
                data.put("project_id", projectId);
            if (multiline != null)
                data.put("multiline", multiline);
            if (timeout != null)
                data.put("timeout", timeout);
        }

        return Arrays.asList(this.httpClient.send(Translation[].class, "get", "/translate", data));
    }

    // single target

    public String getContextVector(String source, String targets, String text) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, null, null).get(targets);
    }

    public String getContextVector(String source, String targets, String text, long[] hints) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, hints, null).get(targets);
    }

    public String getContextVector(String source, String targets, String text, long[] hints,
                                   Integer limit) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, hints, limit).get(targets);
    }

    // multiple targets

    public Map<String, String> getContextVector(String source, List<String> targets, String text) throws IOException {
        return this.getContextVector(source, targets, text, null, null);
    }

    public Map<String, String> getContextVector(String source, List<String> targets, String text,
                                                long[] hints) throws IOException {
        return this.getContextVector(source, targets, text, hints, null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getContextVector(String source, List<String> targets, String text, long[] hints,
                                                Integer limit) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        data.put("targets", targets);
        data.put("text", text);

        if (hints != null)
            data.put("hints", hints);
        if (limit != null)
            data.put("limit", limit);

        Map<?, ?> result = this.httpClient.send(Map.class, "get", "/context-vector", data);
        
        return (Map<String, String>) result.get("vectors");
    }

    // single target with path

    public String getContextVectorFromFile(String source, String targets, String file) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), null, null, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, String file, long[] hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), hints, null, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, String file,
                                           long[] hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), hints, limit, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, String file, long[] hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), hints, limit, compression).get(targets);
    }

    // single target

    public String getContextVectorFromFile(String source, String targets, File file) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, null, null, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, File file, long[] hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, null, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, File file,
                                           long[] hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, limit, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, File file, long[] hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, limit, compression).get(targets);
    }

    // multiple targets with path

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets,
                                                        String file) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), null, null, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file,
                                                        long[] hints) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, null, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file, long[] hints,
                                                        Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file, long[] hints,
                                                        Integer limit, String compression) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, compression);
    }

    // multiple targets

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets,
                                                        File file) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, null, null, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        long[] hints) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, null, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file, long[] hints,
                                                        Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, limit, null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file, long[] hints,
                                                        Integer limit, String compression) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        data.put("targets", targets);

        if (hints != null)
            data.put("hints", hints);
        if (limit != null)
            data.put("limit", limit);
        if (compression != null)
            data.put("compression", compression);

        Map<String, File> files = new HashMap<>();
        files.put("content", file);

        Map<?, ?> result = this.httpClient.send(Map.class, "get", "/context-vector", data, files);
        return (Map<String, String>) result.get("vectors");
    }

}
