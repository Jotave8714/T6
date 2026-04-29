# T6 - Identificacao de Isomorfismo em Arvores

Implementacao em **Java** do Trabalho Pratico 6 da disciplina **Resolucao de
Problemas com Grafos** (Prof. Me Ricardo Carubbi). O programa decide se duas
arvores nao direcionadas, lidas no formato `algs4`, sao isomorfas usando a
tecnica de **codificacao canonica** a partir do(s) **centro(s)** da arvore.

## Video explicativo

Link do video explicativo: **PREENCHER COM A URL DO VIDEO**

> O video deve ter 5 minutos (+/- 30s) e abordar: definicao de isomorfismo,
> por que graus nao bastam, ideia de centro, codificacao canonica, leitura das
> entradas, execucao do programa e por que ordenar os codigos dos filhos e
> obrigatorio.

## Estrutura

```text
T6/
├── README.md
├── T6.md
├── CLAUDE.md
├── dados/
│   ├── invalid-ciclo3.txt
│   ├── iso-path4-a.txt
│   ├── iso-path4-b.txt
│   ├── nao-iso-estrela5.txt
│   ├── nao-iso-path5.txt
│   ├── unico-centro-a.txt
│   └── unico-centro-b.txt
├── imgs/
│   └── UNIFOR_logo1b.png
├── refs/
│   └── youtube_vides.md
└── src/
    ├── Bag.java
    ├── Graph.java
    ├── In.java
    ├── Main.java
    ├── Stack.java
    ├── StdIn.java
    ├── StdOut.java
    └── TreeIsomorphism.java
```

## Compilacao

No diretorio `src`, execute:

```bash
javac Main.java TreeIsomorphism.java Graph.java Bag.java Stack.java In.java StdIn.java StdOut.java
```

## Execucao

O programa recebe **dois caminhos** de arquivos por linha de comando:

```bash
java Main ../dados/iso-path4-a.txt ../dados/iso-path4-b.txt
java Main ../dados/unico-centro-a.txt ../dados/unico-centro-b.txt
java Main ../dados/nao-iso-path5.txt ../dados/nao-iso-estrela5.txt
java Main ../dados/invalid-ciclo3.txt ../dados/iso-path4-a.txt
```

## Formato dos arquivos de entrada (padrao algs4)

```text
V
E
v1 w1
v2 w2
...
```

- `V`: numero de vertices (indexados de `0` a `V-1`);
- `E`: numero de arestas;
- cada linha `v w` e uma aresta nao direcionada.

## Algoritmo

1. **Validacao**: a entrada e arvore se for conexa e tiver exatamente `V-1`
   arestas (sem lacos nem arestas paralelas). A conectividade e verificada com
   BFS.
2. **Centros**: remocao iterativa de folhas. As folhas remanescentes apos o
   ultimo passo sao o(s) centro(s) (1 ou 2).
3. **Codificacao canonica**: para um vertice `v` com pai `p`,
   `encode(v, p) = "(" + sort([encode(filho, v) for filho]) + ")"`. A
   ordenacao lexicografica dos codigos dos filhos e obrigatoria para tornar a
   representacao independente da ordem de leitura.
4. **Dois centros**: a codificacao final e `min(encode(c1, -1), encode(c2, -1))`
   lexicograficamente.
5. **Veredito**: as arvores sao isomorfas se e somente se as codificacoes
   canonicas sao iguais.

## Saida

Para cada arvore o programa imprime a lista de adjacencia, o resultado da
validacao, o(s) centro(s) e a codificacao canonica. Ao final, exibe o veredito
de isomorfismo. Se alguma entrada for invalida, a comparacao e encerrada e o
motivo da invalidade e impresso.
