package hoytekken.app.model.components.powerup;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import hoytekken.app.Hoytekken;
import hoytekken.app.model.components.player.interfaces.IPlayer;
import hoytekken.app.model.components.powerup.interfaces.IPowerUpFactory;

/**
 * Class represents the currently active power up in the game
 */
public class ActivePowerUp extends Sprite {
    private static final float POWERUP_SIZE = 30 / Hoytekken.PPM;

    private final String type;
    private final World world;
    private Body body;
    private final Texture texture;
    private final PowerUp powerUp;
    private final TiledMap map;

    private boolean isVisible = true;
    private boolean shouldBeDestroyed = false;

    private float powerUpInterval = 0;

    /**
     * Constructor for the active power up
     * 
     * @param factory the power up factory
     * @param world   the world object
     */
    public ActivePowerUp(IPowerUpFactory factory, World world, TiledMap map) {

        this.world = world;
        this.powerUp = factory.getNext();
        this.type = powerUp.getClass().getSimpleName();
        this.texture = powerUp.getTexture();
        this.map = map;

        setRegion(texture);
        defineBody();
        setBounds(0, 0, POWERUP_SIZE, POWERUP_SIZE);
        positionBody();
        positionTexture();
    }

    private void positionBody() {
        if (this.map == null)
            return;
        float x = (float) Math.random() * Hoytekken.V_WIDTH / Hoytekken.PPM;
        float y = (float) Math.random() * Hoytekken.V_HEIGHT / Hoytekken.PPM;
        float angle = 0;
        MapLayers layers = map.getLayers();
        for (MapLayer layer : layers) {
            if (layer instanceof TiledMapTileLayer)
                continue;
            for (RectangleMapObject tempRect : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = tempRect.getRectangle();
                float rectX = rect.getX() / Hoytekken.PPM;
                float rectY = rect.getY() / Hoytekken.PPM;
                float rectWidth = (rect.getWidth() / Hoytekken.PPM);
                float rectHeight = (rect.getHeight() / Hoytekken.PPM);

                if (x + POWERUP_SIZE / 2 > rectX && x - POWERUP_SIZE / 2 < rectX + rectWidth
                        && y + POWERUP_SIZE / 2 > rectY && y - POWERUP_SIZE / 2 < rectY + rectHeight) {
                    positionBody();
                    return;
                }
            }
        }
        body.setTransform(x, y, angle);

    }

    private void positionTexture() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    private void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        // Set the user data to this object
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(POWERUP_SIZE / 2, POWERUP_SIZE / 2);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this.type + "powerUp");
    }

    /**
     * Updates the powerup
     * includes updating the timer for the powerup and removing it
     */
    public void update(float dt) {
        if (isVisible) {
            powerUpInterval += dt;
            if (powerUpInterval >= 3) {
                makeInvisible();
            }
        }
    }

    /**
     * Method to get the body of the powerup
     * 
     * @return the body of the powerup
     */
    public Body getBody() {
        return body;
    }

    /**
     * Marks the powerup for destruction
     */
    public void markForDestruction() {
        shouldBeDestroyed = true;
    }

    /**
     * Makes the powerup visible
     */
    public void makeVisible() {
        isVisible = true;
        powerUpInterval = 0;
    }

    /**
     * Checks if the powerup is visible
     * 
     * @return true if the powerup is visible, false otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Checks if the powerup should be destroyed
     * 
     * @return true if the powerup should be destroyed, false otherwise
     */
    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

    /**
     * Applies the powerup to the player
     */
    public void apply(IPlayer player) {
        powerUp.applyPowerUp(player);
    }

    /**
     * Makes the powerup invisible
     */
    public void makeInvisible() {
        this.isVisible = false;
    }

}
