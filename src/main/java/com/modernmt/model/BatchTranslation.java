package com.modernmt.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BatchTranslation extends Model {

    private final Object data;

    private final Object metadata;

    public BatchTranslation(Object data, Object metadata) {
        this.data = data;
        this.metadata = metadata;
    }

    public Translation getData() {
        if (data instanceof Translation)
            return (Translation) data;

        return null;
    }

    public List<Translation> getDataList() {
        if (data instanceof Translation[])
            return Arrays.asList((Translation[]) data);

        return Collections.singletonList((Translation) data);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetadata() {
        return (T) metadata;
    }

}
