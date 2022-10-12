package zombie;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.io.FileNotFoundException;

public class OrthographicCameraExampleLauncher {

    public static void main(String[] arg) throws FileNotFoundException {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Orthographic Camera");
        new Lwjgl3Application(new OrthographicCameraExample(), config);
    }

}