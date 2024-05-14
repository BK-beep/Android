package enset.android.projetsynthese.ui.googleBooks.models;

import java.io.Serializable;

public class ImageLinks  implements Serializable {
    private String smallThumbnail;
    private String thumbnail;

    @Override
    public String toString() {
        return "ImageLinks{" +
                "smallThumbnail='" + smallThumbnail + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
