package com.mygdx.game.EntityManagement.Bins;

import com.mygdx.game.enums.ItemType;

public interface BinActorInterface {
    boolean acceptsItemType(ItemType itemType);
}
