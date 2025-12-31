package com.aor.hero.mvc;

import com.aor.components.BoxCollider;
import com.aor.game.Arena;
import com.aor.game.InputHandler;
import com.aor.hero.states.HeroState;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

class TestHeroController {
    HeroModel mockedModel;
    Arena mockedArena;
    HeroController heroController;

    @BeforeEach
    void setup() {
        mockedModel = Mockito.mock(HeroModel.class);
        mockedArena = Mockito.mock(Arena.class);

        Mockito.when(mockedModel.getPosition()).thenReturn(new Vector2d(0, 0));
        heroController = new HeroController(mockedModel, new Vector2d(0, 0), 8, 8, mockedArena);
    }


    @Test
    void testCheckIfGrounded1() {
        //Is not colliding with nothing
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertFalse(heroController.checkIfGrounded());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfGrounded2() {
        //Is not colliding with a collider
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertTrue(heroController.checkIfGrounded());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfRightSliding1() {
        //Is not touching anything and is player is not pressing nothing
        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertFalse(heroController.checkIfRightSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }


    @Test
    void testCheckIfRightSliding2() {
        //Is not touching anything and is player is pressing the right button
        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(1, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertFalse(heroController.checkIfRightSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfRightSliding3() {
        //Is touching a wall and is player is pressing nothing
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertFalse(heroController.checkIfRightSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfRightSliding4() {
        //Is touching a wall and is player is pressing to right
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(1, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertTrue(heroController.checkIfRightSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfRightSliding5() {
        //Is touching a wall and is player is pressing to left
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(-1, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertFalse(heroController.checkIfRightSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfLeftSliding1() {
        //Is not touching anything and is player is not pressing nothing
        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertFalse(heroController.checkIfLeftSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }


    @Test
    void testCheckIfLeftSliding2() {
        //Is not touching anything and is player is pressing the left button
        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(-1, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertFalse(heroController.checkIfLeftSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfLeftSliding3() {
        //Is touching a wall and is player is pressing nothing
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertFalse(heroController.checkIfLeftSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfLeftSliding4() {
        //Is touching a wall and is player is pressing to left
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(-1, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertTrue(heroController.checkIfLeftSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testCheckIfLeftSliding5() {
        //Is touching a wall and is player is pressing to right
        List<BoxCollider> colliderList = new ArrayList<>();
        colliderList.add(new BoxCollider(new Vector2d(0, 0), 8, 8, "tile"));

        Mockito.when(mockedModel.getDirection()).thenReturn(new Vector2d(1, 0));
        Mockito.when(mockedArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString())).thenReturn(colliderList);
        Assertions.assertFalse(heroController.checkIfLeftSliding());

        Mockito.verify(mockedArena, Mockito.times(1)).detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.anyString());
    }

    @Test
    void testUpdate1() {
        //While pressing to left
        HeroState mockedState = Mockito.mock(HeroState.class);
        Mockito.when(mockedModel.getState()).thenReturn(mockedState);

        Mockito.doNothing().when(mockedState).update(Mockito.anyDouble());
        Mockito.doNothing().when(mockedState).transition(Mockito.any(HeroController.class), Mockito.anyDouble());

        Mockito.when(mockedModel.getVelocity()).thenReturn(new Vector2d(0, 0));
        Mockito.doNothing().when(mockedModel).setPosition(Mockito.any(Vector2d.class));
        InputHandler.getInstance().readKeys(KeyEvent.VK_LEFT, true);
        InputHandler.getInstance().readKeys(KeyEvent.VK_RIGHT, true);

        heroController.update(1);

        Mockito.verify(mockedModel, Mockito.times(3)).getState();
        Mockito.verify(mockedModel, Mockito.times(1)).getVelocity();
        Mockito.verify(mockedModel, Mockito.times(1)).setPosition(Mockito.any(Vector2d.class));
        Mockito.verify(mockedModel, Mockito.times(8)).getPosition();

        Mockito.verify(mockedState, Mockito.times(1)).update(Mockito.anyDouble());
        Mockito.verify(mockedState, Mockito.times(1)).transition(Mockito.any(HeroController.class), Mockito.anyDouble());
    }

    @Test
    void testUpdate2() {
        //While pressing to right
        HeroState mockedState = Mockito.mock(HeroState.class);
        Mockito.when(mockedModel.getState()).thenReturn(mockedState);

        Mockito.doNothing().when(mockedState).update(Mockito.anyDouble());
        Mockito.doNothing().when(mockedState).transition(Mockito.any(HeroController.class), Mockito.anyDouble());

        Mockito.when(mockedModel.getVelocity()).thenReturn(new Vector2d(0, 0));
        Mockito.doNothing().when(mockedModel).setPosition(Mockito.any(Vector2d.class));
        InputHandler.getInstance().readKeys(KeyEvent.VK_LEFT, false);
        InputHandler.getInstance().readKeys(KeyEvent.VK_RIGHT, true);

        heroController.update(1);

        Mockito.verify(mockedModel, Mockito.times(3)).getState();
        Mockito.verify(mockedModel, Mockito.times(1)).getVelocity();
        Mockito.verify(mockedModel, Mockito.times(1)).setPosition(Mockito.any(Vector2d.class));
        Mockito.verify(mockedModel, Mockito.times(8)).getPosition();

        Mockito.verify(mockedState, Mockito.times(1)).update(Mockito.anyDouble());
        Mockito.verify(mockedState, Mockito.times(1)).transition(Mockito.any(HeroController.class), Mockito.anyDouble());
    }

    @Test
    void testUpdate3() {
        //While pressing nothing
        HeroState mockedState = Mockito.mock(HeroState.class);
        Mockito.when(mockedModel.getState()).thenReturn(mockedState);

        Mockito.doNothing().when(mockedState).update(Mockito.anyDouble());
        Mockito.doNothing().when(mockedState).transition(Mockito.any(HeroController.class), Mockito.anyDouble());

        Mockito.when(mockedModel.getVelocity()).thenReturn(new Vector2d(0, 0));
        Mockito.doNothing().when(mockedModel).setPosition(Mockito.any(Vector2d.class));
        InputHandler.getInstance().readKeys(KeyEvent.VK_LEFT, false);
        InputHandler.getInstance().readKeys(KeyEvent.VK_RIGHT, false);

        heroController.update(1);

        Mockito.verify(mockedModel, Mockito.times(2)).getState();
        Mockito.verify(mockedModel, Mockito.times(1)).getVelocity();
        Mockito.verify(mockedModel, Mockito.times(1)).setPosition(Mockito.any(Vector2d.class));
        Mockito.verify(mockedModel, Mockito.times(8)).getPosition();

        Mockito.verify(mockedState, Mockito.times(1)).update(Mockito.anyDouble());
        Mockito.verify(mockedState, Mockito.times(1)).transition(Mockito.any(HeroController.class), Mockito.anyDouble());
    }
}
