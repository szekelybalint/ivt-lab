package hu.bme.mit.spaceship;

import static hu.bme.mit.spaceship.FiringMode.ALL;
import static hu.bme.mit.spaceship.FiringMode.SINGLE;

/**
* A simple spaceship with two proton torpedo stores and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500() {
    this.primaryTorpedoStore = new TorpedoStore(10);
    this.secondaryTorpedoStore = new TorpedoStore(10);
  }

  public boolean fireLaser(FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty, the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedo(FiringMode firingMode) {

    boolean firingSuccess = false;

    if(firingMode==SINGLE) {

        // try to fire the secondary first
        if (primaryFiredLastAndSecondaryNotEmpty()) {
          firingSuccess = secondaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = false;
        }
        if(primaryFiredLastAndSecondaryEmpty()){
          // although primary was fired last time, but the secondary is empty
          // thus try to fire primary again
          if (!primaryTorpedoStore.isEmpty()) {
            firingSuccess = primaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = true;
          }
          // if both of the stores are empty, nothing can be done, return failure

      }

        // try to fire the primary first
        if (secondaryFiredLastAndPrimaryNotEmpty()) {
          firingSuccess = primaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = true;
        }
        if(secondaryFiredLastAndPrimaryEmpty()){
          // although secondary was fired last time, but primary is empty
          // thus try to fire secondary again
            firingSuccess = secondaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = false;
          // if both of the stores are empty, nothing can be done, return failure
        }

    }

    if(firingMode==ALL&&(!primaryTorpedoStore.isEmpty() && !secondaryTorpedoStore.isEmpty())&&
            (primaryTorpedoStore.fire(primaryTorpedoStore.getTorpedoCount()))&&(secondaryTorpedoStore.fire(secondaryTorpedoStore.getTorpedoCount())) ) {
      // try to fire both of the torpedo stores
      //TODO implement feature

          firingSuccess = true;
        }

    return firingSuccess;
  }

  public boolean primaryFiredLastAndSecondaryEmpty(){
    return (wasPrimaryFiredLast&&secondaryTorpedoStore.isEmpty());

  }

  public boolean primaryFiredLastAndSecondaryNotEmpty(){
    return (wasPrimaryFiredLast&&!secondaryTorpedoStore.isEmpty());

  }
  public boolean secondaryFiredLastAndPrimaryNotEmpty(){
    return (!wasPrimaryFiredLast&&!secondaryTorpedoStore.isEmpty());

  }
  public boolean secondaryFiredLastAndPrimaryEmpty(){
    return (!wasPrimaryFiredLast&&secondaryTorpedoStore.isEmpty());

  }


}
