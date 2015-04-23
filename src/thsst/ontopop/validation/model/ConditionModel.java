package thsst.ontopop.validation.model;

import java.util.List;

import javax.swing.JOptionPane;

public class ConditionModel {
	private String conditionName;
	private List<String> synonyms;
	private List<String> symptoms;
	private List<String> neededFood;
	private List<String> avoidFood;
	private List<String> deficientNutrients;
	private List<String> excessNutrients;
	
	public String getConditionName() {
		return conditionName;
	}
	
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> otherNames) {
		this.synonyms = otherNames;
	}

	public List<String> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<String> symptoms) {
		this.symptoms = symptoms;
	}

	public List<String> getNeededFood() {
		return neededFood;
	}

	public void setNeededFood(List<String> neededFood) {
		this.neededFood = neededFood;
	}

	public List<String> getAvoidFood() {
		return avoidFood;
	}

	public void setAvoidFood(List<String> avoidFood) {
		this.avoidFood = avoidFood;
	}

	public List<String> getDeficientNutrients() {
		return deficientNutrients;
	}

	public void setDeficientNutrients(List<String> deficientNutrients) {
		this.deficientNutrients = deficientNutrients;
	}

	public List<String> getExcessNutrients() {
		return excessNutrients;
	}

	public void setExcessNutrients(List<String> excessNutrients) {
		this.excessNutrients = excessNutrients;
	}
	
	@Override
	public String toString(){
		return conditionName;
	}
}
