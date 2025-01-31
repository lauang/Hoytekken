package hoytekken.app.model.components.eventBus.records;

import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.interfaces.IEvent;

/**
 * Event for when the game state changes
 * 
 * @param oldState the old game state
 * @param newState the new game state
 */
public record GameStateEvent(GameState oldState, GameState newState) implements IEvent {
}
