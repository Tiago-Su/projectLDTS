package com.aor.game;

import com.aor.hero.StateTag;
import com.aor.hero.states.*;
import com.aor.utils.Utils;

import java.io.File;
import java.util.*;

public class GameData {
    private GameData() {}

    public static final int SCREEN_WIDTH = 128;
    public static final int SCREEN_HEIGHT = 128;

    public static final int TILE_WIDTH = 8;
    public static final int TILE_HEIGHT = 8;

    private static Map<String ,String> tilesImgPath;
    private static Map<String, File> sfxFiles;
    
    public static final String PLAYER_TAG = "player";
    public static final String TILE_TAG = "tile";
    public static final String ALL_COLLIDERS = "all";
    public static final String END_BLOCK_TAG = "end";
    public static final String STRAWBERRY_TAG = "strawberry";
    public static final String SPIKE_TAG = "spike";
    public static final String VOID_TILE_TAG = "void";
    public static final String INV_WALL_TAG = "inv";

    public static final double MAX_HORIZONTAL_VELOCITY = 50d;
    public static final double MAX_VERTICAL_VELOCITY = 80d;
    public static final double MAX_VERTICAL_VELOCITY_FALLING = 100d;

    public static final double GROUND_FRICTION = 300d;
    public static final double AIR_FRICTION = 280d;
    public static final double DASH_FRICTION = 600d;

    public static final double HORIZONTAL_ACCELERATION = 200d;
    public static final double GRAVITY_ACCELERATION = 270d;
    public static final double GRAVITY_ACCELERATION_FALLING = 350d;

    public static final double JUMP_FORCE = -110d;
    public static final double DASH_FORCE = 180d;
    public static final double WALL_JUMP_FORCE_HORIZONTAL = 90d;
    public static final double WALL_JUMP_FORCE_VERTICAL = -90d;

    public static final double JUMP_BUFFER_TIME = 0.15d;
    public static final double COYOTE_TIME = 0.15d;
    public static final double DASH_TIME = 0.25d;
    public static final double WALL_JUMP_TIME = 0.10d;

    public static final double STRAWBERRY_SPEED = 0.1d;
    public static final double STRAWBERRY_TIME = 0.5d;

    public static final double HAIR_VELOCITY = 50d;

    public static Map<String ,String> getTileImgPath(){
        if (tilesImgPath != null) return tilesImgPath;

        tilesImgPath = new HashMap<>();
        for(Integer i = 0; i < 37; i++){
            String id = i.toString();
            if (i < 10) id = "0" + id;

            tilesImgPath.put("T" + id, Utils.createTempFile("Tiles/tile0" + id + ".png"));
        }

        for (Integer i = 0; i < 8; i++) {
            String id = i.toString();
            id = "0" + id;

            tilesImgPath.put("F" + id, Utils.createTempFile("Background/bg0" + id + ".png"));
        }

        tilesImgPath.put(STRAWBERRY_TAG, Utils.createTempFile("Misc/strawberry.png"));
        tilesImgPath.put(SPIKE_TAG, Utils.createTempFile("Misc/Spike.png"));
        tilesImgPath.put("flower", Utils.createTempFile("Misc/flower.png"));
        tilesImgPath.put("G00", Utils.createTempFile("Misc/grass0.png"));
        tilesImgPath.put("G01", Utils.createTempFile("Misc/grass1.png"));
        tilesImgPath.put("A00", Utils.createTempFile("Tree/tree0.png"));
        tilesImgPath.put("A01", Utils.createTempFile("Tree/tree1.png"));

        return tilesImgPath;
    }

    public static Map<String, File> getSfxFiles() {
        if (sfxFiles != null) return sfxFiles;

        sfxFiles = new HashMap<>();
        sfxFiles.put("ost1", new File(Utils.createTempFile("Music/ost1.wav")));
        sfxFiles.put("dash", new File(Utils.createTempFile("Music/dash.wav")));
        sfxFiles.put("jump", new File(Utils.createTempFile("Music/jump.wav")));

        return sfxFiles;
    }

    public static Map<StateTag, List<String>> getHeroFilePaths() {
        EnumMap<StateTag, List<String>> filePaths = new EnumMap<>(StateTag.class);

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

        return filePaths;
    }

    public static Map<StateTag, List<String>> getHeroDashlessFilePaths() {
        EnumMap<StateTag, List<String>> filePathsDashless = new EnumMap<>(StateTag.class);

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

        return filePathsDashless;
    }
}
