    package com.mygdx.game.CollisionManagement;

    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.utils.Array;
    import com.mygdx.game.CollisionManagement.handlers.ICollisionHandler;
    import com.mygdx.game.EntityManagement.*;
    import com.mygdx.game.CollisionManagement.CollisionCriterias.Criterias;
    //import com.mygdx.game.CollisionManagement.CollisionCriterias.CollectCollisionCriteria;
    //import com.mygdx.game.CollisionManagement.handlers.CollectCollisionHandler;
    import com.mygdx.game.CollisionManagement.CollisionCriterias.PickUpCollisionCriteria;
    import com.mygdx.game.CollisionManagement.handlers.PickUpCollisionHandler;
    import com.badlogic.gdx.scenes.scene2d.Actor;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class CollisionManager {
        private List<CollidableActor> actors;
        private Array<PaperItemsActor> paperitems;
        private Array<MetalItemsActor> metalitems;
        private Array<GlassItemsActor> glassitems;
        private Array<PlasticItemsActor> plasticitems;
        private Array<TrashItemsActor> trashitems;

        private Stage stage;
        private Map<Class<? extends Criterias>, Class<? extends ICollisionHandler>> criteriaToHandlers;

        public CollisionManager(List<CollidableActor> actors, Array<PaperItemsActor> paperitems, Array<MetalItemsActor> metalitems,Array<GlassItemsActor> glassitems, Array<PlasticItemsActor> plasticitems, Array<TrashItemsActor> trashitems,Stage stage) {
            this.actors = actors;
            this.paperitems = paperitems;
            this.metalitems = metalitems;
            this.glassitems = glassitems;
            this.plasticitems = plasticitems;
            this.trashitems = trashitems;
            this.stage = stage;
            this.criteriaToHandlers = new HashMap<>();

            // Link each CollisionCriteria class to its corresponding CollisionHandler class
    //        this.criteriaToHandlers.put(CollectCollisionCriteria.class, CollectCollisionHandler.class);
            this.criteriaToHandlers.put(PickUpCollisionCriteria.class, PickUpCollisionHandler.class);
            // Add more entries as needed...
        }

        public void handleCollisions() {
            // Iterate over all pairs of actors.
            for (int i = 0; i < actors.size(); i++) {
                for (int j = i + 1; j < actors.size(); j++) {
                    // Check if the two actors are colliding.
                    if (actors.get(i).getBounds().overlaps(actors.get(j).getBounds())) {
                        // Iterate over all entries in the criteriaToHandlers map.
                        for (Map.Entry<Class<? extends Criterias>, Class<? extends ICollisionHandler>> entry : criteriaToHandlers.entrySet()) {
                            try {
                                // Create an instance of the current CollisionCriteria class.
                                Criterias criteria;
                                if (entry.getKey() == PickUpCollisionCriteria.class) {
                                    criteria = new PickUpCollisionCriteria(stage);
                                } else {
                                    criteria = entry.getKey().newInstance();
                                }
                                // Check if the collision meets the criteria defined by the current CollisionCriteria class.
                                if (criteria.meetsCriteria(actors.get(i), actors.get(j))) {
                                    // If the criteria are met, create an instance of the corresponding CollisionHandler class and call its handleCollision method.
                                    ICollisionHandler handler = entry.getValue().getConstructor(Actor.class, Actor.class, Array.class, Array.class, Array.class, Array.class, Array.class).newInstance(actors.get(i), actors.get(j), paperitems, metalitems, glassitems, plasticitems, trashitems);
                                    handler.handleCollision();
                                    break;
                                }
                                else if (criteria.meetsCriteria(actors.get(i), actors.get(j))) {
                                    // If the criteria are met, create an instance of the corresponding CollisionHandler class and call its handleCollision method.
                                    ICollisionHandler handler = entry.getValue().getConstructor(Actor.class, Actor.class, Array.class, Array.class, Array.class, Array.class, Array.class).newInstance(actors.get(i), actors.get(j), paperitems, metalitems, glassitems, plasticitems, trashitems);
                                    handler.handleCollision();
                                    break;
                                }
                            } catch (Exception e) {
                                // Print any exceptions that occur during the collision handling process.
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }