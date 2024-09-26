package de.muenchen.anzeigenportal.swbrett.files.model;

/**
 * Transferobject für Dateien.
 */
public class SwbFileTO {

    private long id;

    private String name;

    private int size;

    private String fileBase64;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }
}
