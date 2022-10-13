package zombie.types;

public enum Face {

    LOOKING_LEFT,
    LOOKING_STRAIGHT,
    LOOKING_RIGHT;

    public boolean isLookingLeft() {
        return this == LOOKING_LEFT;
    }

    public boolean isLookingStraight() {
        return this == LOOKING_STRAIGHT;
    }

    public boolean isLookingRight() {
        return this == LOOKING_RIGHT;
    }

}
