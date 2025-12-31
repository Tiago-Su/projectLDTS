package com.aor.hero.states;

import com.aor.game.GameData;
import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.mvc.GenericController;
import com.aor.mvc.GenericModel;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;


public class JumpingState implements HeroState, EnterableState {
    private final HeroModel model;
    public static final StateTag tag = StateTag.JUMPING;

    public JumpingState(GenericModel model, GenericController controller, double delta) {
        this.model = (HeroModel) model;
        ((HeroController) controller).playJumpSFX();
        onEnter(delta);
    }

    @Override
    public void onEnter(double delta) {
        model.getVelocity().setY(GameData.JUMP_FORCE);
    }

    @Override
    public void update(double delta) {
        getVelocity(delta);
    }

    @Override
    public void transition(HeroController controller, double delta) {
        if (model.getVelocity().getY() >= 0) {
            model.setState(new FallingState(model));

        } else if (controller.checkIfLeftSliding() || controller.checkIfRightSliding()) {
            model.setState(new SlidingState(model, delta));
        }
    }

    @Override
    public void getVelocity(double delta) {
        Vector2d direction = model.getDirection();
        Vector2d current = model.getVelocity();
        double maxHorizontalVelocity;
        double maxHorizontalDelta;

        if (direction.getX() == 0) {
            maxHorizontalVelocity = 0;
            maxHorizontalDelta = GameData.AIR_FRICTION * delta;
        } else {
            maxHorizontalVelocity = GameData.MAX_HORIZONTAL_VELOCITY * direction.getX();
            maxHorizontalDelta = GameData.HORIZONTAL_ACCELERATION * delta;
        }

        Vector2d target = new Vector2d(maxHorizontalVelocity, 0);
        double xVelocity = Utils.moveTowards(current.getX(), target.getX(), maxHorizontalDelta);
        double yVelocity = Utils.moveTowards(current.getY(), target.getY(), GameData.GRAVITY_ACCELERATION * delta);

        model.setVelocity(new Vector2d(xVelocity, yVelocity));
    }

    @Override
    public StateTag getTag() {
        return tag;
    }
}