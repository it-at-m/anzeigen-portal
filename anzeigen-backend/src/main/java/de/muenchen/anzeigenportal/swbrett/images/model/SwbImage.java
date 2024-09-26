package de.muenchen.anzeigenportal.swbrett.images.model;

import static javax.persistence.FetchType.LAZY;

import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.engine.jdbc.NonContextualLobCreator;

/**
 * POJO für Bilder, die für eine Anzeige hochgeladen werden können.
 */
@Entity
@Table(name = "t_swb_image")
public class SwbImage {

	// ======================== FIELD/COLUMN DECLARATIONS

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Access(AccessType.PROPERTY) // <= F*** Hibernate 3.5+ requires this on property getters!
    @Column(name = "image", nullable = false)
    @Basic(fetch = LAZY) // <= Is ignored unless "hibernate-enhance-maven-plugin" is configured!
    public Blob getImageBlob() { return this.imageBlob; }
    public void setImageBlob(Blob imageBlob) { this.imageBlob = imageBlob; }
    private Blob imageBlob;

    // ======================== FIELD GETTERS AND SETTERS

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public byte[] getImage() {
    	Blob ib = this.getImageBlob();
    	if (ib == null) return null;
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
