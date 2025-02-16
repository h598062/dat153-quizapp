package no.dat153.quizzler.entity;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Represents an item in the gallery.
 * Each item has an id, a name and an image uri.
 */
@Entity
public class QuestionItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imageText;
    private String imageUriString;

    /**
     * Creates a new GalleryItem.
     *
     * @param imageText      The name of the item.
     * @param imageUriString The uri of the image.
     */
    public QuestionItem(String imageText, String imageUriString) {
        this.imageText = imageText;
        this.imageUriString = imageUriString;
    }

    public QuestionItem(String imageText, Uri imageUriString) {
        this.imageText = imageText;
        this.imageUriString = imageUriString.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public String getImageUriString() {
        return imageUriString;
    }

    public void setImageUriString(String imageUriString) {
        this.imageUriString = imageUriString;
    }

    public Uri getImageUri() {
        return Uri.parse(imageUriString);
    }

    @NonNull
    @Override
    public String toString() {
        return "GalleryItem{" +
                " imageUriString=" + imageUriString +
                ", imageText='" + imageText + '\'' +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionItem)) return false;
        QuestionItem that = (QuestionItem) o;
        return Objects.equals(imageText, that.imageText) && Objects.equals(imageUriString, that.imageUriString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageText, imageUriString);
    }
}
