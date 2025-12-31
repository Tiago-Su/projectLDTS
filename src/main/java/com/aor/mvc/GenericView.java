package com.aor.mvc;

import com.googlecode.lanterna.graphics.TextGraphics;

public interface GenericView {
    void update(TextGraphics graphics, double delta);
}