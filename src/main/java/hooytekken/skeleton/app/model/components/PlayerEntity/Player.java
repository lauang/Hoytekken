package hooytekken.skeleton.app.model.components.PlayerEntity;

import hooytekken.skeleton.app.Hoytekken;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The player class
 */
public class Player extends Sprite implements IPlayer {
    private World world;
    private Body body;
    private TextureRegion playerTexture;

    // The type of player (player one or player two)
    private PlayerType type;

    // Check if the player is alive
    private boolean isAlive = true;

    // Health, if health is 0, player is dead
    private int health = 100;

    // if attack is over limit, block is unsuccessful
    private int blockLimit = 30;

    // if block is unsuccessful, divide attack by this
    private int blockSupresser = 2;

    private static final float PLAYER_RADIUS = 20 / Hoytekken.PPM;

    public Player(World world, PlayerType type) {
        this.world = world;
        this.type = type;

        definePlayer();
        setBounds(0, 0, 45 / Hoytekken.PPM, 45 / Hoytekken.PPM);
    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set((32 * (type == PlayerType.PLAYER_ONE ? 10 : 20)) / Hoytekken.PPM,
                (32 * 14) / Hoytekken.PPM);

        body = world.createBody(bdef);

        // Set the user data to this object
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(PLAYER_RADIUS);

        fdef.shape = shape;
        body.createFixture(fdef);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - playerTexture.getRegionWidth() / 2,
                body.getPosition().y - playerTexture.getRegionHeight() / 2);
    }

    public Body getBody() {
        return body;
    }

    public void move(float deltaX, float deltaY) {
        body.applyLinearImpulse(new Vector2(deltaX, deltaY), body.getWorldCenter(), true);
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;

        if (this.health <= 0) {
            this.isAlive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public int getHealth() {
        return health;
    }

    private boolean isWithinRange(Player that) {
        Vector2 thisPos = new Vector2(getBody().getPosition().x, getBody().getPosition().y);
        Vector2 thatPos = new Vector2(that.getBody().getPosition().x, that.getBody().getPosition().y);

        float distance = thisPos.dst(thatPos);
        float range = PLAYER_RADIUS * 2; // punch can reach the lenght of body
        return distance <= range;
    }

    @Override
    public boolean punch(Player that, int dmg) {
        if (!isWithinRange(that)) {
            return false;
        }
        if (this.isAlive() && that.isAlive()) {
            that.takeDamage(dmg);
            return true;
        }
        return false;
    }

    @Override
    public boolean kick(Player that, int dmg) {
        if (!isWithinRange(that)) {
            return false;
        }
        if (this.isAlive() && that.isAlive()) {
            that.takeDamage(dmg);
            return true;
        }
        return false;
    }

    @Override
    public boolean block(Player that, int incomingAttack) {
        if (this.isAlive() && incomingAttack > blockLimit && isWithinRange(that)) {
            this.takeDamage(incomingAttack / blockSupresser);
            return false;
        }
        return true;
    }
}
