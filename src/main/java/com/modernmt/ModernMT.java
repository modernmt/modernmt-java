package com.modernmt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.*;
import com.modernmt.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ModernMT {

    private static final String PLATFORM = "modernmt-java";
    private static final String PLATFORM_VERSION = "1.4.0";

    private final HttpClient httpClient;
    public final MemoryServices memories;

    private JWTVerifier jwtVerifier;
    private long batchPublicKeyTimestampMs;

    private final Gson gson;

    public ModernMT(String apiKey, String platform, String platformVersion, long apiClient) {
        Map<String, String> headers = new HashMap<>();

        headers.put("MMT-ApiKey", apiKey);
        headers.put("MMT-Platform", platform);
        headers.put("MMT-PlatformVersion", platformVersion);

        if (apiClient != 0)
            headers.put("MMT-ApiClient", String.valueOf(apiClient));

        this.httpClient = new HttpClient("https://api.modernmt.com", headers);

        this.memories = new MemoryServices(this.httpClient);

        this.gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public ModernMT(String apiKey, String platform, String platformVersion) {
        this(apiKey, platform, platformVersion, 0);
    }

    // useless but kept for backward compatibility
    public ModernMT(String apiKey, String platform) {
        this(apiKey, platform, PLATFORM_VERSION, 0);
    }

    public ModernMT(String apiKey) {
        this(apiKey, PLATFORM);
    }

    public ModernMT(String apiKey, long apiClient) {
        this(apiKey, PLATFORM, PLATFORM_VERSION, apiClient);
    }

    public List<String> listSupportedLanguages() throws IOException {
        return Arrays.asList(this.httpClient.send(String[].class, "get", "/translate/languages", null));
    }

    public DetectedLanguage detectLanguage(String q) throws IOException {
        return this.detectLanguage(Collections.singletonList(q)).get(0);
    }

    public DetectedLanguage detectLanguage(String q, String format) throws IOException {
        return this.detectLanguage(Collections.singletonList(q), format).get(0);
    }

    public List<DetectedLanguage> detectLanguage(List<String> q) throws IOException {
        return this.detectLanguage(q, null);
    }

    public List<DetectedLanguage> detectLanguage(List<String> q, String format) throws IOException {
        Map<String, Object> data = new HashMap<>();

        data.put("q", q);
        if (format != null)
            data.put("format", format);

        return Arrays.asList(this.httpClient.send(DetectedLanguage[].class, "get", "/translate/detect", data));
    }

    // translate single sentence

    public Translation translate(String source, String target, String q) throws IOException {
        return this.translate(source, target, Collections.singletonList(q)).get(0);
    }

    public Translation translate(String source, String target, String q, TranslateOptions options) throws IOException {
        return this.translate(source, target, Collections.singletonList(q), options).get(0);
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

    // translate multiple sentences

    public List<Translation> translate(String source, String target, List<String> q) throws IOException {
        return this.translate(source, target, q, (List<String>) null);
    }

    public List<Translation> translate(String source, String target, List<String> q,
                                       TranslateOptions options) throws IOException {
        return this.translate(source, target, q, (List<String>) null, null, options);
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

    public List<Translation> translate(String source, String target, List<String> q,
                                       List<String> hints) throws IOException {
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
            Integer altTranslations = options.getAltTranslations();
            String session = options.getSession();

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
            if (altTranslations != null)
                data.put("alt_translations", altTranslations);
            if (session != null)
                data.put("session", session);
        }

        return Arrays.asList(this.httpClient.send(Translation[].class, "get", "/translate", data));
    }

    // batchTranslate single sentence

    public boolean batchTranslate(String webhook, String source, String target, String q) throws IOException {
        return this.batchTranslate(webhook, source, target, q, (List<String>) null);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q,
                                  TranslateOptions options) throws IOException {
        return this.batchTranslate(webhook, source, target, q, (List<String>) null, null, options);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q,
                                  long[] hints) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q,
                                  long[] hints, String contextVector) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, contextVector, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q,
                                  long[] hints, String contextVector, TranslateOptions options) throws IOException {
        List<String> _hints = null;
        if (hints != null)
            _hints = LongStream.of(hints).mapToObj(Long::toString).collect(Collectors.toList());

        return this.batchTranslate(webhook, source, target, q, _hints, contextVector, options);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q,
                                  List<String> hints) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q, List<String> hints,
                                  String contextVector) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, contextVector, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, String q,
                                  List<String> hints, String contextVector,
                                  TranslateOptions options) throws IOException {
        return this.batchTranslate(webhook, source, target, (Object) q, hints, contextVector, options);
    }

    // batchTranslate multiple sentences

    public boolean batchTranslate(String webhook, String source, String target, List<String> q) throws IOException {
        return this.batchTranslate(webhook, source, target, q, (List<String>) null);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  TranslateOptions options) throws IOException {
        return this.batchTranslate(webhook, source, target, q, (List<String>) null, null, options);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  long[] hints) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  long[] hints, String contextVector) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, contextVector, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  long[] hints, String contextVector, TranslateOptions options) throws IOException {
        List<String> _hints = null;
        if (hints != null)
            _hints = LongStream.of(hints).mapToObj(Long::toString).collect(Collectors.toList());

        return this.batchTranslate(webhook, source, target, q, _hints, contextVector, options);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  List<String> hints) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  List<String> hints, String contextVector) throws IOException {
        return this.batchTranslate(webhook, source, target, q, hints, contextVector, null);
    }

    public boolean batchTranslate(String webhook, String source, String target, List<String> q,
                                  List<String> hints, String contextVector,
                                  TranslateOptions options) throws IOException {
        return this.batchTranslate(webhook, source, target, (Object) q, hints, contextVector, options);
    }

    // batchTranslate implementation

    private boolean batchTranslate(String webhook, String source, String target, Object q,
                                   List<String> hints, String contextVector,
                                   TranslateOptions options) throws IOException {
        Map<String, Object> data = new HashMap<>();
        Map<String, String> headers = new HashMap<>();

        data.put("target", target);
        data.put("q", q);
        data.put("webhook", webhook);

        if (source != null)
            data.put("source", source);
        if (hints != null)
            data.put("hints", String.join(",", hints));
        if (contextVector != null)
            data.put("context_vector", contextVector);

        if (options != null) {
            String projectId = options.getProjectId();
            Boolean multiline = options.getMultiline();
            String format = options.getFormat();
            Integer altTranslations = options.getAltTranslations();
            Object metadata = options.getMetadata();
            String session = options.getSession();

            if (projectId != null)
                data.put("project_id", projectId);
            if (multiline != null)
                data.put("multiline", multiline);
            if (format != null)
                data.put("format", format);
            if (altTranslations != null)
                data.put("alt_translations", altTranslations);
            if (metadata != null)
                data.put("metadata", metadata);
            if (session != null)
                data.put("session", session);

            String idempotencyKey = options.getIdempotencyKey();

            if (idempotencyKey != null)
                headers.put("x-idempotency-key", idempotencyKey);
        }

        Map<?, ?> result = this.httpClient.send(Map.class, "post", "/translate/batch", data, null, headers);

        return (boolean) result.get("enqueued");
    }

    // handleCallback

    public BatchTranslation handleCallback(Object body, String signature) throws IOException {
        return this.handleCallback(body, signature, null);
    }

    public BatchTranslation handleCallback(Object body, String signature, Class<?> metadataClass) throws IOException {
        if (this.jwtVerifier == null)
            this.refreshJWTVerifier();

        if (System.currentTimeMillis() - this.batchPublicKeyTimestampMs > 1000 * 3600) {   // key is older than 1 hour
            try {
                this.refreshJWTVerifier();
            } catch (Exception e) {
                // ignore
            }
        }

        try {
            this.jwtVerifier.verify(signature);
        }
        catch (JWTVerificationException e) {
            throw new SignatureException(e);
        }

        JsonObject json;
        if (body instanceof String)
            json = JsonParser.parseString((String) body).getAsJsonObject();
        else if (body instanceof JsonObject)
            json = (JsonObject) body;
        else
            json = JsonParser.parseString(this.gson.toJson(body)).getAsJsonObject();

        JsonObject resultJson = json.getAsJsonObject("result");
        JsonObject metadataJson = json.getAsJsonObject("metadata");

        Object metadata = null;
        if (metadataJson != null && metadataClass != null)
            metadata = this.gson.fromJson(metadataJson, metadataClass);

        int status = resultJson.get("status").getAsInt();
        if (status >= 300 || status < 200) {
            JsonObject errorJson = resultJson.getAsJsonObject("error");

            throw new ModernMTException(
                    status,
                    errorJson.get("type").getAsString(),
                    errorJson.get("message").getAsString(),
                    metadata);
        }

        JsonElement dataJson = resultJson.get("data");
        if (dataJson.isJsonArray())
            return new BatchTranslation(this.gson.fromJson(dataJson, Translation[].class), metadata);
        else
            return new BatchTranslation(this.gson.fromJson(dataJson, Translation.class), metadata);
    }

    private void refreshJWTVerifier() throws IOException {
        Map<?, ?> result = this.httpClient.send(Map.class, "get", "/translate/batch/key", null);
        String keyString = new String(Base64.getDecoder().decode((String) result.get("publicKey")), StandardCharsets.UTF_8);

        keyString = keyString.replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(keyString));

        KeyFactory kf;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        RSAPublicKey key;
        try {
            key = (RSAPublicKey) kf.generatePublic(keySpecX509);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        Algorithm algorithm = Algorithm.RSA256(key, null);

        this.jwtVerifier = JWT.require(algorithm).build();
        this.batchPublicKeyTimestampMs = System.currentTimeMillis();
    }


    // getContextVector single target

    public String getContextVector(String source, String target, String text) throws IOException {
        return this.getContextVector(source, Collections.singletonList(target), text).get(target);
    }

    public String getContextVector(String source, String target, String text, long[] hints) throws IOException {
        return this.getContextVector(source, Collections.singletonList(target), text, hints).get(target);
    }

    public String getContextVector(String source, String target, String text, long[] hints,
                                   Integer limit) throws IOException {
        return this.getContextVector(source, Collections.singletonList(target), text, hints, limit).get(target);
    }

    public String getContextVector(String source, String target, String text, List<String> hints) throws IOException {
        return this.getContextVector(source, Collections.singletonList(target), text, hints).get(target);
    }

    public String getContextVector(String source, String target, String text, List<String> hints,
                                   Integer limit) throws IOException {
        return this.getContextVector(source, Collections.singletonList(target), text, hints, limit).get(target);
    }

    // getContextVector multiple targets

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

    // getContextVectorFromFile single target with path

    public String getContextVectorFromFile(String source, String target, String file) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), (List<String>) null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file,
                                           long[] hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), hints, null, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file,
                                           long[] hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), hints, limit, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file, long[] hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), hints, limit, compression).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file,
                                           List<String> hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), hints, null, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file,
                                           List<String> hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), hints, limit, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file, List<String> hints,
                                           Integer limit, String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), hints, limit, compression).get(target);
    }

    public String getContextVectorFromFile(String source, String target, String file,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), new File(file), (List<String>) null, null, compression)
                .get(target);
    }

    // getContextVectorFromFile single target

    public String getContextVectorFromFile(String source, String target, File file) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, (List<String>) null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file, long[] hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, hints, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file,
                                           long[] hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, hints, limit, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file, long[] hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, hints, limit, compression).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file,
                                           List<String> hints) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, hints, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file,
                                           List<String> hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, hints, limit, null).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file, List<String> hints, Integer limit,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, hints, limit, compression).get(target);
    }

    public String getContextVectorFromFile(String source, String target, File file,
                                           String compression) throws IOException {
        return this.getContextVectorFromFile(source,
                Collections.singletonList(target), file, (List<String>) null, null, compression).get(target);
    }

    // getContextVectorFromFile multiple targets with path

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

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file,
                                                        List<String> hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file,
                                                        List<String> hints, Integer limit,
                                                        String compression) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), hints, limit, compression);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, String file,
                                                        String compression) throws IOException {
        return this.getContextVectorFromFile(source, targets, new File(file), (List<String>) null, null, compression);
    }

    // getContextVectorFromFile multiple targets

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

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        List<String> hints, Integer limit) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, hints, limit, null);
    }

    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        String compression) throws IOException {
        return this.getContextVectorFromFile(source, targets, file, (List<String>) null, null, compression);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getContextVectorFromFile(String source, List<String> targets, File file,
                                                        List<String> hints, Integer limit,
                                                        String compression) throws IOException {
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

    public User me() throws IOException {
        return this.httpClient.send(User.class, "get", "/users/me", null);
    }

    public QualityEstimation qe(String source, String target, String sentence, String translation) throws IOException {
        return this.qe(source, target,
                Collections.singletonList(sentence), Collections.singletonList(translation)).get(0);
    }

    public List<QualityEstimation> qe(String source, String target,
                      List<String> sentences, List<String> translations) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        data.put("target", target);
        data.put("sentence", sentences);
        data.put("translation", translations);

        return Arrays.asList(this.httpClient.send(QualityEstimation[].class, "get", "/qe", data));
    }

}
