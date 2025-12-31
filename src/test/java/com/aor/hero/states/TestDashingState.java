package com.aor.hero.states;

import com.aor.game.GameData;
import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.utils.Timer;
import com.aor.utils.Vector2d;
import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestDashingState {

    DashingState dashingState;
    HeroModel mockedHeroModel;
    HeroController mockedHeroController;

    Timer mockedDashTimer;

    @BeforeEach
    void setup() {
        mockedHeroModel = Mockito.mock(HeroModel.class);

        mockedDashTimer = Mockito.mock(Timer.class);
        mockedHeroController = Mockito.mock(HeroController.class);

        Mockito.doNothing().when(mockedHeroModel).setState(Mockito.any(HeroState.class));
        Mockito.doNothing().when(mockedHeroModel).setCanDash(Mockito.anyBoolean());
        Mockito.when(mockedHeroModel.getVelocity()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedHeroModel.getDirection()).thenReturn(new Vector2d(0, 0));
        Mockito.doNothing().when(mockedHeroModel).setVelocity(Mockito.any(Vector2d.class));
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(true);
        Mockito.doNothing().when(mockedHeroModel).setFacingRight(Mockito.anyBoolean());

        Mockito.when(mockedHeroController.getDashTimer()).thenReturn(mockedDashTimer);


        Mockito.doNothing().when(mockedDashTimer).interrupt();

        Mockito.doNothing().when(mockedHeroController).playJumpSFX();


    }

    @Property(tries = 10)
    void TestGettersAndStartingVelocity(@ForAll("genState") StateTag tag, @ForAll @Positive double delta,@ForAll("dirValNot00") Vector2d dir){
        setup();

        Mockito.when(mockedHeroModel.getDirection()).thenReturn(dir);
        dashingState = new DashingState(mockedHeroModel, mockedHeroController, tag,delta);


        Assertions.assertEquals(tag, dashingState.getLastState());
        Mockito.verify(mockedHeroModel,Mockito.times(1)).setVelocity(new Vector2d(GameData.DASH_FORCE* dir.getX(),GameData.DASH_FORCE* dir.getY()));


    }
    @Test
    void TestWhenDirIs00InitialVelocity(){
        Mockito.when(mockedHeroModel.getDirection()).thenReturn(new Vector2d(0,0));

        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(true);
        dashingState = new DashingState(mockedHeroModel, mockedHeroController, StateTag.FALLING,2);
        Mockito.verify(mockedHeroModel,Mockito.times(1)).setVelocity(new Vector2d(GameData.DASH_FORCE,0));

        setup();
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(false);
        dashingState=new DashingState(mockedHeroModel, mockedHeroController, StateTag.FALLING,2);
        Mockito.verify(mockedHeroModel,Mockito.times(1)).setVelocity(new Vector2d(-GameData.DASH_FORCE,0));

    }
    @Test
    void TestGetVelocity(){

        dashingState = new DashingState(mockedHeroModel, mockedHeroController, StateTag.FALLING,1);
        dashingState.getVelocity(1);
        Mockito.verify(mockedHeroModel,Mockito.times(1)).getVelocity();//called in update and in getVelocity
        Mockito.verify(mockedHeroModel,Mockito.times(2)).setVelocity(Mockito.any(Vector2d.class));//called in getVelocity and in the constructor


    }
    @Test
    void TestTransition1(){
        dashingState = new DashingState(mockedHeroModel, mockedHeroController, StateTag.FALLING,1);

        Mockito.when(mockedDashTimer.isRunning()).thenReturn(true); // dashing is on going

        dashingState.transition(mockedHeroController,1);

        Mockito.verify(mockedHeroModel,Mockito.times(0)).setState(Mockito.any(HeroState.class)); //keeps the same state

        Mockito.when(mockedDashTimer.isRunning()).thenReturn(false); // dashing has ended

        dashingState.transition(mockedHeroController,1);

        Mockito.verify(mockedHeroModel,Mockito.times(1)).setState(Mockito.any(FallingState.class));//change to falling state
    }

    @Provide
    Arbitrary<StateTag> genState(){
        return Arbitraries.of( StateTag.IDLE,
                StateTag.GROUNDED,
                StateTag.JUMPING,
                StateTag.FALLING,
                StateTag.SLIDING,
                StateTag.DASHING,
                StateTag.WALL_JUMPING);
    }

    @Provide
    Arbitrary<Vector2d> dirValNot00(){
        return Combinators.combine(Arbitraries.of(-1d,0d,1d),Arbitraries.of(-1d,0d,1d)).as(Vector2d::new).filter(n->!n.equals(new Vector2d(0,0)));
    }

}
