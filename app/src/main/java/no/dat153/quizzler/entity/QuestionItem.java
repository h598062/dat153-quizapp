package no.dat153.quizzler.entity;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Objects;

public class QuestionItem {
    private String text;
    private Uri imageUri;

    public QuestionItem(String text, Uri imageUri) {
        this.text = text;
        this.imageUri = imageUri;
    }


    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUriString() {
        return imageUri.toString();
    }

    public void setImgUriFromString(String imgUriString) {
        this.imageUri = Uri.parse(imgUriString);
    }

    @NonNull
    @Override
    public String toString() {
        return "QuestionItem{" +
                " imageUri=" + imageUri +
                ", text='" + text + '\'' +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionItem)) return false;
        QuestionItem that = (QuestionItem) o;
        return Objects.equals(text, that.text) && Objects.equals(imageUri, that.imageUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, imageUri);
    }
}
