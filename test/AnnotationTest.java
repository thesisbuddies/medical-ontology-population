import gate.util.GateException;

import java.io.IOException;

import thsst.annotation.Annotation;


public class AnnotationTest {
	
	public static void main(String[] args){
		
		try {
			Annotation a = new Annotation();
			a.annotateXML("file:C:/Users/Paolo/Desktop/updated articles/infonet/NE Output/Beriberi.xml", "hello");
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
