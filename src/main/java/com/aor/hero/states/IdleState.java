package com.aor.hero.states;

import com.aor.game.GameData;
import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.mvc.GenericModel;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;

public class IdleState implements HeroState {
    private final HeroModel model;
    public static final StateTag tag = StateTag.IDLE;

    public IdleState(GenericModel model) {
        this.model = (HeroModel) model;
    }

    @Override
    public void transition(HeroController controller, double delta) {
        if (model.getDirection().getX() != 0) {
            model.setState(new GroundedState(model));

        } else if (controller.getJumpBufferTimer().isRunning()) {
            model.setState(new JumpingState(model, controller, delta));

            controller.getCoyoteTimer().interrupt();
            controller.getJumpBufferTimer().interrupt();
            controller.getWallJumpTimer().interrupt();
        }
    }

    @Override
    public void update(double delta) {
        getVelocity(delta);
    }

    @Override
    public void getVelocity(double delta) {
        Vector2d direction = model.getDirection();
        Vector2d current = model.getVelocity();
        double maxHorizontalVelocity;
        double maxHorizontalDelta;

        if (direction.getX() == 0) {
            maxHorizontalVelocity = 0;
            maxHorizontalDelta = GameData.GROUND_FRICTION * delta;
        } else {
            maxHorizontalVelocity = GameData.MAX_HORIZONTAL_VELOCITY * direction.getX();
            maxHorizontalDelta = GameData.HORIZONTAL_ACCELERATION * delta;
        }

        Vector2d target = new Vector2d(maxHorizontalVelocity, GameData.MAX_VERTICAL_VELOCITY);
        double xVelocity = Utils.moveTowards(current.getX(), target.getX(), maxHorizontalDelta);
        double yVelocity = Utils.moveTowards(current.getY(), target.getY(), GameData.GRAVITY_ACCELERATION * delta);

        model.setVelocity(new Vector2d(xVelocity, yVelocity));
    }

    @Override
    public StateTag getTag() {
        return tag;
    }
}
