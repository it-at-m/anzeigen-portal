package de.muenchen.anzeigenportal.swbrett.files.model;

import static jakarta.persistence.FetchType.LAZY;

import java.sql.Blob;
import java.sql.SQLException;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.jdbc.NonContextualLobCreator;

/**
 * POJO für Dateien (z.B. Bilder, PDFs), die in swbrett angelegt werden können.
 */
@Setter
@Getter
@Entity
@Table(name = "t_swb_file")
public class SwbFile {

    // ======================== FIELD/COLUMN DECLARATIONS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    private int size;

    @Access(AccessType.PROPERTY) // <= F*** Hibernate 3.5+ requires this on property getters!
    @Column(name = "file", nullable = false)
    @Basic(fetch = LAZY) // <= Is ignored unless "hibernate-enhance-maven-plugin" is configured!
    private Blob fileBlob; // TODO: fix the warning ... should be some byte array


    // ======================== FIELD GETTERS AND SETTERS

    public byte[] getFile() {
        Blob fb = this.getFileBlob();
        if (fb == null) {
            return new byte[0];
        }
        try {
            return fb.getBytes(1, (int) fb.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getFileLength() {
        Blob fb = this.getFileBlob();
        if (fb == null) {
            return -1;
        }
        try {
            return fb.length();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasFile() {
        return this.getFileBlob() != null;
    }

    public void setFile(byte[] file) {
        this.setFileBlob(NonContextualLobCreator.INSTANCE.createBlob(file));
    }
}
