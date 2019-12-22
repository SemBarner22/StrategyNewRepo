package com.mygdx.game.ExtensionLibrary;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonWithIndex extends TextButton {
    private int index;
    public ButtonWithIndex(String text, Skin skin, int index) {
        super(text, skin);
        this.index = index;
    }

    public ButtonWithIndex(String text, Skin skin, String styleName, int index) {
        super(text, skin, styleName);
        this.index = index;
    }

    public ButtonWithIndex(String text, TextButtonStyle style, int index) {
        super(text, style);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
