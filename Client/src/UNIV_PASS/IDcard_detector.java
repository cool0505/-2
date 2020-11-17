package UNIV_PASS;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import net.sourceforge.tess4j.Tesseract;
public class IDcard_detector {
 
	static JFrame frame;
	static JFrame frame2;
	static JLabel lbl;
	static JLabel lbl2;
	static JPanel pn1;
	static JPanel pn2;
	static JButton bt1;
	static JButton bt2;
	static ImageIcon icon;
	static ImageIcon icon2;
	static Tesseract instance = Tesseract.getInstance();
	static float sum;
	public static String process (String fileName) {
		// TODO Auto-generated method stub
		File inputFile =new File(fileName);
		String result ="";
		try {
			
			result =instance.doOCR(inputFile);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


		CascadeClassifier cascadeFaceClassifier = new CascadeClassifier(
				"C:/Users/admin/Desktop/final/test.xml");
		/*CascadeClassifier cascadeEyeClassifier = new CascadeClassifier(
				"C:/Users/admin/Documents/opencv-object-detection/data/haarcascade files/haarcascade_eye.xml");
		
		CascadeClassifier cascadeNoseClassifier = new CascadeClassifier(
				"C:/Users/admin/Documents/opencv-object-detection/data/haarcascade files/haarcascade_mcs_nose.xml");
*/
		VideoCapture videoDevice = new VideoCapture(0);
		videoDevice.open(0);
		if (videoDevice.isOpened()) {
		
			while (true) {		
				Mat frameCapture = new Mat();
				videoDevice.read(frameCapture);
				Mat subimage = null;
			
				MatOfRect faces = new MatOfRect();
				//Rect rect=null;
				cascadeFaceClassifier.detectMultiScale(frameCapture,faces);								
				/*try {
				rect=faces.toArray()[0];
				}
				catch(ArrayIndexOutOfBoundsException e){
					continue;
					
				}*/
				for (Rect rect : faces.toArray()) {
					if((sum=rect.width+rect.height)>600) {
						System.out.println(rect.width+rect.height);
						Imgproc.putText(frameCapture, "S ID C", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));								
						Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(0, 100, 0),3);
						LIVEPushImage(ConvertMat2Image(frameCapture));
						//System.out.println(process("Img5.jpg"));
				        Rect rectCrop=new Rect(new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height));
				        subimage = new Mat(frameCapture ,rectCrop);
					}
				}	
				
				
				
				/*MatOfRect eyes = new MatOfRect();
				cascadeEyeClassifier.detectMultiScale(frameCapture, eyes);
				for (Rect rect : eyes.toArray()) {

					Imgproc.putText(frameCapture, "Eye", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));				

					Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(200, 200, 100),2);
				}*/
				
			
				/*MatOfRect nose = new MatOfRect();
				cascadeNoseClassifier.detectMultiScale(frameCapture, nose);
				for (Rect rect : nose.toArray()) {
					
					Imgproc.putText(frameCapture, "Nose", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));				
					
					Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(50, 255, 50),2);
				}*/
				

			   /*MatOfRect mouth = new MatOfRect();
				cascadeMouthClassifier.detectMultiScale(frameCapture, mouth);
				for (Rect rect : mouth.toArray()) {
					
					Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(129, 90, 50),2);
				}
				
				*/
				
				LIVEPushImage(ConvertMat2Image(frameCapture));
				if(sum>600) {

					SubPushImage(ConvertMat2Image(subimage));
					ResizeImage(subimage);
				}
				//System.out.println(String.format("%s y�z(FACES)", faces.toArray().length));
			}
		} else {
			System.out.println("Video aygytyna ba?lanylamady.");
			return;
		}
	}

	private static BufferedImage ConvertMat2Image(Mat kameraVerisi) {

		
		MatOfByte byteMatVerisi = new MatOfByte();
		try {
			Imgcodecs.imencode(".jpg", kameraVerisi, byteMatVerisi);
		}
			catch (NullPointerException e) {
				return null;
			}
		byte[] byteArray = byteMatVerisi.toArray();
		BufferedImage goruntu = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			goruntu = ImageIO.read(in);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			/*File file3 = new File("C:\\Users\\admin\\Desktop\\asdasd\\final\\pororo2.jpg");
	        try {
				ImageIO.write(goruntu, "jpg", file3);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        String result ="";
			try {
				
				result =instance.doOCR(goruntu);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);*/
		
		return goruntu;
	}
	
	private static void ResizeImage(Mat asd) {
		Mat last= new Mat();
		String result ="";
		try {
			Imgproc.resize(asd,last,new Size(1200,1200));
			Image img4 = ConvertMat2Image(last);
			result =instance.doOCR((BufferedImage)img4);
			
			String resultF=result.substring(result.indexOf("20"), result.indexOf("20")+10);
			System.out.println(resultF);
		}
		catch(NullPointerException e) {			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		

	}
  	

	public static void PencereHazirla() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setSize(2000, 1200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void LIVEPushImage(Image img2) {

		icon=null;
		if (frame == null)
			PencereHazirla();
		
		if (lbl != null)
			try {
				frame.remove(lbl);
}
		catch(ArrayIndexOutOfBoundsException e){
						
		}
		lbl = new JLabel(new ImageIcon(img2));

		lbl.setLayout(null);
		lbl.setBounds(100, 170, 800, 800);



		frame.getContentPane().add(lbl);

		
		lbl.revalidate(); 

		lbl.repaint(); 

	}
	public static void SubPushImage(Image img3) {

		icon=null;
		if (lbl2 != null)
			try {
				frame.remove(lbl2);}
		catch(ArrayIndexOutOfBoundsException e){					
		}
		try {
		lbl2 = new JLabel(new ImageIcon(img3));}
		catch(NullPointerException e){}
		lbl2.setLayout(null);
		lbl2.setBounds(1000, 170, 300, 300);

		frame.getContentPane().add(lbl2);
        
		lbl2.revalidate();

		lbl2.repaint();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
