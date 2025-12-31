

package com.aor.tile;

import com.aor.components.BoxCollider;
import com.aor.components.sprites.TransparentSprite;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class TestTileView {
    TileView tileView;
    BoxCollider mockedBoxCollider;
    TileModel mockedModel;
    TransparentSprite sprite;
    TextGraphics graphics;
    @BeforeEach
    void setup(){
        mockedModel= Mockito.mock(TileModel.class);
        mockedBoxCollider=Mockito.mock(BoxCollider.class);
        sprite=Mockito.mock(TransparentSprite.class);
        graphics=Mockito.mock(TextGraphics.class);
    }
    @Test
    void TestUpdate() {
        Mockito.when(mockedModel.getPosition()).thenReturn(new Vector2d(0,0));
        tileView=new TileView(mockedModel,sprite);
        tileView.update(graphics,1);

        Mockito.verify(sprite,Mockito.times(1)).draw(Mockito.any(TextGraphics.class));
        Mockito.verify(sprite,Mockito.times(1)).update(Mockito.any(Vector2d.class));
    }
}
