import java.awt.geom.Point2D.Double;
import java.util.HashMap;

public abstract class Monster extends Creature
{
	public Monster(Environment world, HashMap<Double, Creature> occupationMap)
	{
		super(world, occupationMap);
	}
}
