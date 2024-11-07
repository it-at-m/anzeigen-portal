package de.muenchen.anzeigenportal.swbrett.images.model;

import static jakarta.persistence.FetchType.LAZY;

import java.sql.Blob;
import java.sql.SQLException;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.jdbc.NonContextualLobCreator;

/**
 * POJO für Bilder, die für eine Anzeige hochgeladen werden können.
 */
@Entity
@Table(name = "t_swb_image")
public class SwbImage {

    // ======================== FIELD/COLUMN DECLARATIONS

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Access(AccessType.PROPERTY) // <= F*** Hibernate 3.5+ requires this on property getters!
    @Column(name = "image", nullable = false)
    @Basic(fetch = LAZY) // <= Is ignored unless "hibernate-enhance-maven-plugin" is configured!
    public Blob getImageBlob() {
        return this.imageBlob;
    } // TODO: fix the warning ... should be some byte array

    public void setImageBlob(Blob imageBlob) {
        this.imageBlob = imageBlob;
    }

    private Blob imageBlob;

    // ======================== FIELD GETTERS AND SETTERS

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        Blob ib = this.getImageBlob();
        if (ib == null) return new byte[0];
        try {
            return ib.getBytes(1, (int) ib.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getImageLength() {
        Blob ib = this.getImageBlob();
        if (ib == null) return -1;
        try {
            return ib.length();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasImage() {
        return this.getImageBlob() != null;
    }

    public void setImage(byte[] image) {
        this.setImageBlob(NonContextualLobCreator.INSTANCE.createBlob(image));
    }
}
