package com.falcao.cordstore.models.enums;

public enum Catogories {

    MOUSE(0),
    MONITOR(1),
    KEYBOARD(2),
    HEADSET(3),
    MOUSEPAD(4),
    GAMING_CHAIR(5),
    SMARTPHONE_ACCESSORIES(6);

    private int categoryIndex;

    Catogories(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public static Catogories getCategory(int categoryIndex) {
        for (Catogories cat: Catogories.values()) {
            if (cat.categoryIndex == categoryIndex) return cat;
        }
        throw new IllegalArgumentException("Category not found");
    }
}
