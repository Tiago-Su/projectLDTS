package com.aor.components.sprites;

import com.aor.components.factory.GenericSpriteFactory;
import com.aor.components.factory.TransparentSpriteFactory;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestAnimatedSprite {
    GenericSprite spriteStub;
    AnimatedSprite animatedSprite;
    List<GenericSprite> sprites=new ArrayList<>();
    GenericSpriteFactory spriteFactory;
    TextGraphics graphicsStub;
    Vector2d animatedSpritePos=new Vector2d(0,0);

    @BeforeEach
    void setup() throws IOException {
        graphicsStub = Mockito.mock(TextGraphics.class);
        spriteStub = Mockito.mock(GenericSprite.class);
        spriteFactory=Mockito.mock(GenericSpriteFactory.class);

        List<String> filesPaths = new ArrayList<>();

        filesPaths.add("src/main/resources/Hero/Running/running0.png");
        filesPaths.add("src/main/resources/Hero/Running/running1.png");
        filesPaths.add("src/main/resources/Hero/Running/running2.png");

        boolean first=true;
        for(String fp:filesPaths){
            if(first){
                sprites.add(spriteStub);  // the first sprite is a stub, the others are real transparent sprites
                first=false;
            }
            else {
                sprites.add(new TransparentSprite(animatedSpritePos,fp));
            }
            Mockito.when(spriteFactory.newSprite(animatedSpritePos,fp)).thenReturn(sprites.getLast()); // the factory is being

        }
        try {
            animatedSprite = new AnimatedSprite( new Vector2d(0, 0), filesPaths,1,spriteFactory);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    void testGetterAndFrameTransition() {
        Assertions.assertEquals(0, animatedSprite.getCurrentFrame());
        Assertions.assertEquals(sprites.getFirst(), animatedSprite.getCurrentFrameSprite());
        Assertions.assertEquals(animatedSpritePos, animatedSprite.getPosition());

        //time passes but the frame is still the same and the position is changed
        animatedSprite.update(new Vector2d(0,0), 0.5f);
        Assertions.assertEquals(sprites.getFirst(), animatedSprite.getCurrentFrameSprite());
        Assertions.assertEquals(animatedSpritePos, animatedSprite.getPosition());

        //time passes, the frame is changed and the position is the same
        animatedSprite.update(new Vector2d(1,1), 0.5f);
        Assertions.assertEquals(1, animatedSprite.getCurrentFrame());
        Assertions.assertEquals(sprites.get(1), animatedSprite.getCurrentFrameSprite());
        Assertions.assertEquals(new Vector2d(1, 1), animatedSprite.getPosition());

        //frame transition
        animatedSprite.update(new Vector2d(2,2), 1f);
        Assertions.assertEquals(2, animatedSprite.getCurrentFrame());
        Assertions.assertEquals(sprites.get(2), animatedSprite.getCurrentFrameSprite());
        Assertions.assertEquals(new Vector2d(2, 2), animatedSprite.getPosition());

        Assertions.assertEquals(7, animatedSprite.getWidth());
        Assertions.assertEquals(8, animatedSprite.getHeight());
    }

    @Test
    void testEquals() {
        try {
            AnimatedSprite as = new AnimatedSprite(new Vector2d(0, 0), new ArrayList<String>(Arrays.asList("src/main/resources/Hero/Running/running0.png","src/main/resources/Hero/Running/running1.png","src/main/resources/Hero/Running/running2.png")),1,new TransparentSpriteFactory());
            Assertions.assertNotEquals(as, animatedSprite);
            Assertions.assertEquals(animatedSprite, animatedSprite);

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    void testLanternaIsCalledOnDraw() {
        animatedSprite.update(new Vector2d(1,1), 1f); // go to a frame that is not a stub
        Mockito.when(graphicsStub.setBackgroundColor(Mockito.any(TextColor.class))).thenReturn(null);
        animatedSprite.draw(graphicsStub);
        Mockito.verify(graphicsStub, Mockito.atLeastOnce()).setBackgroundColor(Mockito.any(TextColor.class));
    }

    @Test
    void testSpriteIsCalledOnDraw() {
        animatedSprite.draw(graphicsStub);
        Mockito.verify(spriteStub, Mockito.atLeastOnce()).draw(Mockito.any(TextGraphics.class));
    }
}