[The problem list](https://www.topcoder.com/tc?module=MatchList) without spoil it. 

枚举 + 贪心 https://community.topcoder.com/stat?c=problem_statement&pm=15305
Simple math https://community.topcoder.com/stat?c=problem_statement&pm=15292
Bridge https://community.topcoder.com/stat?c=problem_statement&pm=15458&rd=17514　

# DIV-1 250 
## SRM 579 [EllysThreePrimes](https://community.topcoder.com/stat?c=problem_statement&pm=15458&rd=17514)

* Observation 1: If the digit is 0, then number of the digit must be 1. (So we can handle 0 specially and remove it)
* Observation 2: After removing 0, the length of strings is no more than 18. 
* Observation 3: Each number of digits will be at least 1.

Then the problems turns to distribute 18 to 9 pieces, each pieces should be at least 1. Which is C(17, 8) = 24310, we can just enumerate this via permutation and then brute force on it.

## SRM 578 [SelfDescFind](https://community.topcoder.com/stat?c=problem_statement&pm=15436&rd=17531)
Since the prime numbers is sparse(about 8k in [10^4, 10^5]), we can brute force with two level for-loops and then verify the remaining number is also prime.

