package com.mygdx.game.CollisionManagement;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CollisionManagement.handlers.IDropCollisionHandler;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.CollisionManagement.CollisionCriterias.Criterias;
import com.mygdx.game.CollisionManagement.CollisionCriterias.PickUpCollisionCriteria;
import com.mygdx.game.CollisionManagement.handlers.PickUpCollisionHandler;
import com.mygdx.game.CollisionManagement.CollisionCriterias.DropCollisionCriteria;
import com.mygdx.game.CollisionManagement.handlers.DropCollisionHandler;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropCollisionManager {
    private List<CollidableActor> actors;
    private PaperBinActor paperBin;
    private MetalBinActor metalBin;
    private GlassBinActor glassBin;
    private PlasticBinActor plasticBin;
    private TrashBinActor trashBin;

    private Stage stage;
    private Map<Class<? extends Criterias>, Class<? extends IDropCollisionHandler>> criteriaToHandlers;

    public DropCollisionManager(List<CollidableActor> actors, PaperBinActor paperBin, MetalBinActor metalBin, GlassBinActor glassBin, PlasticBinActor plasticBin, TrashBinActor trashBin,Stage stage) {
        this.actors = actors;
        this.paperBin = paperBin;
        this.metalBin = metalBin;
        this.glassBin = glassBin;
        this.plasticBin = plasticBin;
        this.trashBin = trashBin;
        this.stage = stage;
        this.criteriaToHandlers = new HashMap<>();


        this.criteriaToHandlers.put(DropCollisionCriteria.class, DropCollisionHandler.class);
    }

    public void handleDropCollisions() {
        // Iterate over all pairs of actors.
        for (int i = 0; i < actors.size(); i++) {
            for (int j = i + 1; j < actors.size(); j++) {
                // Check if the two actors are colliding.
                if (actors.get(i).getBounds().overlaps(actors.get(j).getBounds())) {
                    // Iterate over all entries in the criteriaToHandlers map.
                    for (Map.Entry<Class<? extends Criterias>, Class<? extends IDropCollisionHandler>> entry : criteriaToHandlers.entrySet()) {
                        try {
                            Criterias criteria;
                            if (entry.getKey() == DropCollisionCriteria.class) {
                                criteria = new DropCollisionCriteria(stage);
                            } else {
                                criteria = entry.getKey().newInstance();
                            }
                            // Check if the collision meets the criteria defined by the current CollisionCriteria class.
                            if (criteria.meetsCriteria(actors.get(i), actors.get(j))) {
                                // If the criteria are met, create an instance of the corresponding CollisionHandler class and call its handleCollision method.
                                IDropCollisionHandler handler = entry.getValue().getConstructor(Actor.class, Actor.class, Actor.class, Actor.class, Actor.class, Actor.class).newInstance(actors.get(i), actors.get(j), paperBin, metalBin, glassBin, plasticBin, trashBin);
                                handler.handleDropCollision();
                                break;
                            }
                            else if (criteria.meetsCriteria(actors.get(i), actors.get(j))) {
                                // If the criteria are met, create an instance of the corresponding CollisionHandler class and call its handleCollision method.
                                IDropCollisionHandler handler = entry.getValue().getConstructor(Actor.class, Actor.class, Actor.class, Actor.class, Actor.class, Actor.class).newInstance(actors.get(i), actors.get(j), paperBin, metalBin, glassBin, plasticBin, trashBin);
                                handler.handleDropCollision();
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