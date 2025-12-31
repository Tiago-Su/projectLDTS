package com.aor.hero.mvc;

import com.aor.hero.StateTag;
import com.aor.hero.states.*;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

class TestHeroView {
    HeroModel mockedHeroModel;
    HeroView heroView;
    TextGraphics mockedGraphics;
    HeroState heroState;

    @BeforeEach
    void setup() {
        mockedHeroModel = Mockito.mock(HeroModel.class);
        mockedGraphics = Mockito.mock(TextGraphics.class);
        heroState = Mockito.mock(HeroState.class);

        Mockito.when(mockedHeroModel.getPosition()).thenReturn(new Vector2d(0, 0));
        Mockito.when(mockedGraphics.setBackgroundColor(Mockito.any(TextColor.class))).thenReturn(null);
        Mockito.when(mockedGraphics.setCharacter(Mockito.any(TerminalPosition.class), Mockito.anyChar())).thenReturn(null);

        Map<StateTag, List<String>> filePaths = new EnumMap<>(StateTag.class);
        Map<StateTag, List<String>> filePathsDashless = new EnumMap<>(StateTag.class);

        List<String> runningFiles = new ArrayList<>();
        runningFiles.add(Utils.createTempFile("Hero/Running/running0.png"));
        runningFiles.add(Utils.createTempFile("Hero/Running/running1.png"));
        runningFiles.add(Utils.createTempFile("Hero/Running/running2.png"));
        filePaths.put(GroundedState.tag, runningFiles);

        List<String> jumpingFiles = new ArrayList<>();
        jumpingFiles.add(Utils.createTempFile("Hero/Jumping/jumping.png"));
        filePaths.put(JumpingState.tag, jumpingFiles);

        List<String> slidingFiles = new ArrayList<>();
        slidingFiles.add(Utils.createTempFile("Hero/Sliding/sliding.png"));
        filePaths.put(SlidingState.tag, slidingFiles);

        List<String> idleFiles = new ArrayList<>();
        idleFiles.add(Utils.createTempFile("Hero/Idle/idle.png"));
        filePaths.put(IdleState.tag, idleFiles);

        List<String> fallingFiles = new ArrayList<>();
        fallingFiles.add(Utils.createTempFile("Hero/Falling/falling.png"));
        filePaths.put(FallingState.tag, fallingFiles);

        List<String> wallJumpingFiles = new ArrayList<>();
        wallJumpingFiles.add(Utils.createTempFile("Hero/Idle/idle.png"));
        filePaths.put(WallJumpState.tag, wallJumpingFiles);

        List<String> runningFilesDashless = new ArrayList<>();
        runningFilesDashless.add(Utils.createTempFile("Hero/Running/running0_v1.png"));
        runningFilesDashless.add(Utils.createTempFile("Hero/Running/running1_v1.png"));
        runningFilesDashless.add(Utils.createTempFile("Hero/Running/running2_v1.png"));
        filePathsDashless.put(GroundedState.tag, runningFilesDashless);

        List<String> jumpingFilesDashless = new ArrayList<>();
        jumpingFilesDashless.add(Utils.createTempFile("Hero/Jumping/jumping_v1.png"));
        filePathsDashless.put(JumpingState.tag, jumpingFilesDashless);

        List<String> slidingFilesDashless = new ArrayList<>();
        slidingFilesDashless.add(Utils.createTempFile("Hero/Sliding/sliding_v1.png"));
        filePathsDashless.put(SlidingState.tag, slidingFilesDashless);

        List<String> idleFilesDashless = new ArrayList<>();
        idleFilesDashless.add(Utils.createTempFile("Hero/Idle/idle_v1.png"));
        filePathsDashless.put(IdleState.tag, idleFilesDashless);

        List<String> fallingFilesDashless = new ArrayList<>();
        fallingFilesDashless.add(Utils.createTempFile("Hero/Falling/falling_v1.png"));
        filePathsDashless.put(FallingState.tag, fallingFilesDashless);

        List<String> wallJumpingFilesDashless = new ArrayList<>();
        wallJumpingFilesDashless.add(Utils.createTempFile("Hero/WallJumping/wallJumping_v1.png"));
        filePathsDashless.put(WallJumpState.tag, wallJumpingFilesDashless);


        heroView = new HeroView(mockedHeroModel, filePaths, filePathsDashless);
    }

    @Test
    void testUpdate1() {
        //Facing right and can dash and grounded
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(true);
        Mockito.when(mockedHeroModel.canDash()).thenReturn(true);
        Mockito.when(mockedHeroModel.getState()).thenReturn(heroState);

        Mockito.when(heroState.getTag()).thenReturn(StateTag.GROUNDED);

        heroView.update(mockedGraphics, 0);

        Mockito.verify(mockedHeroModel, Mockito.times(21)).getPosition();
        Mockito.verify(mockedHeroModel, Mockito.times(1)).isFacingRight();
        Mockito.verify(mockedHeroModel, Mockito.times(2)).canDash();
    }

    @Test
    void testUpdate2() {
        //Facing left and can dash and falling
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(false);
        Mockito.when(mockedHeroModel.canDash()).thenReturn(true);
        Mockito.when(mockedHeroModel.getState()).thenReturn(heroState);

        Mockito.when(heroState.getTag()).thenReturn(StateTag.FALLING);

        heroView.update(mockedGraphics, 0);

        Mockito.verify(mockedHeroModel, Mockito.times(21)).getPosition();
        Mockito.verify(mockedHeroModel, Mockito.times(1)).isFacingRight();
        Mockito.verify(mockedHeroModel, Mockito.times(2)).canDash();
    }

    @Test
    void testUpdate3() {
        //Facing right and cannot dash and idling
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(true);
        Mockito.when(mockedHeroModel.canDash()).thenReturn(false);
        Mockito.when(mockedHeroModel.getState()).thenReturn(heroState);

        Mockito.when(heroState.getTag()).thenReturn(StateTag.IDLE);

        heroView.update(mockedGraphics, 0);

        Mockito.verify(mockedHeroModel, Mockito.times(21)).getPosition();
        Mockito.verify(mockedHeroModel, Mockito.times(1)).isFacingRight();
        Mockito.verify(mockedHeroModel, Mockito.times(2)).canDash();
    }

    @Test
    void testUpdate4() {
        //Facing left and cannot dash and sliding
        Mockito.when(mockedHeroModel.isFacingRight()).thenReturn(false);
        Mockito.when(mockedHeroModel.canDash()).thenReturn(false);
        Mockito.when(mockedHeroModel.getState()).thenReturn(heroState);

        Mockito.when(heroState.getTag()).thenReturn(StateTag.SLIDING);

        heroView.update(mockedGraphics, 0);

        Mockito.verify(mockedHeroModel, Mockito.times(21)).getPosition();
        Mockito.verify(mockedHeroModel, Mockito.times(1)).isFacingRight();
        Mockito.verify(mockedHeroModel, Mockito.times(2)).canDash();
    }
}
