package com.modernmt;

import com.modernmt.model.DetectedLanguage;
import com.modernmt.model.TranslateOptions;
import com.modernmt.model.Translation;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
        this(apiKey, platform, "1.0.4");
    }

    public ModernMT(String apiKey) {
        this(apiKey, "modernmt-java");
    }

    public List<String> listSupportedLanguages() throws IOException {
        return Arrays.asList(this.httpClient.send(String[].class, "get", "/translate/languages", null));
    }

    public DetectedLanguage detectLanguage(String q) throws IOException {
        return this.detectLanguage(q, null);
    }

    public DetectedLanguage detectLanguage(String q, String format) throws IOException {
        Map<String, Object> data = new HashMap<>();

        data.put("q", q);
        if (format != null)
            data.put("format", format);

        return this.httpClient.send(DetectedLanguage.class, "get", "/translate/detect", data);
    }

    // single sentence

    public Translation translate(String source, String target, String q) throws IOException {
        return this.translate(source, target, Collections.singletonList(q)).get(0);
    }

    public Translation translate(String source, String target, String q, long[] hints) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints).get(0);
    }

    public Translation translate(String source, String target, String q, long[] hints,
                                 String contextVector) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, contextVector).get(0);
    }

    public Translation translate(String source, String target, String q, long[] hints, String contextVector,
                                 TranslateOptions options) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, contextVector, options).get(0);
    }

    public Translation translate(String source, String target, String q, List<String> hints) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints).get(0);
    }

    public Translation translate(String source, String target, String q, List<String> hints,
                                 String contextVector) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, contextVector).get(0);
    }

    public Translation translate(String source, String target, String q, List<String> hints, String contextVector,
                                 TranslateOptions options) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), hints, contextVector, options).get(0);
    }

    // multiple sentences

    public List<Translation> translate(String source, String target, List<String> q) throws IOException {
        return this.translate(source, target, q, (List<String>) null);
    }

    public List<Translation> translate(String source, String target, List<String> q, long[] hints) throws IOException {
        return this.translate(source, target, q, hints, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, long[] hints,
                                       String contextVector) throws IOException {
        return this.translate(source, target, q, hints, contextVector, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, long[] hints,
                                       String contextVector, TranslateOptions options) throws IOException {
        List<String> _hints = null;
        if (hints != null)
            _hints = LongStream.of(hints).mapToObj(Long::toString).collect(Collectors.toList());

        return this.translate(source, target, q, _hints, contextVector, options);
    }

    public List<Translation> translate(String source, String target, List<String> q, List<String> hints) throws IOException {
        return this.translate(source, target, q, hints, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, List<String> hints,
                                       String contextVector) throws IOException {
        return this.translate(source, target, q, hints, contextVector, null);
    }

    public List<Translation> translate(String source, String target, List<String> q, List<String> hints,
                                       String contextVector, TranslateOptions options) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("target", target);
        data.put("q", q);

        if (source != null)
            data.put("source", source);
        if (hints != null)
            data.put("hints", String.join(",", hints));
        if (contextVector != null)
            data.put("context_vector", contextVector);

        if (options != null) {
            String priority = options.getPriority();
            String projectId = options.getProjectId();
            Boolean multiline = options.getMultiline();
            Integer timeout = options.getTimeout();
            String format = options.getFormat();

            if (priority != null)
                data.put("priority", priority);
            if (projectId != null)
                data.put("project_id", projectId);
            if (multiline != null)
                data.put("multiline", multiline);
            if (timeout != null)
                data.put("timeout", timeout);
            if (format != null)
                data.put("format", format);
        }

        return Arrays.asList(this.httpClient.send(Translation[].class, "get", "/translate", data));
    }

    // single target

    public String getContextVector(String source, String targets, String text) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text).get(targets);
    }

    public String getContextVector(String source, String targets, String text, long[] hints) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, hints).get(targets);
    }

    public String getContextVector(String source, String targets, String text, long[] hints,
                                   Integer limit) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, hints, limit).get(targets);
    }

    public String getContextVector(String source, String targets, String text, List<String> hints) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, hints).get(targets);
    }

    public String getContextVector(String source, String targets, String text, List<String> hints,
                                   Integer limit) throws IOException {
        return this.getContextVector(source, Collections.singletonList(targets), text, hints, limit).get(targets);
    }

    // multiple targets

    public Map<String, String> getContextVector(String source, List<String> targets, String text) throws IOException {
        return this.getContextVector(source, targets, text, (List<String>) null);
    }

    public Map<String, String> getContextVector(String source, List<String> targets, String text,
                                                long[] hints) throws IOException {
        return this.getContextVector(source, targets, text, hints, null);
    }

    public Map<String, String> getContextVector(String source, List<String> targets, String text, long[] hints,
                                                Integer limit) throws IOException {
        List<String> _hints = null;
        if (hints != null)
            _hints = LongStream.of(hints).mapToObj(Long::toString).collect(Collectors.toList());

        return this.getContextVector(source, targets, text, _hints, limit);
    }

    public Map<String, String> getContextVector(String source, List<String> targets, String text,
                                                List<String> hints) throws IOException {
        return this.getContextVector(source, targets, text, hints, null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getContextVector(String source, List<String> targets, String text, List<String> hints,
                                                Integer limit) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        data.put("targets", targets);
        data.put("text", text);

        if (hints != null)
            data.put("hints", String.join(",", hints));
        if (limit != null)
            data.put("limit", limit);

        Map<?, ?> result = this.httpClient.send(Map.class, "get", "/context-vector", data);
        
        return (Map<String, String>) result.get("vectors");
    }

    // single target with path

    public String getContextVectorFromFile(String source, String targets, String file) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), (List<String>) null).get(targets);
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

    public String getContextVectorFromFile(String source, String targets, String file, List<String> hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), hints, null, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, String file,
                                           List<String> hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), hints, limit, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, String file, List<String> hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), new File(file), hints, limit, compression).get(targets);
    }


    // single target

    public String getContextVectorFromFile(String source, String targets, File file) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, (List<String>) null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, File file, long[] hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, null).get(targets);
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

    public String getContextVectorFromFile(String source, String targets, File file, List<String> hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, File file,
                                           List<String> hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, limit, null).get(targets);
    }

    public String getContextVectorFromFile(String source, String targets, File file, List<String> hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(targets), file, hints, limit, compression).get(targets);
    }

    // multiple targets with path

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets,
                                                        String file) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), (List<String>) null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file,
                                                        long[] hints) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file, long[] hints,
                                                        Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file, long[] hints,
                                                        Integer limit, String compression) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, compression);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file,
                                                        List<String> hints) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file, List<String> hints,
                                                        Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file, List<String> hints,
                                                        Integer limit, String compression) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, compression);
    }

    // multiple targets

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets,
                                                        File file) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, (List<String>) null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        long[] hints) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        long[] hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, limit, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file, long[] hints,
                                                        Integer limit, String compression) throws IOException {
        List<String> _hints = null;
        if (hints != null)
            _hints = LongStream.of(hints).mapToObj(Long::toString).collect(Collectors.toList());

        return this.getContextVectorFromFile(source, targets, file, _hints, limit, compression);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        List<String> hints) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file, List<String> hints,
                                                        Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, limit, null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file, List<String> hints,
                                                        Integer limit, String compression) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        data.put("targets", targets);

        if (hints != null)
            data.put("hints", String.join(",", hints));
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
