package com.aor.tile;

import com.aor.components.BoxCollider;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestTileController {
    TileController tileController;
    BoxCollider mockedBoxCollider;
    TileModel mockedModel;
    @BeforeEach
    void setup(){
        mockedModel= Mockito.mock(TileModel.class);
        mockedBoxCollider=Mockito.mock(BoxCollider.class);
    }
    @Test
    void TestUpdate(){
        Mockito.when(mockedModel.getPosition()).thenReturn(new Vector2d(0,0));
        tileController=new TileController(mockedModel, mockedBoxCollider);
        tileController.update(1);
        Mockito.verify(mockedBoxCollider,Mockito.times(1)).update(Mockito.any(Vector2d.class));
    }
    @Test
    void TestGetCollider(){
        tileController = new TileController(mockedModel, mockedBoxCollider);
        Assertions.assertEquals(tileController.getBoxCollider(),mockedBoxCollider);
    }
    @Test
    void TestSetAndGetSpeed(){
        tileController=new TileController(mockedModel ,mockedBoxCollider);
        Vector2d speed=new Vector2d(1,1);
        tileController.setSpeed(speed);
        Assertions.assertEquals(speed,tileController.getSpeed());
    }


}
