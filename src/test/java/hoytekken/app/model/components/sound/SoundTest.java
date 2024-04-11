package hoytekken.app.model.components.sound;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/*
 * Unit tests for the Sound class
 */
public class SoundTest {
    private ISound sound;
    private String path = "/resources/sounds/punch-3-166696.mp3";

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };

        new HeadlessApplication(listener, config);
    }

    @BeforeEach
    void setUpBeforeEach() {
        sound = new Sound(path);
    }
}
