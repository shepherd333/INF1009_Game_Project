//package com.mygdx.game.CollisionManagement.CollisionCriterias;
//
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.mygdx.game.EntityManagement.BucketActor;
//import com.mygdx.game.EntityManagement.RaindropActor;
//
//public class CollectCollisionCriteria implements Criterias {
//    @Override
//    public boolean meetsCriteria(Actor actor1, Actor actor2) {
//        // Check if the actors are instances of BucketActor and RaindropActor
//        return (actor1 instanceof BucketActor && actor2 instanceof RaindropActor) ||
//                (actor1 instanceof RaindropActor && actor2 instanceof BucketActor);
//    }
//}