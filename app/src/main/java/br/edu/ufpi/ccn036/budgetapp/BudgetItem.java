package br.edu.ufpi.ccn036.budgetapp;

import br.edu.ufpi.ccn036.budgetapp.algorithms.Knapsack.KnapsackItem;

public class BudgetItem implements KnapsackItem {
	private String title;
	private double weight, value;
	
	public BudgetItem(String title, double weight, double value) {
		this.title = title;
		this.weight = weight;
		this.value = value;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	public String getTitle() {
		return title;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public void setWeight(double value) {
		this.weight = value;
	}

	public void setTitle(String value) {
		this.title = value;
	}
}