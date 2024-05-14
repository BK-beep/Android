package enset.android.projetsynthese.ui.googleBooks.models;

import java.io.Serializable;

public class Book implements Serializable {
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "volumeInfo=" + volumeInfo +
                '}';
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
