package de.muenchen.anzeigenportal.swbrett.images.model;

import static jakarta.persistence.FetchType.LAZY;

import java.sql.Blob;
import java.sql.SQLException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.jdbc.NonContextualLobCreator;

/**
 * POJO für Bilder, die für eine Anzeige hochgeladen werden können.
 */
@Setter
@Getter
@Entity
@Table(name = "t_swb_image")
@SuppressFBWarnings("EI_EXPOSE_REP")
public class SwbImage {

    // ======================== FIELD/COLUMN DECLARATIONS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Access(AccessType.PROPERTY) // <= F*** Hibernate 3.5+ requires this on property getters!
    @Column(name = "image", nullable = false)
    @Basic(fetch = LAZY) // <= Is ignored unless "hibernate-enhance-maven-plugin" is configured!
    private Blob imageBlob; // TODO: fix the warning ... should be some byte array

    // ======================== FIELD GETTERS AND SETTERS

    public byte[] getImage() {
        final Blob ib = this.getImageBlob();
        if (ib == null) {
            return new byte[0];
        }
        try {
            return ib.getBytes(1, (int) ib.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getImageLength() {
        final Blob ib = this.getImageBlob();
        if (ib == null) {
            return -1;
        }
        try {
            return ib.length();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasImage() {
        return this.getImageBlob() != null;
    }

    public void setImage(final byte[] image) {
        this.setImageBlob(NonContextualLobCreator.INSTANCE.createBlob(image));
    }
}
