package br.edu.ufpi.ccn036.budgetapp;

import java.util.ArrayList;

public class Knapsack {
	private double capacity, currValue = 0, currWeight = 0, best = 0;
	private ArrayList<KnapsackItem> items, solution, subset;
	private boolean saved = false;
	
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
			currValue += item.value();
			currWeight += item.weight();
			search(k + 1);
			subset.remove(subset.size() - 1);
			currValue -= item.value();
			currWeight -= item.weight();
		}
	}
	
	public ArrayList<KnapsackItem> solve() {
		return solve(Algorithm.COMPLETE_SEARCH);
	}
	
	public ArrayList<KnapsackItem> solve(Algorithm algo) {
		if(saved) return solution;
		
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
		public double value();
		public double weight();
	}
	
}
