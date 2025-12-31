package com.aor.hero.states;

import com.aor.hero.StateTag;
import com.aor.hero.mvc.HeroController;

public interface HeroState {
    void update(double delta);
    void getVelocity(double delta);
    void transition(HeroController controller, double delta);
    StateTag getTag();
}