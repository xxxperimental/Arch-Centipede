import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
/**
 * GameComponent simply is an extension of JComponent.  However, we overwrote the paint component
 * to add creatures and draw/paint things onto the frame.
 * @author George
 *
 */
/**
 * Code for the component for rendering the Environment
 * and its' creatures on the GUI.
 *
 * @author zamanmm.
 *         Created Nov 3, 2015.
 */
public class GameComponent extends JComponent implements Temporal
{
	protected static final long serialVersionUID = -3783350103861335960L;
	protected static final int FPS = 30;
	protected static final long REPAINT_INTERVAL = 1000 / FPS;
	protected Environment world;
	protected int backgroundFrame = 0;

	/**
	 * Constructs a GameComponent
	 * 
	 * @param world
	 */
	public GameComponent(Environment world)
	{
		this.world = world;
		setPreferredSize(world.getSize());
		setMaximumSize(world.getSize());
		
		
		/**
		 * Creates a separate "thread of execution" to trigger periodic
		 * repainting of this component.
		 */
		Runnable repainter = new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						Thread.sleep(REPAINT_INTERVAL);
						if (world.reloader == false)
						{
							try 
							{
								timePassed();
								world.timePassed();
								repaint();
							}
							catch (ConcurrentModificationException e)
							{
								// Ignore
							}
						}
					}	
				} 
					catch (InterruptedException exception)
				{
					// Stop when interrupted
				}
			}
		};
		new Thread(repainter).start();
	}
	/**
	 * As time passes triggers the timePassed methods of all
	 * creatures in the Environment.
	 */
	@Override
	public void timePassed()
	{
		for (Creature creature: this.world.creatures)
		{
			creature.timePassed();
		}
	}

	/**
	 * The paintComponent inherited from the JComponent.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (this.world.defeated == true)
		{
			try
			{
				BufferedImage image = ImageIO.read(new File("./img/defeat.png"));
				g.drawImage(image, 0, 0, this);
					
			} 
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		else if (this.world.victorious == true)
		{
			try
			{
				BufferedImage image = ImageIO.read(new File("./img/victory.png"));
				g.drawImage(image, 0, 0, this);
					
			} 
			catch (IOException exception)
			{
				//Ignore.
			}
		}
		else
		{
			try
			{
				BufferedImage image = ImageIO.read(new File("./img/background/bg_" + Integer.toString(this.backgroundFrame) + ".gif"));
				g.drawImage(image, 0, 0, this);
					
			} 
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
			
			if (this.backgroundFrame == 39)
				this.backgroundFrame = 0;
			else
				this.backgroundFrame++;
			
			try
			{
				List<Creature> drawableParts = this.world.getDrawableParts();
				for (Creature creature : drawableParts)
				{
					drawDrawable(g2, creature);
				}
			}
			catch (ConcurrentModificationException e)
			{
				 // Ignore
			}
		}
	}

	/**
	 * This helper method draws the given drawable object on the given graphics
	 * area.
	 * 
	 * @param g2
	 *            the graphics area on which to draw
	 * @param d
	 *            the drawable object
	 */
	private static void drawDrawable(Graphics2D g2, Drawable d)
	{
		g2.drawImage(d.getImage(), null, (int) d.getSpawnPoint().x,
				(int) d.getSpawnPoint().y);
	}

}