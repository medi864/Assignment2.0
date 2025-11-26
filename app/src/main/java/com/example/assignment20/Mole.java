package com.example.assignment20;

import android.widget.ImageView;

public class Mole {
    private int index;
    private ImageView imageView;
    private boolean isVisible;

    public Mole(int index, ImageView imageView) {
        this.index = index;
        this.imageView = imageView;
        this.isVisible = false;
    }

    public int getIndex() {
        return index;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}