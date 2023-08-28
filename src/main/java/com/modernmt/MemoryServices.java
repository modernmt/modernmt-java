package com.modernmt;

import com.modernmt.model.GlossaryTerm;
import com.modernmt.model.ImportJob;
import com.modernmt.model.Memory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryServices {

    private final HttpClient httpClient;

    MemoryServices(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<Memory> list() throws IOException {
        return Arrays.asList(this.httpClient.send(Memory[].class, "get", "/memories", null));
    }

    public Memory get(long id) throws IOException {
        return this.get(Long.toString(id));
    }

    public Memory get(String id) throws IOException {
        return this.httpClient.send(Memory.class, "get", "/memories/" + id, null);
    }

    public Memory create(String name) throws IOException {
        return this.create(name, null, null);
    }

    public Memory create(String name, String description) throws IOException {
        return this.create(name, description, null);
    }

    public Memory create(String name, String description, String externalId) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);

        if (description != null)
            data.put("description", description);
        if (externalId != null)
            data.put("external_id", externalId);

        return this.httpClient.send(Memory.class, "post", "/memories/", data);
    }

    public Memory edit(long id, String name) throws IOException {
        return this.edit(id, name, null);
    }

    public Memory edit(long id, String name, String description) throws IOException {
        return this.edit(Long.toString(id), name, description);
    }

    public Memory edit(String id, String name) throws IOException {
        return this.edit(id, name, null);
    }

    public Memory edit(String id, String name, String description) throws IOException {
        Map<String, Object> data = new HashMap<>();

        if (name != null)
            data.put("name", name);
        if (description != null)
            data.put("description", description);

        return this.httpClient.send(Memory.class, "put", "/memories/" + id, data);
    }

    public Memory delete(long id) throws IOException {
        return this.delete(Long.toString(id));
    }

    public Memory delete(String id) throws IOException {
        return this.httpClient.send(Memory.class, "delete", "/memories/" + id, null);
    }

    public ImportJob add(long id, String source, String target, String sentence,
                         String translation) throws IOException {
        return this.add(Long.toString(id), source, target, sentence, translation, null, null);
    }

    public ImportJob add(long id, String source, String target, String sentence, String translation,
                         String tuid) throws IOException {
        return this.add(Long.toString(id), source, target, sentence, translation, tuid, null);
    }

    public ImportJob add(long id, String source, String target, String sentence, String translation,
                         String tuid, String session) throws IOException {
        return this.add(Long.toString(id), source, target, sentence, translation, tuid, session);
    }

    public ImportJob add(String id, String source, String target, String sentence,
                         String translation) throws IOException {
        return this.add(id, source, target, sentence, translation, null, null);
    }

    public ImportJob add(String id, String source, String target, String sentence,
                         String translation, String tuid) throws IOException {
        return this.add(id, source, target, sentence, translation, tuid, null);
    }

    public ImportJob add(String id, String source, String target, String sentence, String translation,
                         String tuid, String session) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        data.put("target", target);
        data.put("sentence", sentence);
        data.put("translation", translation);

        if (tuid != null)
            data.put("tuid", tuid);

        if (session != null)
            data.put("session", session);

        return this.httpClient.send(ImportJob.class, "post", "/memories/" + id + "/content", data);
    }

    public ImportJob replace(long id, String tuid, String source, String target, String sentence,
                             String translation) throws IOException {
        return this.replace(Long.toString(id), tuid, source, target, sentence, translation, null);
    }

    public ImportJob replace(long id, String tuid, String source, String target, String sentence,
                             String translation, String session) throws IOException {
        return this.replace(Long.toString(id), tuid, source, target, sentence, translation, session);
    }

    public ImportJob replace(String id, String tuid, String source, String target, String sentence,
                             String translation) throws IOException {
        return this.replace(id, tuid, source, target, sentence, translation, null);
    }

    public ImportJob replace(String id, String tuid, String source, String target, String sentence,
                             String translation, String session) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("tuid", tuid);
        data.put("source", source);
        data.put("target", target);
        data.put("sentence", sentence);
        data.put("translation", translation);

        if (session != null)
            data.put("session", session);

        return this.httpClient.send(ImportJob.class, "put", "/memories/" + id + "/content", data);
    }

    public ImportJob importTmx(long id, String tmx) throws IOException {
        return this.importTmx(id, tmx, null);
    }

    public ImportJob importTmx(long id, File tmx) throws IOException {
        return this.importTmx(id, tmx, null);
    }

    public ImportJob importTmx(long id, String tmx, String compression) throws IOException {
        return this.importTmx(id, new File(tmx), compression);
    }

    public ImportJob importTmx(long id, File tmx, String compression) throws IOException {
        return this.importTmx(Long.toString(id), tmx, compression);
    }

    public ImportJob importTmx(String id, String tmx) throws IOException {
        return this.importTmx(id, new File(tmx), null);
    }

    public ImportJob importTmx(String id, File tmx) throws IOException {
        return this.importTmx(id, tmx, null);
    }

    public ImportJob importTmx(String id, String tmx, String compression) throws IOException {
        return this.importTmx(id, new File(tmx), compression);
    }

    public ImportJob importTmx(String id, File tmx, String compression) throws IOException {
        Map<String, Object> data = new HashMap<>();
        if (compression != null)
            data.put("compression", compression);

        Map<String, File> files = new HashMap<>();
        files.put("tmx", tmx);

        return this.httpClient.send(ImportJob.class, "post", "/memories/" + id + "/content", data, files);
    }

    public ImportJob addToGlossary(long id, List<GlossaryTerm> terms, String type, String tuid) throws IOException {
        return this.addToGlossary(Long.toString(id), terms, type, tuid);
    }
    public ImportJob addToGlossary(String id, List<GlossaryTerm> terms, String type, String tuid) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("terms", terms);
        data.put("type", type);
        data.put("tuid", tuid);

        return this.httpClient.send(ImportJob.class, "post", "/memories/" + id + "/glossary", data);
    }

    public ImportJob replaceInGlossary(long id, List<GlossaryTerm> terms,
                                       String type, String tuid) throws IOException {
        return this.replaceInGlossary(Long.toString(id), terms, type, tuid);
    }
    public ImportJob replaceInGlossary(String id, List<GlossaryTerm> terms,
                                       String type, String tuid) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("terms", terms);
        data.put("type", type);
        data.put("tuid", tuid);

        return this.httpClient.send(ImportJob.class, "put", "/memories/" + id + "/glossary", data);
    }

    public ImportJob importGlossary(long id, String csv, String type) throws IOException {
        return this.importGlossary(Long.toString(id), new File(csv), type, null);
    }

    public ImportJob importGlossary(long id, File csv, String type) throws IOException {
        return this.importGlossary(Long.toString(id), csv, type, null);
    }

    public ImportJob importGlossary(long id, String csv, String type, String compression) throws IOException {
        return this.importGlossary(Long.toString(id), new File(csv), type, compression);
    }

    public ImportJob importGlossary(long id, File csv, String type, String compression) throws IOException {
        return this.importGlossary(Long.toString(id), csv, type, compression);
    }

    public ImportJob importGlossary(String id, String csv, String type) throws IOException {
        return this.importGlossary(id, new File(csv), type, null);
    }

    public ImportJob importGlossary(String id, File csv, String type) throws IOException {
        return this.importGlossary(id, csv, type, null);
    }

    public ImportJob importGlossary(String id, String csv, String type,
                                    String compression) throws IOException {
        return this.importGlossary(id, new File(csv), type, compression);
    }

    public ImportJob importGlossary(String id, File csv, String type,
                                    String compression) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);

        if (compression != null)
            data.put("compression", compression);

        Map<String, File> files = new HashMap<>();
        files.put("csv", csv);

        return this.httpClient.send(ImportJob.class, "post", "/memories/" + id + "/glossary", data, files);
    }

    public ImportJob getImportStatus(String uuid) throws IOException {
        return this.httpClient.send(ImportJob.class, "get", "/import-jobs/" + uuid, null);
    }

}
