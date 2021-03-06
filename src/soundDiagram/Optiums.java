package soundDiagram;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;



public class Optiums {
	public static final AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
	public static final int SAMPLE_RATE = 44100;
	public static final byte SAMPLE_SIZE_IN_BITS = 8;  
	public static final byte CHENNELS = 1;
	public static final byte FRAME_SIZE = 1;
	public static final int  FRAME_RATE = 44100;
	public static final boolean BIG_ENDIAN = false;
	public static String FILE_RECORD_NAME = "SoundFile";
	public static final int THREAD_SLEEPING = 5;
	public static final AudioFileFormat.Type FILE_TYPE = AudioFileFormat.Type.AU;
	public static final int BUFF_SIZE = 512;
	public static final int DIAGRAM_BUFF = 20;
	public static final boolean DISABLES_BUTTONS = true;  /* Disabled one button when
	 another button  is active.	Do not recommend set false can be errors but sound funny 
	 when activated record and player together*/
}