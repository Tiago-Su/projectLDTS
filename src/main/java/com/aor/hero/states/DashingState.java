package com.aor.hero.states;

import com.aor.game.GameData;
import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.mvc.GenericController;
import com.aor.mvc.GenericModel;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;

public class DashingState implements HeroState, EnterableState {
    private final HeroModel model;
    private final Vector2d direction;
    private final StateTag lastState;
    public static final StateTag tag = StateTag.DASHING;

    public DashingState(GenericModel model, GenericController controller, StateTag lastState, double delta) {
        this.model = (HeroModel) model;
        this.direction = ((HeroModel) model).getDirection();
        this.lastState = lastState;
        ((HeroController) controller).playDashSFX();

        if (direction.getX() == 0 && direction.getY() == 0) {
            if (this.model.isFacingRight()) {
                direction.setX(1);
            } else {
                direction.setX(-1);
            }
        }

        if (direction.getX() == 0 && direction.getY() == 0) direction.setX(1);
        onEnter(delta);
    }

    @Override
    public void onEnter(double delta) {
        model.setVelocity(Utils.scalarProduct(direction, GameData.DASH_FORCE));
    }

    @Override
    public void transition(HeroController controller, double delta) {
        if (!controller.getDashTimer().isRunning()) {
            model.setState(new FallingState(model));
        }
    }

    @Override
    public void update(double delta) {
        getVelocity(delta);
    }

    @Override
    public void getVelocity(double delta) {
        Vector2d current = model.getVelocity();
        double maxHorizontalDelta = GameData.DASH_FRICTION * delta;

        double xVelocity = Utils.moveTowards(current.getX(), 0, maxHorizontalDelta);
        double yVelocity = Utils.moveTowards(current.getY(), 0, maxHorizontalDelta);

        model.setVelocity(new Vector2d(xVelocity, yVelocity));
    }

    public StateTag getLastState() {
        return lastState;
    }

    @Override
    public StateTag getTag() {
        return tag;
    }
}