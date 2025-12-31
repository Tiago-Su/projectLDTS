package com.aor.hero.mvc;

import com.aor.components.BoxCollider;
import com.aor.components.RigidBody;
import com.aor.game.GameData;
import com.aor.game.GenericArena;
import com.aor.game.InputHandler;
import com.aor.hero.states.SlidingState;
import com.aor.hero.states.DashingState;
import com.aor.music.MusicPlayer;
import com.aor.mvc.GenericModel;
import com.aor.utils.*;
import com.aor.mvc.GenericController;
import com.aor.utils.Timer;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;

public class HeroController implements GenericController {
    private final HeroModel model;
    protected final RigidBody rigidBody;

    //Colliders
    protected final BoxCollider boxCollider;
    protected final BoxCollider hitBox;

    private final BoxCollider groundCollider;
    private final BoxCollider leftWallCollider;
    private final BoxCollider rightWallCollider;

    private final GenericArena arena;
    private final MusicPlayer jumpSFX;
    private final MusicPlayer dashSFX;

    //Timers
    private final Timer jumpBufferTimer;
    private final Timer coyoteTimer;
    private final Timer dashTimer;
    private final Timer wallJumpTimer;

    public HeroController(GenericModel model, Vector2d initialVelocity, int width, int height, GenericArena arena) {
        String playerTag = GameData.PLAYER_TAG;

        this.model = (HeroModel) model;
        this.boxCollider = new BoxCollider(this.model.getPosition(), width, height, playerTag);
        this.hitBox = new BoxCollider(this.model.getPosition(), new Vector2d(1, 1), 5, 7, playerTag);
        this.rigidBody = new RigidBody(this.model.getPosition(), initialVelocity, arena, boxCollider);

        //Ground collider
        Vector2d groundColliderOffset =  new Vector2d(1, height - 1d);
        this.groundCollider = new BoxCollider(this.model.getPosition(), groundColliderOffset, 6, 2, playerTag);

        //Left wall collider
        Vector2d leftWallOffset = new Vector2d(-1, 0);
        this.leftWallCollider = new BoxCollider(this.model.getPosition(), leftWallOffset, 2, 8, playerTag);

        //Right Wall collider
        Vector2d rightWallOffset = new Vector2d(width - 1d, 0);
        this.rightWallCollider = new BoxCollider(this.model.getPosition(), rightWallOffset, 2, 8, playerTag);

        this.arena = arena;

        MusicPlayer sound;
        try {
            Clip jumpClip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameData.getSfxFiles().get("jump"));
            jumpClip.open(audioInputStream);

            sound = new MusicPlayer(jumpClip, false, false);
        } catch (Exception e) {
            sound = null;
        }

        this.jumpSFX = sound;

        try {
            Clip dashClip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameData.getSfxFiles().get("dash"));
            dashClip.open(audioInputStream);

            sound = new MusicPlayer(dashClip, false, false);
        } catch (Exception e) {
            sound = null;
        }

        this.dashSFX = sound;

        //Timers
        this.jumpBufferTimer = new Timer(GameData.JUMP_BUFFER_TIME, false);
        this.coyoteTimer = new Timer(GameData.COYOTE_TIME, false);
        this.dashTimer = new Timer(GameData.DASH_TIME, false);
        this.wallJumpTimer = new Timer(GameData.WALL_JUMP_TIME, false);
    }

    public boolean checkIfGrounded() {
        groundCollider.update(model.getPosition());
        return !arena.detectCollision(groundCollider.getPosition(), groundCollider, GameData.TILE_TAG).isEmpty();
    }

    public boolean checkIfLeftSliding() {
        leftWallCollider.update(model.getPosition());
        return !arena.detectCollision(leftWallCollider.getPosition(), leftWallCollider, GameData.TILE_TAG).isEmpty() && model.getDirection().getX() < 0;
    }

    public boolean checkIfRightSliding() {
        rightWallCollider.update(model.getPosition());
        return !arena.detectCollision(rightWallCollider.getPosition(), rightWallCollider, GameData.TILE_TAG).isEmpty() && model.getDirection().getX() > 0;
    }

    private void checkCollisionsWithHitBox() {
        hitBox.update(model.getPosition());
        arena.detectCollision(hitBox.getPosition(), hitBox, GameData.ALL_COLLIDERS);
    }

    private void dash(double delta) {
        model.setState(new DashingState(model, this, model.getState().getTag(), delta));
        model.setCanDash(false);

        dashTimer.start();
        coyoteTimer.interrupt();
        jumpBufferTimer.interrupt();
        wallJumpTimer.interrupt();
    }

    private void handleInputs(double delta) {
        //Check horizontal inputs
        if (InputHandler.getInstance().getKeyPressed(KeyEvent.VK_LEFT)) {
            model.setDirectionX(-1);
            if (!(model.getState() instanceof SlidingState)) model.setFacingRight(false);

        } else if (InputHandler.getInstance().getKeyPressed(KeyEvent.VK_RIGHT)) {
            model.setDirectionX(1);
            if (!(model.getState() instanceof SlidingState)) model.setFacingRight(true);

        } else {
            model.setDirectionX(0);
        }

        //Check vertical inputs
        if (InputHandler.getInstance().getKeyPressed(KeyEvent.VK_UP)) {
            model.setDirectionY(-1);

        } else if (InputHandler.getInstance().getKeyPressed(KeyEvent.VK_DOWN)) {
            model.setDirectionY(1);

        } else {
            model.setDirectionY(0);
        }

        if (InputHandler.getInstance().getKeyDown(KeyEvent.VK_C)) {
            jumpBufferTimer.start();
        }

        if (model.canDash() && InputHandler.getInstance().getKeyDown(KeyEvent.VK_X)) {
            dash(delta);
        }
    }

    @Override
    public void update(double delta) {
        //Get inputs and calculate velocity
        handleInputs(delta);
        model.getState().update(delta);

        //Move
        rigidBody.setVelocity(model.getVelocity());

        //Update rigidBody and boxColliders and model
        model.setPosition(rigidBody.move(delta));
        rigidBody.update(model.getPosition());

        //Timers updates
        jumpBufferTimer.update(delta);
        coyoteTimer.update(delta);
        dashTimer.update(delta);
        wallJumpTimer.update(delta);

        //Check for transitions
        model.getState().transition(this, delta);
        checkCollisionsWithHitBox();
    }

    public BoxCollider getHitBox() {
        return hitBox;
    }

    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void playDashSFX() {
        if (dashSFX == null) return;
        this.dashSFX.start();
    }
    public void playJumpSFX() {
        if (jumpSFX == null) return;
        this.jumpSFX.start();
    }
    public Timer getCoyoteTimer() {
        return coyoteTimer;
    }
    public Timer getDashTimer() {
        return dashTimer;
    }
    public Timer getJumpBufferTimer() {
        return jumpBufferTimer;
    }
    public Timer getWallJumpTimer() {
        return wallJumpTimer;
    }
    public void setVelocity(Vector2d velocity){
        model.setVelocity(velocity);
    }
}