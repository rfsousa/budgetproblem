package br.edu.ufpi.ccn036.budgetapp.algorithms;

import java.util.ArrayList;

public class Knapsack {
	private double capacity, currValue = 0, currWeight = 0, best = 0;
	private ArrayList<KnapsackItem> items, solution, subset;
	private boolean saved = false, enableMemoization = true;
	
	public Knapsack(double budget) {
		this.capacity = budget;
		items = new ArrayList<>();
		solution = new ArrayList<>();
		subset = new ArrayList<>();
	}
	
	public boolean addBudgetItem(KnapsackItem item) {
		saved = false;
		return items.add(item);
	}
	
	public KnapsackItem remove(int index) {
		saved = false;
		return items.remove(index);
	}
	
	public KnapsackItem getBudgetItem(int index) {
		return items.get(index);
	}

	public ArrayList<KnapsackItem> getItems() {
		return items;
	}
	
	public void setCapacity(double budget) {
		this.capacity = budget;
		saved = false;
	}
	
	private void search(int k) {
		if(k == items.size()) {
			if(currWeight <= capacity) {
				if(best < currValue) {
					solution = new ArrayList<>(subset);
					best = currValue;
				}
			}
		} else {
			search(k + 1);
			var item = items.get(k);
			subset.add(item);
			currValue += item.getValue();
			currWeight += item.getWeight();
			search(k + 1);
			subset.remove(subset.size() - 1);
			currValue -= item.getValue();
			currWeight -= item.getWeight();
		}
	}
	
	public Knapsack toggleMemoization() {
		enableMemoization = !enableMemoization;
		return this;
	}
	
	public ArrayList<KnapsackItem> solve() {
		best = 0;
		return solve(Algorithm.COMPLETE_SEARCH);
	}
	
	public ArrayList<KnapsackItem> solve(Algorithm algo) {
		if(saved && enableMemoization) return solution;
		
		switch(algo) {
		case COMPLETE_SEARCH:
			search(0);
			break;
		default:
			return null;
		}
		
		saved = true;
		
		return solution;
	}
	
	public enum Algorithm {
		COMPLETE_SEARCH();
	}
	
	public static interface KnapsackItem {
		public double getValue();
		public double getWeight();
	}
	
}
