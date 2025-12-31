package com.aor.hero.states;

import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.utils.Timer;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestSlidingState {
    SlidingState slidingState;
    HeroModel mockedHeroModel;
    HeroController mockedHeroController;

    Timer mockedCoyoteTimer;
    Timer mockedJumpBufferTimer;
    Timer mockedWallJumpTimer;

    @BeforeEach
    void setup() {
        mockedHeroModel = Mockito.mock(HeroModel.class);
        mockedJumpBufferTimer = Mockito.mock(Timer.class);
        mockedCoyoteTimer = Mockito.mock(Timer.class);
        mockedHeroController = Mockito.mock(HeroController.class);
        mockedWallJumpTimer = Mockito.mock(Timer.class);

        Mockito.doNothing().when(mockedHeroModel).setState(Mockito.any(HeroState.class));
        Mockito.doNothing().when(mockedHeroModel).setCanDash(Mockito.anyBoolean());
        Mockito.doNothing().when(mockedHeroModel).setVelocity(Mockito.any(Vector2d.class));
        Mockito.when(mockedHeroModel.getVelocity()).thenReturn(new Vector2d(0,0));

        Mockito.when(mockedHeroController.getJumpBufferTimer()).thenReturn(mockedJumpBufferTimer);
        Mockito.when(mockedHeroController.getCoyoteTimer()).thenReturn(mockedCoyoteTimer);
        Mockito.when(mockedHeroController.getWallJumpTimer()).thenReturn(mockedWallJumpTimer);

        Mockito.doNothing().when(mockedCoyoteTimer).interrupt();
        Mockito.doNothing().when(mockedJumpBufferTimer).interrupt();
        Mockito.doNothing().when(mockedWallJumpTimer).interrupt();

        Mockito.doNothing().when(mockedHeroController).playJumpSFX();
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(true);
        Mockito.doNothing().when(mockedHeroModel).setFacingRight(Mockito.anyBoolean());

        slidingState = new SlidingState(mockedHeroModel, 1);
    }

    @Test
    void testOnEnter() {
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(true);
        Mockito.doNothing().when(mockedHeroModel).setFacingRight(Mockito.anyBoolean());

        Mockito.verify(mockedHeroModel, Mockito.times(1)).isFacingRight();
        Mockito.verify(mockedHeroModel, Mockito.times(1)).setFacingRight(Mockito.anyBoolean());
    }

    @Test
    void testTransition1() {
        //No transition
        Mockito.when(mockedHeroController.checkIfGrounded()).thenReturn(false);
        Mockito.when(mockedJumpBufferTimer.isRunning()).thenReturn(false);
        Mockito.when(mockedHeroController.checkIfLeftSliding()).thenReturn(true);
        Mockito.when(mockedHeroController.checkIfRightSliding()).thenReturn(false);

        slidingState.transition(mockedHeroController, 1);

        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(GroundedState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(WallJumpState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(FallingState.class));
    }

    @Test
    void testTransition2() {
        //Transition to grounded
        Mockito.when(mockedHeroController.checkIfGrounded()).thenReturn(true);
        Mockito.when(mockedJumpBufferTimer.isRunning()).thenReturn(false);
        Mockito.when(mockedHeroController.checkIfLeftSliding()).thenReturn(true);
        Mockito.when(mockedHeroController.checkIfRightSliding()).thenReturn(false);

        slidingState.transition(mockedHeroController, 1);

        Mockito.verify(mockedHeroModel, Mockito.times(1)).setState(Mockito.any(GroundedState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(WallJumpState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(FallingState.class));

        Mockito.verify(mockedHeroModel, Mockito.times(1)).setCanDash(true);
    }

    @Test
    void testTransition3() {
        //Transition to wall jump
        Mockito.when(mockedHeroController.checkIfGrounded()).thenReturn(false);
        Mockito.when(mockedJumpBufferTimer.isRunning()).thenReturn(true);
        Mockito.when(mockedHeroController.checkIfLeftSliding()).thenReturn(false);
        Mockito.when(mockedHeroController.checkIfRightSliding()).thenReturn(true);

        slidingState.transition(mockedHeroController, 1);

        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(GroundedState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(1)).setState(Mockito.any(WallJumpState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(FallingState.class));

        Mockito.verify(mockedWallJumpTimer, Mockito.times(1)).start();
        Mockito.verify(mockedCoyoteTimer, Mockito.times(1)).interrupt();
        Mockito.verify(mockedJumpBufferTimer, Mockito.times(1)).interrupt();
    }

    @Test
    void testTransition4() {
        //Transition to falling
        Mockito.when(mockedHeroController.checkIfGrounded()).thenReturn(false);
        Mockito.when(mockedJumpBufferTimer.isRunning()).thenReturn(false);
        Mockito.when(mockedHeroController.checkIfLeftSliding()).thenReturn(false);
        Mockito.when(mockedHeroController.checkIfRightSliding()).thenReturn(false);

        slidingState.transition(mockedHeroController, 1);

        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(GroundedState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(0)).setState(Mockito.any(WallJumpState.class));
        Mockito.verify(mockedHeroModel, Mockito.times(1)).setState(Mockito.any(FallingState.class));
    }


    @Test
    void testUpdateAndGetVelocity() {
        Mockito.when(mockedHeroModel.getDirection()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedHeroModel.getVelocity()).thenReturn(new Vector2d(0, 0));

        slidingState.update(1);

        Mockito.verify(mockedHeroModel, Mockito.times(2)).getVelocity();
        Mockito.verify(mockedHeroModel, Mockito.times(1)).setVelocity(Mockito.any(Vector2d.class));
    }

    @Test
    void testGetTag() {
        Assertions.assertEquals(StateTag.SLIDING, slidingState.getTag());
    }
}
