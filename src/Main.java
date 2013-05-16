import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

public class Main {
	public static int IMG_WIDTH = 24;
	public static int IMG_HEIGHT = 20;
	public static int test = 1;

	public static void main(String[] args) throws IOException {

		// taking path img2, make a samples dat file with the starting position
		// 0,0 and the image size width height because images are already
		// cropped., or the second option is to just create a file path without x y pos (for background data file)
		if (test == 1) {
			File f2 = new File("img/");
			File results = new File("bg.txt");
			try {
				results.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuilder s = new StringBuilder();
			String[] images = f2.list();
			System.out.println();
			for (String e : images) {
				
				
//				 BufferedImage bimg = ImageIO.read(new File("img2X/" + e));
//				 int width = bimg.getWidth();
//				 int height = bimg.getHeight();
//				 1 denotes number of images.
//				 0 0 denotes starting (top left)
//				 width and height grabbed using ImageIO import.
//				 s.append("img2X/" + e + " 1 0 0 " + width + " " + height +
//				 "\n");
				
				
				s.append("img/" + e + "\n");
			}
			FileWriter fw = new FileWriter(results);
			fw.write(s.toString().trim());
			fw.flush();
			fw.close();
		}

		// test "2" where we recreate images at 24 pixels each
		if (test == 2) {
			File f2 = new File("img2/");
			File f3 = new File("img3/");
			f3.mkdir();
			String[] images = f2.list();
			for (String e : images) {

				BufferedImage originalImage = ImageIO.read(new File("img2" + File.separator + e));
				int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
				BufferedImage resizeImagePng = resizeImageWithHint(originalImage, type);
				new File("img3" + File.separator + e).createNewFile();
				ImageIO.write(resizeImagePng, "jpg", new File("img3" + File.separator + e)); 
			}
		}

		// take img2 path and make an extra image of everything.
		if (test == -1) {
			File f2 = new File("img2/");
			String[] images = f2.list();
			for (int i = 0; i < 1; i++)

				for (String e : images) {
					File fw = new File("img2/" + e.substring(0, e.length() - 4) + i + e.substring(e.length() - 4, e.length()));
					System.out.println(fw.getAbsolutePath());
					FileChannel src = new FileInputStream(new File("img2/" + e)).getChannel();
					FileChannel dest = new FileOutputStream(new File("img2/" + e.substring(0, e.length() - 4) + i
							+ e.substring(e.length() - 4, e.length()))).getChannel();
					dest.transferFrom(src, 0, src.size());
				}
		}
	}

	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}
