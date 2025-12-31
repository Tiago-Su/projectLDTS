package com.aor.hero.states;

import com.aor.game.GameData;
import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;
import com.aor.hero.mvc.HeroModel;
import com.aor.mvc.GenericController;
import com.aor.mvc.GenericModel;

public class WallJumpState implements HeroState, EnterableState {
    private final HeroModel model;
    public static final StateTag tag = StateTag.WALL_JUMPING;

    public WallJumpState(GenericModel model, GenericController controller, double delta) {
        this.model = (HeroModel) model;
        ((HeroController) controller).playJumpSFX();
        onEnter(delta);
    }

    @Override
    public void onEnter(double delta) {
        int xDirection = 1;
        if (model.isFacingRight()) xDirection = -1;

        model.getVelocity().setX(GameData.WALL_JUMP_FORCE_HORIZONTAL * xDirection * -1);
        model.getVelocity().setY(GameData.WALL_JUMP_FORCE_VERTICAL);
    }

    @Override
    public void update(double delta) {getVelocity(delta);}

    @Override
    public void transition(HeroController controller, double delta) {
        if (!controller.getWallJumpTimer().isRunning()) {
            model.setState(new FallingState(model));
        }
    }

    @Override
    public void getVelocity(double delta) {}

    @Override
    public StateTag getTag() {
        return tag;
    }
}
