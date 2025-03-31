package com.example.circleindicator3andviewpager2;

import java.io.Serializable;

public class Images implements Serializable {
    private int imagesId;

    // Constructor
    public Images(int imagesId) {
        this.imagesId = imagesId;
    }

    // Getter
    public int getImagesId() {
        return imagesId;
    }

    // Setter
    public void setImagesId(int imagesId) {
        this.imagesId = imagesId;
    }
}

