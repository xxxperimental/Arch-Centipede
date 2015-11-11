import java.util.HashMap;

/**
 * Creates the Environment and GameComponent; Loads the level.
 *
 * @author zamanmm.
 * 
 */
public class ArcadeGame
{
	HashMap<Integer, Level> levelMap;
	Environment world = new Environment();;
	GameComponent gameComponent = new GameComponent(this.world);
	private final int height = 940;
	private final int width = 900;
	private int initialLevel = 1;
	
	/**
	 * Constructs the Arcade Game.
	 */
	@SuppressWarnings("unused")
	public ArcadeGame()
	{
		this.world.setSize(this.width, this.height);
		
		/**
		 * Runs an instance of the initial level.
		 */
		Level level = new Level(this.initialLevel, this.world, this.gameComponent);	
	}
	
}
