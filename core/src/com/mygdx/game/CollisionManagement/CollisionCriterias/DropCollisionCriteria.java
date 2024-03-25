package com.mygdx.game.CollisionManagement.CollisionCriterias;

import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.PaperBinActor;
import com.mygdx.game.EntityManagement.MetalBinActor;
import com.mygdx.game.EntityManagement.GlassBinActor;

public class DropCollisionCriteria {

    public boolean meetsCriteria(BucketActor bucket, PaperBinActor paperBin, MetalBinActor metalBin, GlassBinActor glassBin) {
        return bucket.getBounds().overlaps(paperBin.getBounds()) ||
                bucket.getBounds().overlaps(metalBin.getBounds()) ||
                bucket.getBounds().overlaps(glassBin.getBounds());
    }
}