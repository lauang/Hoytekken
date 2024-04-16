package hoytekken.app.model;

import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.EventBus;

import java.util.HashMap;

public interface IViewAndControl {

    /**
     * Gets the event bus, stored in model
     * @return the event bus
     */
    public EventBus getEventBus();

    /**
     * Method to get the maps for the game
     * 
     * @return the maps for the game
     */
    public HashMap<String, String> getGameMaps();

    /**
     * Gets the gamestate that the game is currently in
     * 
     * @return a GameState-object that represents the current gamestate
     */
    GameState getGameState();

    /**
     * Updates the current gamestate
     * 
     * @param gameState is the gamestate that the game gets set to
     */
    void setGameState(GameState gameState);

    /**
     * Method to set the map for the game
     * 
     * @param mapName the name of the map
     */
    void setGameMap(String mapName);
}
