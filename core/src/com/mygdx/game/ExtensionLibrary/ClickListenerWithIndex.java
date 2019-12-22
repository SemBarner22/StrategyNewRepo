package com.mygdx.game.ExtensionLibrary;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClickListenerWithIndex extends ClickListener {

    private ButtonWithIndex button;

    public ClickListenerWithIndex(ButtonWithIndex button) {
        this.button = button;
    }

    public ButtonWithIndex getButtonWithIndex() {
        return button;
    }
}
