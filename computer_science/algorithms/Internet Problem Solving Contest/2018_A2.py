#!/usr/bin/env python

# Armed bandit
# https://ipsc.ksp.sk/2018/real/problems/a.html

def lexicographically_smallest(arr):
	return map(greedy_fn, arr[:-1]) + [1]

def greedy_fn(num):
	if(num < 10):
		return 1
	else:
		return greedy_fn(num/10) * 10

def main():
	with open('a2.out', 'w') as outf:
		with open('a2.in', 'r') as inf:
			cases = int(inf.readline())
			for case in range(cases):
				inf.readline()
				size = int(inf.readline())
				arr = map(int, inf.readline().split())
				outf.write(' '.join(str(e) for e in lexicographically_smallest(arr)) + '\n')

if __name__ == "__main__":
    main()
