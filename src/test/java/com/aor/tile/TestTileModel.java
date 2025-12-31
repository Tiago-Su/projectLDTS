package com.aor.tile;

import com.aor.utils.Vector2d;
import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Assertions;

public class TestTileModel {

    TileModel model;
    String filePath;
    String tag;
    Vector2d initialPos;
    Vector2d offset;
    int height;
    int width;

    @Property (tries = 10)
    void TestGettersAndSetters(@ForAll String filePath, @ForAll String tag, @ForAll("TVector2d") Vector2d initialPos,@ForAll("TVector2d") Vector2d offset,@ForAll @Positive int width,@ForAll @Positive int height  ){
        model=new TileModel(initialPos,offset,width,height,filePath, tag);
        Assertions.assertEquals(filePath,model.getFilePath());
        Assertions.assertEquals(model.getTag(),tag);
        Assertions.assertEquals(initialPos,model.getPosition());
        Assertions.assertEquals(model.getWidth(),width);
        Assertions.assertEquals(model.getHeight(),height);
        Assertions.assertEquals(model.getOffset(),offset);

        Vector2d newPos=new Vector2d(1,1);
        model.setPosition(newPos);

        Assertions.assertEquals(model.getPosition(),newPos);

    }

    @Provide
    Arbitrary<Vector2d> TVector2d(){
        return Combinators.combine(Arbitraries.doubles(),Arbitraries.doubles()).as(Vector2d::new);
    }
}


