package zombie;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.io.FileNotFoundException;

public class DesktopLauncher {

    public static void main(String[] arg) throws FileNotFoundException {
        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Zombie");
        config.setWindowedMode(displayMode.width, displayMode.height);
        new Lwjgl3Application(new Context(), config);
    }

}
