## Notes
Question 1. Why "Thus a formula of length n can have only about n/logn distinct function and predicate symbols"?

One example formula is `C1^C10^C11^...Cm`, whose length is `m + (m - 1) + (log1 + log2 + ... +logm)`, so we got.

```
2m - 1 + (log1 + log2 + ... +logm) <= n
2m - 1 + log(m!) <= n
2m - 1 + mlogm - m + 1 <= n  , due to [Stirling's approximation](https://en.wikipedia.org/wiki/Stirling%27s_approximation#Derivation)
mlogm + m <= n
```
Don't know how to go next. Maybe some practise of logarithm [[1]](http://mathcentral.uregina.ca/QQ/database/QQ.09.04/alain1.html) can help. 
