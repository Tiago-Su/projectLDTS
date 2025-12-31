package com.aor.game_loader.loaders;

import com.aor.game.GenericArena;
import com.aor.hero.mvc.Hero;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class HeroLoader implements GameElementLoader {
    GenericArena arena;
    public HeroLoader(){
        this.arena=null;
    }
    public void setArena(GenericArena arena){
        this.arena=arena;
    }
    GenericArena getArena(){
        return arena;
    }

    @Override
    public Hero parse(String data, Vector2d pos) throws IOException {
        return new Hero(pos,arena);
    }

}
