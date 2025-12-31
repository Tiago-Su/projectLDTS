package com.aor.tile;

import com.aor.components.BoxCollider;
import com.aor.mvc.GenericController;
import com.aor.mvc.GenericModel;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;

public class TileController implements GenericController {
    private final BoxCollider boxCollider;
    private final TileModel tileModel;
    private Vector2d speed;

    public TileController(GenericModel tileModel, String tag){
        this.tileModel = (TileModel) tileModel;
        this.boxCollider = new BoxCollider(this.tileModel.getPosition(), this.tileModel.getOffset(), this.tileModel.getWidth(), this.tileModel.getHeight(), tag);

        speed = new Vector2d(0, 0);
    }

    public TileController(GenericModel tileModel, BoxCollider boxCollider){
        this.tileModel = (TileModel) tileModel;
        this.boxCollider = boxCollider;
        speed = new Vector2d(0, 0);
    }

    @Override
    public void update(double delta) {
        Vector2d nextPosition = Utils.vectorSum(tileModel.getPosition(), speed);
        tileModel.setPosition(nextPosition);
        boxCollider.update(nextPosition);
    }

    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void setSpeed(Vector2d speed) {
        this.speed = speed;
    }
    public Vector2d getSpeed(){return speed;}

    public TileModel getTileModel(){
        return tileModel;
    }
}
