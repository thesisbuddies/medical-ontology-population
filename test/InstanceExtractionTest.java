import thsst.ontopop.ontology_population.InstanceExtractor;

public class InstanceExtractionTest {
	public static void main(String[] args) throws Exception{
		//InstanceExtraction controller = new InstanceExtraction();
        InstanceExtractor extractor = new InstanceExtractor();
        extractor.extract("test/SampleXML - Copy.xml");
        extractor.commit("ontology.ttl");
	}
}
