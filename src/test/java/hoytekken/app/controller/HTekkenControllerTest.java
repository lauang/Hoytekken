package hoytekken.app.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import hoytekken.app.Hoytekken;
import hoytekken.app.model.HTekkenModel;
import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.EventBus;
import hoytekken.app.model.components.player.IPlayer;
import hoytekken.app.model.components.player.PlayerType;

public class HTekkenControllerTest {
    HTekkenModel model;
    HtekkenController controller;
    IPlayer player1;
    IPlayer player2;

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };
        new HeadlessApplication(listener, config);
        Gdx.gl = mock(GL20.class);
    }

    @BeforeEach
    void setUpBeforeEach() {
        model = new HTekkenModel(Mockito.mock(EventBus.class));
        controller = new HtekkenController(model);
        player1 = model.getPlayer(PlayerType.PLAYER_ONE);
        model.setNumberOfPlayers(false);
        player2 = model.getPlayer(PlayerType.PLAYER_TWO);
    }

    private boolean isWithinRange(IPlayer p1, IPlayer p2) {
        float playerWidth = 45 / Hoytekken.PPM;

        Vector2 p1Pos = new Vector2(p1.getBody().getPosition().x, p1.getBody().getPosition().y);
        Vector2 p2Pos = new Vector2(p2.getBody().getPosition().x, p2.getBody().getPosition().y);

        float distance = p1Pos.dst(p2Pos);
        float range = playerWidth * 1.8f;
        return distance <= range;
    }

    private void movePlayersBeside() {
        while (!isWithinRange(player1, player2)) {
            player1.move(100, 0);
            model.getGameWorld().step(1 / 60f, 6, 2);
        }
    }

    @Test
    void keyDownTestMainMenuI() {
        model.setGameState(GameState.MAIN_MENU);
        assertTrue(controller.keyDown(Input.Keys.I));
    }

    @Test
    void keyDownTestMainMenuESCAPE() {
        model.setGameState(GameState.MAIN_MENU);
        assertTrue(controller.keyDown(Input.Keys.ESCAPE));
    }

    @Test
    void keyDownTestMainMenuUP() {
        model.setGameState(GameState.MAIN_MENU);
        assertFalse(controller.keyDown(Input.Keys.UP));
    }

    @Test
    void keyDownTestActiveGame() {

    }

}
