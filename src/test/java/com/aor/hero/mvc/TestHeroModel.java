package com.aor.hero.mvc;

import com.aor.hero.StateTag;
import com.aor.hero.states.FallingState;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestHeroModel {
    HeroModel heroModel;

    @BeforeEach
    void setup() {
        Vector2d initialPosition = new Vector2d(0, 0);
        Vector2d initialVelocity = new Vector2d(1, 2);
        Vector2d initialDirection = new Vector2d(1, 0);

        heroModel = new HeroModel(initialPosition, initialVelocity, initialDirection);
    }

    @Test
    void testSettersAndGetters() {
        heroModel.setPosition(new Vector2d(5, 1));
        Assertions.assertEquals(new Vector2d(5, 1), heroModel.getPosition());

        heroModel.setVelocity(new Vector2d(10, 3));
        Assertions.assertEquals(new Vector2d(10, 3), heroModel.getVelocity());

        heroModel.setDirectionX(-1);
        heroModel.setDirectionY(1);
        Assertions.assertEquals(new Vector2d(-1, 1), heroModel.getDirection());

        heroModel.setState(new FallingState(heroModel));
        Assertions.assertEquals(StateTag.FALLING, heroModel.getState().getTag());

        heroModel.setCanDash(false);
        Assertions.assertFalse(heroModel.canDash());

        heroModel.setFacingRight(false);
        Assertions.assertFalse(heroModel.isFacingRight());
    }
}
