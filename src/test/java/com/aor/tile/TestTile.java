package com.aor.tile;

import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestTile {
    Tile tile;
    TileController mockedController;
    TileView mockedView;
    TileModel mockedModel;
    TextGraphics graphics;
    boolean setupOnce;

    @BeforeEach
    void setup() {
        if(!setupOnce){
            mockedController= Mockito.mock(TileController.class);
            mockedModel=Mockito.mock(TileModel.class);
            mockedView=Mockito.mock(TileView.class);
            graphics=Mockito.mock(TextGraphics.class);

            Mockito.when(mockedView.getTileModel()).thenReturn(mockedModel);
            Mockito.when(mockedController.getTileModel()).thenReturn(mockedModel);
            tile=new Tile(mockedView,mockedModel,mockedController);

            setupOnce=true;
        }

    }
    @Test
    void TestUpdate() {
        double delta=1;
        tile.update(graphics,delta);
        Mockito.verify(mockedController,Mockito.times(1)).update(delta);
        Mockito.verify(mockedView,Mockito.times(1)).update(graphics,delta);

    }
    @Test
    void  TestGetBoxCollider(){
        tile.getBoxCollider();
        Mockito.verify(mockedController,Mockito.times(1)).getBoxCollider();
    }

    @Test
    void  TestSetSpeed() {
        Vector2d speed=new Vector2d(0,0);
        tile.setSpeed(speed);
        Mockito.verify(mockedController,Mockito.times(1)).setSpeed(speed);
    }
}
