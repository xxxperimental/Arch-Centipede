import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * The environment controls the playing field and adds Creature objects, spawn points, images, and
 * has the ability to clear the list of creatures.
 * @author George
 *
 */
/**
 * Code for the environment. 
 *
 * @author zamanmm.
 *         Created Nov 3, 2015.
 */
@SuppressWarnings("serial")
public class Environment extends JFrame implements Temporal
{
	protected static final long UPDATE_INTERVAL = 10;
	protected List<Creature> creatures = new ArrayList<>();
	protected List<Creature> creaturesToRemove = new ArrayList<>();
	protected List<Creature> creaturesToAdd = new ArrayList<>();
	protected boolean reloader = false;
	protected boolean defeated = false;
	protected boolean victorious = false;
	/**
	 * Constructs the Environment.
	 */
	public Environment()
	{
		
	}
	
	/**
	 * Adds a creature to the Environment.
	 * 
	 * @param creature
	 */
	public synchronized void addCreature(Creature creature) 
	{
		this.creaturesToAdd.add(creature);
	}
	
	/**
	 * Adds a creature to the remove list.
	 * 
	 * @param creature
	 */
	public synchronized void removeCreature(Creature creature)
	{
		this.creaturesToRemove.add(creature);
	}
	
	/**
	 * Returns the creatures that have to be drawn.
	 *
	 * @return
	 */
	public List<Creature> getDrawableParts()
	{
		return this.creatures;
	}

	/**
	 * Removes everything from the Environment.
	 */
	public void clear()
	{
		this.creaturesToAdd.clear();
		this.creaturesToRemove.clear();
		this.creatures.clear();
	}
	
	/**
	 * Defeat screen.
	 */
	public void defeat()
	{
		clear();
		this.defeated = true;
	}
	
	public void victory()
	{
		clear();
		this.victorious = true;
	}
	
	/**
	 * Removes the creatures, which were added to the remove list.
	 */
	@Override
	public void timePassed()
	{
		this.creatures.removeAll(this.creaturesToRemove);
		this.creatures.addAll(this.creaturesToAdd);
		this.creaturesToRemove.clear();
		this.creaturesToAdd.clear();
		
	}
}
