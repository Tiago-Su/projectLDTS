package com.aor.hero.mvc;

import java.util.*;

import com.aor.components.factory.TransparentSpriteFactory;
import com.aor.components.sprites.AnimatedSprite;
import com.aor.components.sprites.HairComponent;
import com.aor.hero.StateTag;
import com.aor.hero.states.DashingState;
import com.aor.hero.states.IdleState;
import com.aor.mvc.GenericModel;
import com.aor.mvc.GenericView;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;

public class HeroView implements GenericView {
    private HeroModel model;
    private final Map<StateTag, AnimatedSprite> sprites = new EnumMap<>(StateTag.class);
    private final Map<StateTag, AnimatedSprite> spritesDashless = new EnumMap<>(StateTag.class);
    private AnimatedSprite currentSprite;
    private StateTag currentState;

    private final List<HairComponent> normalHair = new ArrayList<>();
    private final List<HairComponent> blueHair = new ArrayList<>();

    public HeroView(GenericModel model, Map<StateTag, List<String>> filePaths, Map<StateTag, List<String>> filesPathsDashless) {
        try {
            this.model = (HeroModel) model;
            TransparentSpriteFactory factory = new TransparentSpriteFactory();

            for (Map.Entry<StateTag, List<String>> entry : filePaths.entrySet()) {
                sprites.put(entry.getKey(), new AnimatedSprite(this.model.getPosition(), filePaths.get(entry.getKey()), 5, factory));
                spritesDashless.put(entry.getKey(), new AnimatedSprite(this.model.getPosition(), filesPathsDashless.get(entry.getKey()), 5, factory));
            }

            currentState = IdleState.tag;
            currentSprite = sprites.get(currentState);

            normalHair.add(new HairComponent(this.model.getPosition(), new Vector2d(3,2), this.model, new Vector2d(4,4),new Vector2d(-4,0),new Vector2d(-2,2), Utils.createTempFile("Hero/hair/hair0.png")));
            normalHair.add(new HairComponent(normalHair.get(0).getPosition(), new Vector2d(0,0), normalHair.get(0),new Vector2d(0,2),new Vector2d(-0.5,0.5),new Vector2d(-0.5,0.5),Utils.createTempFile("Hero/hair/hair1.png")));
            normalHair.add(new HairComponent(normalHair.get(1).getPosition(), new Vector2d(0,0), normalHair.get(1),new Vector2d(0,1),new Vector2d(-0.5,0.5),new Vector2d(-0.5,0.5),Utils.createTempFile("Hero/hair/hair1.png")));
            normalHair.add(new HairComponent(normalHair.get(2).getPosition(), new Vector2d(0,0), normalHair.get(2),new Vector2d(0,1),new Vector2d(0,0),new Vector2d(-0.5,0.5),Utils.createTempFile("Hero/hair/hair2.png")));

            blueHair.add(new HairComponent(this.model.getPosition(),new Vector2d(3,2),this.model,new Vector2d(4,4),new Vector2d(-4,0),new Vector2d(-2,2),Utils.createTempFile("Hero/hair/hair0_v1.png")));
            blueHair.add(new HairComponent(blueHair.get(0).getPosition(),new Vector2d(0,0),blueHair.get(0),new Vector2d(0,2),new Vector2d(-0.5,0.5),new Vector2d(-0.5,0.5),Utils.createTempFile("Hero/hair/hair1_v1.png")));
            blueHair.add(new HairComponent(blueHair.get(1).getPosition(),new Vector2d(0,0),blueHair.get(1),new Vector2d(0,1),new Vector2d(-0.5,0.5),new Vector2d(-0.5,0.5),Utils.createTempFile("Hero/hair/hair1_v1.png")));
            blueHair.add(new HairComponent(blueHair.get(2).getPosition(),new Vector2d(0,0),blueHair.get(2),new Vector2d(0,1),new Vector2d(0,0),new Vector2d(-0.5,0.5),Utils.createTempFile("Hero/hair/hair2_v1.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkState() {
        Map<StateTag, AnimatedSprite> map = sprites;
        if (!model.canDash()) map = spritesDashless;

        if (currentState != model.getState().getTag()) {
            currentState = model.getState().getTag();
            if (model.getState() instanceof DashingState dashingState) currentState = dashingState.getLastState();
            currentSprite = map.get(currentState);
        }
    }

    @Override
    public void update(TextGraphics graphics, double delta) {
        checkState();
        currentSprite.update(model.getPosition(), delta);

        if (model.isFacingRight()){
            currentSprite.normal();
        } else {
            currentSprite.xFlip();
        }

        for (HairComponent h : normalHair){
            h.update(delta);
        }
        for (HairComponent h : blueHair){
            h.update(delta);
        }

        if (!model.canDash()) {
            for (HairComponent h : blueHair){
                h.draw(graphics);
            }
        } else {
            for (HairComponent h : normalHair){
                h.draw(graphics);
            }
        }

        currentSprite.draw(graphics);
    }

    public AnimatedSprite getAnimatedSprite() {
        return currentSprite;
    }
}