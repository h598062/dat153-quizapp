package no.dat153.quizzler.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an item in the gallery.
 * Each item has an id, a name and an image uri.
 */
@Entity
public class GalleryItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String imgUri;

    /**
     * Creates a new GalleryItem.
     * @param name The name of the item.
     * @param imgUri The uri of the image.
     */
    public GalleryItem(String name, String imgUri) {
        this.name = name;
        this.imgUri = imgUri;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUri() {
        return imgUri;
    }
}
