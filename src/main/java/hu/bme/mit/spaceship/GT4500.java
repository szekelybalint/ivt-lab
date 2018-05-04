package hu.bme.mit.spaceship;

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
    //demo comment

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

    if(firingMode == FiringMode.SINGLE){
      if(wasPrimaryFiredLast){
        return firePrimary() || fireSecondary();
      } else {
        return fireSecondary() || firePrimary();
      }
    } else if (firingMode == FiringMode.ALL &&
            !primaryTorpedoStore.isEmpty() && !secondaryTorpedoStore.isEmpty() &&
            primaryTorpedoStore.fire(primaryTorpedoStore.getTorpedoCount()) &&
            secondaryTorpedoStore.fire(secondaryTorpedoStore.getTorpedoCount())) {
      firingSuccess = true;
    }
    return firingSuccess;
  }

  public boolean firePrimary(){
    boolean result = false;
    if(!primaryTorpedoStore.isEmpty()){
      result = primaryTorpedoStore.fire(1);
      wasPrimaryFiredLast = true;
    }
    return result;
  }

  public boolean fireSecondary(){
    boolean result = false;
    if(!secondaryTorpedoStore.isEmpty()){
      result = secondaryTorpedoStore.fire(1);
      wasPrimaryFiredLast = false;
    }
    return result;
  }

}