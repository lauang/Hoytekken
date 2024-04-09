package hoytekken.app.model.components.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import hoytekken.app.model.components.player.IPlayer;
import hoytekken.app.model.components.player.Player;

public class ExtraHealth extends PowerUp {

    private static Integer HEALTH = 50;

    ExtraHealth() {
        super(new Texture(Gdx.files.internal("extra_health.png")));
    }

    @Override
    public void applyPowerUp(IPlayer player) {
        player.increaseHealth(HEALTH);
    }

}
