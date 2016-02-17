package com.superduckinvaders.game.entity.mob;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;

/**
 * Created by james on 07/02/16.
 */
public class GunnerMob extends RangedMob {
    public GunnerMob (Round parent, float x, float y){
        super(parent, x, y, 2, Assets.rangedBadGuyNormal, 3);
    }
}
