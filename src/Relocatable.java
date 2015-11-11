
import java.awt.geom.Point2D;

/**
 * Relocatable just enables the objects to move in different directions and gets the spawn point of the objects.
 * @author George
 *
 */
public interface Relocatable {
	
	public enum MOVE_DIRECTION
	{
		UP, DOWN, LEFT, RIGHT, STILL
	}
	
	void moveToNewPoint();

	Point2D.Double getSpawnPoint();

}