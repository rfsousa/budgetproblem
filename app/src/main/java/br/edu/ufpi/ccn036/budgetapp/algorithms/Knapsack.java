package br.edu.ufpi.ccn036.budgetapp.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	private void heuristic() {
		double average = 0;
		for(KnapsackItem item: items) {
			average += item.getWeight();
		}
		average /= items.size();
		
		double variance = 0;
		
		for(KnapsackItem item: items) {
			variance += (item.getWeight() - average) * (item.getWeight() - average);
		}
		
		double stdDev = Math.sqrt(variance);
		double factor = 2, probability = 0;
		
		for(KnapsackItem item: items) {
			if(Math.abs(item.getWeight() - average) <= factor * stdDev)
				probability++;
		}
		
		probability /= items.size();
		
		if(probability >= 0.65) {
			Collections.sort(items, new Comparator<KnapsackItem>() {
				@Override
				public int compare(KnapsackItem o1, KnapsackItem o2) {
					return (int) Math.round((o2.getValue() / o2.getWeight()) - (o1.getValue() / o1.getWeight())); // sorts in reverse
				}
			});
		} else {
			Collections.sort(items, new Comparator<KnapsackItem>() {
				@Override
				public int compare(KnapsackItem o1, KnapsackItem o2) {
					return (int) Math.round(o2.getValue() - o1.getValue()); // sorts in reverse
				}
			});
		}
		
		double remaining = capacity;
		
		solution.clear();
		for(KnapsackItem item: items) {
			if(item.getWeight() <= remaining) {
				remaining -= item.getWeight();
				solution.add(item);
			}
		}
	}
	
	public Knapsack toggleMemoization() {
		enableMemoization = !enableMemoization;
		return this;
	}
	
	public ArrayList<KnapsackItem> solve() {
		best = 0;
		subset = new ArrayList<>();
		return solve(Algorithm.COMPLETE_SEARCH);
	}
	
	public ArrayList<KnapsackItem> solve(Algorithm algo) {
		if(saved && enableMemoization) return solution;
		
		switch(algo) {
		case COMPLETE_SEARCH: search(0); break;
		case HEURISTIC: heuristic(); break;
		default: return null;
		}
		
		saved = true;
		
		return solution;
	}
	
	public enum Algorithm {
		COMPLETE_SEARCH(),
		HEURISTIC();
	}
	
	public static interface KnapsackItem {
		public double getValue();
		public double getWeight();
	}
	
}
