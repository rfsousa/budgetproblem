package br.edu.ufpi.ccn036.budgetapp;

import java.util.Random;

import br.edu.ufpi.ccn036.budgetapp.algorithms.Knapsack;
import br.edu.ufpi.ccn036.budgetapp.algorithms.Knapsack.Algorithm;
import br.edu.ufpi.ccn036.budgetapp.algorithms.Knapsack.IKnapsackItem;

public class Benchmark {
	public static void main(String[] args) {
		Random random = new Random();
		
		Knapsack knapsack = new Knapsack(random.nextDouble());
		
		int n = random.nextInt();
		
		for(int i = 0; i < n; i++) {
			knapsack.addBudgetItem(new A(random.nextDouble(), random.nextDouble()));
		}
		
		long a = System.nanoTime();
		knapsack.solve(Algorithm.COMPLETE_SEARCH);
		a = System.nanoTime() - a;
		System.out.println("Força bruta: " + a + "ns");
		a = System.nanoTime();
		knapsack.solve(Algorithm.HEURISTIC);
		a = System.nanoTime() - a;
		System.out.println("Heurística: " + a + "ns");
		
	}
	
	private static class A implements IKnapsackItem {
		double a, b;
		
		A(double a, double b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public double getValue() {
			return a;
		}

		@Override
		public double getWeight() {
			return b;
		}
		
	}
}
