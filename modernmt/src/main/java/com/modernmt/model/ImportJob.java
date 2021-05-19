package com.modernmt.model;

public class ImportJob extends Model {

    private String id;
    private long memory;
    private int size;
    private float progress;

    public String getId() {
        return id;
    }

    public long getMemory() {
        return memory;
    }

    public int getSize() {
        return size;
    }

    public float getProgress() {
        return progress;
    }

}
