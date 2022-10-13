package zombie.features;

import zombie.types.AnimationBuilder;
import zombie.types.Cell;
import zombie.types.Level;

public class Wave {

    public static void animateWave(Cell cell) {
        Level level = cell.physics.level;
        level.wave = AnimationBuilder.buildAnimation("white_wave", false);
        level.wave.position.set(cell.getCenter());
        level.wave.duration = 0.77f;
        level.wave.setFps(44);
    }

}
