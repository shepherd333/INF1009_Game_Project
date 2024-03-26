package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.EntityManagement.*;

public class DropCollisionHandler extends BaseCollisionHandler {
    private Array<PaperItemsActor> paperitems;
    private Array<MetalItemsActor> metalitems;
    private Array<GlassItemsActor> glassitems;
    private Array<PlasticItemsActor> plasticitems;
    private Array<TrashItemsActor> trashitems;

    public DropCollisionHandler(Actor actor1, Actor actor2, Array<PaperItemsActor> paperitems, Array<MetalItemsActor> metalitems, Array<GlassItemsActor> glassitems, Array<PlasticItemsActor> plasticitems, Array<TrashItemsActor> trashitems) {
        super(actor1, actor2);
        this.paperitems = paperitems;
        this.metalitems = metalitems;
        this.glassitems = glassitems;
        this.plasticitems = plasticitems;
        this.trashitems = trashitems;
    }

    @Override
    public void handleCollision() {
        BucketActor bucket = (BucketActor) actor1;

        // Reset the item picked up status to allow picking up again
        bucket.setItemPickedUp(false);

        // Optionally, you may implement additional logic here for handling drop collision
        // For example, you may want to check the type of the item being dropped and add it back to the corresponding array.

        // Example:
        if (actor2 instanceof PaperItemsActor) {
            PaperItemsActor paperItem = (PaperItemsActor) actor2;
            paperitems.add(paperItem);
        }

        // You can add similar logic for other types of items as well.
    }
}
