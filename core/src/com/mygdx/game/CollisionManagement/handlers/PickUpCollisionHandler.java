package com.mygdx.game.CollisionManagement.handlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.enums.ItemType;

public class PickUpCollisionHandler extends BaseCollisionHandler {
    private Array<PaperItemsActor> paperitems;
    private Array<MetalItemsActor> metalitems;
    private Array<GlassItemsActor> glassitems;
    private Array<PlasticItemsActor> plasticitems;
    private Array<TrashItemsActor> trashitems;
    private ItemType heldItemType;
    public PickUpCollisionHandler(Actor actor1, Actor actor2, Array<PaperItemsActor> paperitems, Array<MetalItemsActor> metalitems, Array<GlassItemsActor> glassitems, Array<PlasticItemsActor> plasticitems, Array<TrashItemsActor> trashitems) {
        super(actor1, actor2);
        this.paperitems = paperitems;
        this.metalitems = metalitems;
        this.glassitems = glassitems;
        this.plasticitems = plasticitems;
        this.trashitems = trashitems;
    }
    @Override
    public void handleCollision() {
        if (actor1 instanceof BucketActor) {
            BucketActor bucket = (BucketActor) actor1;
            if (!bucket.isItemPickedUp()) {
                if (actor2 instanceof PaperItemsActor) {
                    handlePickUp(bucket, ItemType.PAPER, "paperBag.png");
                    System.out.println("Paper item picked up");
                } else if (actor2 instanceof MetalItemsActor) {
                    handlePickUp(bucket, ItemType.METAL, "metalCanSmall.png");
                    System.out.println("Metal item picked up");
                } else if (actor2 instanceof GlassItemsActor) {
                    handlePickUp(bucket, ItemType.GLASS, "glassSodaBottle.png");
                    System.out.println("Glass item picked up");
                } else if (actor2 instanceof PlasticItemsActor) {
                    handlePickUp(bucket, ItemType.PLASTIC, "plasticBottle.png");
                    System.out.println("Plastic item picked up");
                } else if (actor2 instanceof TrashItemsActor) {
                    handlePickUp(bucket, ItemType.TRASH, "styrofoam.png");
                    System.out.println("Trash item picked up");
                }
                bucket.setItemPickedUp(true);
            }
        }
    }
    private void handlePickUp(BucketActor bucket, ItemType itemType, String textureFilename) {
        // Assuming you have a way to get the correct items array based on the ItemType
        Array<Actor> items = getItemsArrayForType(itemType);

        // Cast actor2 to the correct item type based on the ItemType
        Actor itemActor = (Actor) actor2;

        // Remove the item actor
        itemActor.remove();
        // Remove the item from the corresponding array
        items.removeValue(itemActor, true);
        // Assign the ItemType to the bucket actor
        bucket.setItemType(itemType.ordinal());
        // Set the texture for the held item
        bucket.setHeldItemSprite(new Texture(Gdx.files.internal(textureFilename)));
        Gdx.app.log("PickUpCollisionHandler", "Item type assigned: " + itemType);
    }

    private Array<Actor> getItemsArrayForType(ItemType itemType) {
        // This method is not type-safe and assumes that the caller knows the correct item type.
        switch (itemType) {
            case PAPER:
                return (Array<Actor>)(Array<?>) paperitems;
            case METAL:
                return (Array<Actor>)(Array<?>) metalitems;
            case GLASS:
                return (Array<Actor>)(Array<?>) glassitems;
            case PLASTIC:
                return (Array<Actor>)(Array<?>) plasticitems;
            case TRASH:
                return (Array<Actor>)(Array<?>) trashitems;
            default:
                throw new IllegalArgumentException("Unknown ItemType: " + itemType);
        }
    }
}