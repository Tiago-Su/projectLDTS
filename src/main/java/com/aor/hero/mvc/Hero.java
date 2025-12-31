package com.aor.hero.mvc;

import com.aor.elements.GameElement;
import com.aor.game.GameData;
import com.aor.game.GenericArena;
import com.aor.hero.StateTag;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.EnumMap;
import java.util.List;

public class Hero implements GameElement {
    Vector2d position;
    GenericArena arena;
    protected final HeroModel heroModel;
    protected final HeroView heroView;
    protected final HeroController heroController;

    public Hero(Vector2d position, GenericArena arena) {
        this.position=position;
        this.arena=arena;
        heroModel = new HeroModel(position, new Vector2d(0, 0), new Vector2d(0, 0));
        EnumMap<StateTag, List<String>> filePaths = (EnumMap<StateTag, List<String>>) GameData.getHeroFilePaths();
        EnumMap<StateTag, List<String>> filePathsDashless = (EnumMap<StateTag, List<String>>) GameData.getHeroDashlessFilePaths();

        heroView = new HeroView(heroModel, filePaths, filePathsDashless);
        int width = heroView.getAnimatedSprite().getWidth();
        int height = heroView.getAnimatedSprite().getHeight();

        heroController = new HeroController(heroModel, new Vector2d(0, 0), width, height, arena);
    }

    public void update(TextGraphics graphics, double delta) {
        heroController.update(delta);
        heroView.update(graphics, delta);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)return false;
        if(obj==this)return true;
        if(obj.getClass()!=this.getClass())return false;
        Hero o=(Hero) obj;
        return arena.equals(o.arena) && position.equals(o.position);
    }
}
