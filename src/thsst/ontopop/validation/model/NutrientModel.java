package thsst.ontopop.validation.model;

public class NutrientModel {
	private String srcFile;
	private String name;
	private int amount;
	private String unit;
	
	public NutrientModel(String name, int amount, String unit, String srcFile){
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.srcFile = srcFile;
	}
	
	@Override
	public String toString(){
		return name+": "+amount+""+unit;
	}
}
