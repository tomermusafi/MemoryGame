package com.musafi.memorygame.game;

public class Card {

    private int id;
    private int image;
    private boolean enable;
    private boolean discovered;

    public Card() {
    }

    public Card(int id, int image, boolean enable, boolean discovered) {
        this.id = id;
        this.image = image;
        this.enable= enable;
        this.discovered = discovered;

    }

    public int getId() {
        return id;
    }

    public Card setId(int id) {
        this.id = id;
        return this;
    }

    public int getImage() {
        return image;
    }

    public Card setImage(int image) {
        this.image = image;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public Card setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public Card setDiscovered(boolean discovered) {
        this.discovered = discovered;
        return this;
    }
}
