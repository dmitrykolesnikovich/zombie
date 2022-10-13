package zombie.types;

public enum Face {

    LOOKING_LEFT,
    LOOKING_RIGHT;

    public boolean isLookingLeft() {
        return this == LOOKING_LEFT;
    }

    public boolean isLookingRight() {
        return this == LOOKING_RIGHT;
    }

}
