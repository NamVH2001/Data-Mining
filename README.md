## TCSPM
This repo hosts the code for paper **"Kaixia Hu, Wensheng Gan, Shan Huang, Hao Peng, Philippe Fournier-Viger, Targeted mining of contiguous sequential patterns[J]. Information Sciences, 2024, 653: 119791."**

## Requirements
Java programming language.

## Running the program
A simple way is to run TestTCSPM.java.

## Benchmarks
- The data format used is the same as in the file data.txt. That is, different positive integers represent different terms. In addition, "-1" is used as the itemset separator and "-2" is used as the sequence separator.

## Reproducing the paper experiments
- `data/`: E-shop and OnlineRetail_II datasets from [SPMF](https://www.philippe-fournier-viger.com/spmf/). The `*_sorted.txt` files have the items in each itemset sorted ascending, which the code assumes (see `Itemset.java`). Use `online_retail_II_all_products_sorted_keepdup.txt` for OnlineRetail_II_all: it is sorted with duplicate items kept.
- `experiments/run_sweep.ps1`: runs TCSPM and TCSPM+ over several minsup values and appends results to `experiments/results.csv`. It compiles into a temp folder and calls `RunPaperExperiment.java` (one run, one CSV line) for each minsup and algorithm.
  ```powershell
  .\experiments\run_sweep.ps1 -Dataset online_retail_II_best_products -Query "7593;4479,6173;9773" -Minsups 60,70,80,90,100,110
  ```
- Results: the candidate counts in Table 5 are reproduced exactly for OnlineRetail_II_best (minsup 60-110) and OnlineRetail_II_all (minsup 10-60). The E-shop row of Table 5 is not reproduced with query <(28,212)(280)>. The synthetic datasets (Syn40K/60K/80K) are not public.
