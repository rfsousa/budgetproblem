# BudgetApp

## Introdução

Esta aplicação foi criada para atender a especificação do trabalho prático da disciplina de Projeto e Análise de Algoritmos (DC/CCN036) do Curso de Bacharelado em Ciência da Computação da Universidade Federal do Piauí, período letivo 2022.2.

Trata-se de escolher um problema NP-completo e modelar uma aplicação real desta forma. O problema escolhido foi o problema da mochila binária que apesar de ser possível melhorar a complexidade por meio de programação dinâmica, o problema continua sendo resolvido em tempo pseudopolinomial. A aplicação prática escolhida foi o gerenciamento de orçamento, onde a capacidade da mochila é o orçamento, e as despesas são descritas por um título, um custo e um benefício que será definido pelo usuário.

## Implementação

O algoritmo de força bruta ( _complete search_ ) está descrito no artigo. A interface gráfica cria uma instância da mochila, que é representada por uma classe  _Knapsack_ e a gerencia por meio de interações com os botões e utiliza os dados para preencher as tabelas que demonstram os itens que podem ser escolhidos e a solução ótima. A classe  _Knapsack_ , como foi implementada, permite que seja reutilizada por outros programas, por ser uma abstração.

## To-do list

- [ ] Implementar a função de remover uma despesa da lista.
- [ ] Permitir a visualização do orçamento atual.
- [ ] Implementar a conversão entre moedas, permitindo despesas em diferentes moedas.
- [ ] Permitir que o usuário importe ou exporte um arquivo contendo informações sobre despesas.