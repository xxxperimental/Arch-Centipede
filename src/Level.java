import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * Code for the Level class.
 * 
 * Reads the level .txt files and Initializes the level,
 * adding objects to the Environment.
 *
 * @author zamanmm.
 *         Created Nov 4, 2015.
 */
public class Level implements KeyListener
{
	private int xCoordinate;
	private int yCoordinate;
	protected static final int GAP = 44;
	private int levelNumber;
	private Environment world;
	private GameComponent gameComponent;
	private Hero player;
	protected HashMap<Point2D.Double, Creature> occupationMap;
	int numberOfLines = 20;
	
	/**
	 * Constructs the level.
	 *
	 * @param levelNumber
	 * @param world
	 * @param gameComponent
	 */
	public Level(int levelNumber, Environment world, GameComponent gameComponent) 
	{
		this.world = world;
		this.gameComponent = gameComponent;
		this.occupationMap = new HashMap<>();
		this.initializeMap();
		this.levelNumber = levelNumber;
		
		this.xCoordinate = 0;
		this.yCoordinate = 0;
		
		
		
		/**
		 * Depending on the level loads the file
		 * and calls the levelCreator. 
		 */
		try
		{

			FileReader fileReader = new FileReader("./levels/" + Integer.toString(this.levelNumber) + ".txt");
			String[] textData = OpenFile(fileReader);
			levelCreator(textData, this.world);
				
		} 
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
		this.world.add(this.gameComponent);
		this.world.addKeyListener(this);
		this.world.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.world.setVisible(true);
	}
	
	/**
	 * Loads the file.
	 *
	 * @param fileReader
	 * @return
	 * @throws IOException
	 */
	public String[] OpenFile(FileReader fileReader) throws IOException
	{
		BufferedReader textReader = new BufferedReader(fileReader);
		
		String[] textData = new String[this.numberOfLines];
		
		for (int i = 0; i < this.numberOfLines; i++)
		{
			textData[i] = textReader.readLine();
		}
		textReader.close();
		
		return textData;
	}
	
	/**
	 * Reads the file by iterating through each character in each
	 * line. 
	 * Creates creatures that correspond to the character
	 * that is read.
	 *
	 * @param textData
	 * @param world
	 */
	@SuppressWarnings("hiding")
	public void levelCreator(String[] textData, Environment world)
	{
		for (int i = 0; i < textData.length; i++)
		{
			for (int j = 0; j < textData.length; j++)
			{
				switch(textData[i].charAt(j))
				{
				case '.':
					this.xCoordinate += GAP;
					break;
				case 'm':
					Mushroom shroom = new Mushroom(this.world, this.occupationMap, this.xCoordinate, this.yCoordinate);
					this.occupationMap.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), shroom);
					this.world.addCreature(shroom);
					this.xCoordinate += GAP;
					break;
				case 'c':
					Centipede centipede = new Centipede(world, this.occupationMap, this.xCoordinate, this.yCoordinate);
					this.world.addCreature(centipede);
					for (CentipedeComponent centipedePart: centipede.bodyParts)
					{
						this.occupationMap.put(centipedePart.spawnPoint, centipedePart);
						this.world.addCreature(centipedePart);
					}
					this.xCoordinate += 5*GAP;
					break;
				case 'f':
					Flea flea = new Flea(world, this.occupationMap,this.xCoordinate, this.yCoordinate);
					this.occupationMap.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), flea);
					this.world.addCreature(flea);
					this.xCoordinate += GAP;
					break;
				case 'h':
					Hero player = new Hero(this.world, this.occupationMap, this.xCoordinate, this.yCoordinate);
					this.player = player;
					this.occupationMap.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), this.player);
					this.world.addCreature(player);
					this.world.addKeyListener(player);
					this.xCoordinate += GAP;
					break;
				case 's':
					Scorpion scorp = new Scorpion(world, this.occupationMap, this.xCoordinate, this.yCoordinate);
					this.occupationMap.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), scorp);
					this.world.addCreature(scorp);
					this.xCoordinate += GAP;
					break;
					
				case 'S':
					Spider spider = new Spider(world, this.occupationMap, this.xCoordinate, this.yCoordinate);
					this.occupationMap.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), spider);
					this.world.addCreature(spider);
					this.xCoordinate += GAP;
					break;
				default:
					break;
				}
			}
			this.xCoordinate = 0;
			this.yCoordinate += GAP;
		}
	}
	
	/**
	 * Method for the KeyListener.
	 * 
	 * Makes it possible to switch to other levels
	 * by pressing "U" or "D' keys.
	 */
	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent e)
	{
		this.world.reloader = true;
		switch(KeyEvent.getKeyText(e.getKeyCode()))
		{
		case("U"):
			this.world.clear();
			this.world.victorious = false;
			this.world.defeated = false;
			this.world.removeKeyListener(this.player);
			this.world.defeated = false;
			if (this.levelNumber == 6)
			{
				this.occupationMap = new HashMap<>();
				Level level = new Level(1, this.world, this.gameComponent);
			}
			else
			{
				this.occupationMap = new HashMap<>();
				Level level = new Level(this.levelNumber + 1, this.world, this.gameComponent);
			}
			break;
		case("D"):
			this.world.clear();
			this.world.victorious = false;
			this.world.defeated = false;
			this.world.removeKeyListener(this.player);
			this.world.defeated = false;
			if (this.levelNumber == 1)
			{
				this.occupationMap = new HashMap<>();
				Level level = new Level(6, this.world, this.gameComponent);
			}
			else
			{
				this.occupationMap = new HashMap<>();
				Level level = new Level(this.levelNumber - 1, this.world, this.gameComponent);
			}
			break;
		default:
			break;		
		}
		this.world.reloader = false;
	}
	
	/**
	 * Pre-loads the occupation map with null values.
	 */
	public void initializeMap()
	{
		int xPlacement = 0;
		int yPlacement = 0;
		for (int i = 0; i < this.numberOfLines; i++)
		{
			for (int j = 0; j < this.numberOfLines; j++)
			{
				this.occupationMap.put(new Point2D.Double(xPlacement, yPlacement), null);
				xPlacement += GAP;
			}
			xPlacement = 0;
			yPlacement += GAP;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// Nothing here yet.
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Nothing here yet.
		
	}
	
	
	
}
