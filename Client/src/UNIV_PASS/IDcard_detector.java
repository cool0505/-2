package UNIV_PASS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
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
class frame2 extends JFrame{
	private Image background=new ImageIcon("C:\\Users\\admin\\Desktop\\UNIVPASS.jpg").getImage();
	public void paint(Graphics g) {//그리는 함수
		g.drawImage(background, 0, 0, null);//background를 그려줌
	}
}
public class IDcard_detector {
	static String[] strcomp = new String[2]; 
	static int count=0;
	static Socket socket;
	static frame2 frame;
	static JLabel lbl;
	static JLabel lbl2;
	static JLabel lbl3;
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
		try {
			socket = new Socket("192.168.1.6", 8282);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
				Mat OCRimage = null;
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
						Imgproc.putText(frameCapture, "STUDENT ID CARD", new Point(rect.x,rect.y+55), 1, 2, new Scalar(0,100,100));								
						Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y+50), new Point(rect.x + rect.width, rect.y + rect.height-50),
						new Scalar(0, 100, 0),3);
						LIVEPushImage(ConvertMat2Image(frameCapture));
						//System.out.println(process("Img5.jpg"));
				        Rect rectCrop=new Rect(new Point(rect.x, rect.y+50), new Point(rect.x + rect.width, rect.y + rect.height-50));
				        Rect rectCrop2=new Rect(new Point(0, 50), new Point(rect.width, rect.height-180));
				        subimage = new Mat(frameCapture ,rectCrop);				        
				        OCRimage = new Mat(subimage ,rectCrop2);
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
					//SubPushImage(ResizeImage(OCRimage));
					ResizeImage(OCRimage);
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
		// Rect rectCrop=new Rect(new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height));
		String result ="";
		try {
			Imgproc.resize(asd,last,new Size(1600,800));
			Image img4 = ConvertMat2Image(last);
			result =instance.doOCR((BufferedImage)img4);
			String resultF=result.substring(result.indexOf("20"), result.indexOf("20")+10);
			System.out.println(resultF);
			if(count==2) {
				if(strcomp[0].equals(strcomp[1])) {
					System.out.println("전송");
					String sendstring = "1/" + strcomp[1];
					InputStreamReader ISR = new InputStreamReader(socket.getInputStream());
					PrintWriter print = new PrintWriter(socket.getOutputStream());          
		            print.println(sendstring);
		            print.flush();
		            BufferedReader buffer = new BufferedReader(ISR);
		               
		            String LoginResult = buffer.readLine();
		            System.out.println(LoginResult);
		            lbl3.setText(LoginResult);
		            Thread.sleep(3000);
		            lbl3.setText("학생증을 인식해주세요");

		            count=0;
		            strcomp[0]=null;
		            strcomp[1]=null;
				}
				else {
					String buf=strcomp[0];
					strcomp[0]=strcomp[1];
					strcomp[1]=resultF;
					System.out.println("빠꿈");
				}
				System.out.println("빠꿈2");
				
				
			}
			else {
				System.out.println(count);
				strcomp[count]=resultF;	
				count++;
			}
		}
		catch(NullPointerException e) {			
		}
		catch(Exception e) {

		}

		

	}
  	

	public static void PencereHazirla() {
		frame = new frame2();
		frame.getContentPane().setLayout(null);
		frame.setSize(1200, 610);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 		   // 이렇게 컬러값을 생성 후     // 센 백그라운드에 넣어준다		  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		lbl3 = new JLabel("학생증을 인식해주세요");
        lbl3.setLayout(null);
  		lbl3.setBounds(700, 335, 450, 270);
  		frame.getContentPane().add(lbl3);
  		lbl3.setVisible(true);
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

		lbl.setOpaque(true); 
		lbl.setBackground(Color.BLACK);
		lbl.setLayout(null);
		lbl.setBounds(28, 80, 645, 385);
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
		lbl2.setBounds(700, 41, 450, 270);

		frame.getContentPane().add(lbl2);
        
		lbl2.revalidate();

		lbl2.repaint();


	}
	
}
