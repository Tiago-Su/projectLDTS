package com.aor.hero.states;

import com.aor.game.GameData;
import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.mvc.GenericModel;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;

public class SlidingState implements HeroState, EnterableState {
    private final HeroModel model;
    public static final StateTag tag = StateTag.SLIDING;

    public SlidingState(GenericModel model, double delta) {
        this.model = (HeroModel) model;
        onEnter(delta);
    }

    @Override
    public void onEnter(double delta) {
        model.setFacingRight(!model.isFacingRight());
    }

    @Override
    public void update(double delta) {
        getVelocity(delta);
    }

    @Override
    public void transition(HeroController controller, double delta) {
        if (controller.checkIfGrounded()) {
            model.setState(new GroundedState(model));
            model.setCanDash(true);

        } else if (controller.getJumpBufferTimer().isRunning()) {
            model.setState(new WallJumpState(model, controller, delta));
            controller.getWallJumpTimer().start();

            controller.getCoyoteTimer().interrupt();
            controller.getJumpBufferTimer().interrupt();

        } else if (!(controller.checkIfRightSliding() || controller.checkIfLeftSliding())) {
            model.setState(new FallingState(model));
        }
    }

    @Override
    public void getVelocity(double delta) {
        Vector2d current = model.getVelocity();
        Vector2d target = new Vector2d(0, GameData.MAX_VERTICAL_VELOCITY * 0.3);
        double xVelocity = model.getVelocity().getX();
        double yVelocity = Utils.moveTowards(current.getY(), target.getY(), GameData.GRAVITY_ACCELERATION * delta);

        model.setVelocity(new Vector2d(xVelocity, yVelocity));
    }

    @Override
    public StateTag getTag() {
        return tag;
    }
}
