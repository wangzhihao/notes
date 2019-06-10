[The problem list](https://www.topcoder.com/tc?module=MatchList) without spoil it. 

枚举 + 贪心 https://community.topcoder.com/stat?c=problem_statement&pm=15305
Simple math https://community.topcoder.com/stat?c=problem_statement&pm=15292
Bridge https://community.topcoder.com/stat?c=problem_statement&pm=15458&rd=17514　

# DIV-1 250 
## SRM 759 [EllysThreePrimes](https://community.topcoder.com/stat?c=problem_statement&pm=15458&rd=17514)

* Observation 1: If the digit is 0, then number of the digit must be 1. (So we can handle 0 specially and remove it)
* Observation 2: After removing 0, the length of strings is no more than 18. 
* Observation 3: Each number of digits will be at least 1.

Then the problems turns to distribute 18 to 9 pieces, each pieces should be at least 1. Which is C(17, 8) = 24310, we can just enumerate this via permutation and then brute force on it.

## SRM 758 [SelfDescFind](https://community.topcoder.com/stat?c=problem_statement&pm=15436&rd=17531)
Since the prime numbers is sparse(about 8k in [10^4, 10^5]), we can brute force with two level for-loops and then verify the remaining number is also prime.

## SRM 757 [CentipedeSocks](https://community.topcoder.com/stat?c=problem_statement&pm=15445&rd=17496)
Some ideas of game theory is involved. Imagine we are the opposite player who want to makes X as large as possible. We can do this in a enumeration way. Suppose there are N sock buckets which one for a kind of socks. We are going to make each bucket no more than F sockets, since otherwise the other player can take them out as a valid pair. 

1. Each time, we find out some bucket to add more socks but not exceed F.
2. When no such choice in step 1, we find a bucket with largest remaining socks and then add one sock and let the other player to take them out, clean the bucket and try again.
3. When the player gets C turns, end with current used socks.
4. When no more socks end with -1.

