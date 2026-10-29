"""Sort the items inside every itemset of an SPMF sequence file (ascending, duplicates removed).

The TCSPM code assumes items in an itemset are in ascending order (see Itemset.java),
but some SPMF files (e_shop, online_retail_II_all) store them in attribute order.

Usage: python sort_itemsets.py <input.txt> <output.txt>
"""
import sys


def main(src, dst):
    with open(src, encoding="utf-8") as fin, open(dst, "w", encoding="utf-8", newline="\n") as fout:
        for line in fin:
            line = line.strip()
            if not line or line[0] in "#%@":
                continue
            out, itemset = [], []
            for tok in line.split():
                v = int(tok)
                if v == -1:
                    out.extend(sorted(set(itemset)))
                    out.append(-1)
                    itemset = []
                elif v == -2:
                    break
                else:
                    itemset.append(v)
            out.append(-2)
            fout.write(" ".join(map(str, out)) + "\n")


if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2])
