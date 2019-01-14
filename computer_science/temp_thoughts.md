# temporary thoughts
## Async/await
* https://en.wikipedia.org/wiki/Async/await
* https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/concepts/async/

## Flow
* https://flow.org/en/
* https://docs.aws.amazon.com/iot/latest/developerguide/protocols.html

## MQTT
http://mqtt.org/
https://docs.aws.amazon.com/appsync/latest/devguide/real-time-data.html

## Signal and System

* See how the complex exponential looks like

```Matlab
t = linspace(0, 5, 250);
f = exp((0.5 + 10i)*t);
plot3(t, real(f), imag(f))
```
![Rendered Results](https://image.ibb.co/jhxEy0/octave-online-line-4.png)

* Does the form of convolution sum of LTI (Linear Time-Invariant) System voilate causality property?

![convolution sum of LTI](https://image.ibb.co/kSAyrL/Snip20181021-2.png)

Anwser is no, since for the system which obeys causality property, the `h[n-k]` will always be zero when `n-k < 0`. In other words, `y[n]` will never depend on `x[k]` which `k > n`.

Another way to interpret convolution is by superposition. Acutally convolution is used to be called superposition integral. From LTI system's perspective, `y[n]` is the superposition of all the distributions produced by each `x[k]`. Each `x[k]` contributes one distribution `h` with magnitude/weight `x[k]`. E.g. If `h` is an exponential decay distribution, then there are infinite weighted exponential decay distributions sum up to construt the `y[n]`. This is exactly the same idea as stated in following quoted paragraph in the book "Signal and System":

![interpret convolution in LTI's view](https://image.ibb.co/m2Jh1L/Snip20181021-3.png)

[Here](http://lpsa.swarthmore.edu/Convolution/Convolution.html) are a good toturial by [Prof. Erik Cheever](http://www.swarthmore.edu/NatSci/echeeve1/) to show this idea with matlab illustration. The [matlab source code](http://lpsa.swarthmore.edu/Convolution/Convolution.m) is also avaliable.

* How can we prove the commutativity of convolution intutively instread of the algebra way?

The answer is probably we can't. This is the same like the high dimensional geometry which involves so many dimensions that man can think all together. Algebra serves as a way to let us focus on some partial dimensions instead of the whole dimensions. In this example, the Algebra proves the commutativity for only one point at `n`, and then apply the propety to all the `n` analytically.

* Why RPC instead of REST?

From different use cases, RPC and REST can be both useful.

RPC is preferable to talk between applications servers. REST is preferable to talk between client and servers. The key difference is that client is volatile and of large quantity. REST is designed to let client easily joined in and out, and more specifically to let each client leave less profile/footprint on server to ease server's burden. One example is the TCP connection, both in connection time and connection resource. On the contrary, applications servers are stable and of less quantities. Since they are almost always there and their relationship are known, it's more benefit to keep some memory on them in order to optimize the performance, One example of memory is to keep long-running dedicated connection. Another example is to keep directly IP address mapping so as to jump less network hops.

![Hybrid system](https://cdn-images-1.medium.com/max/800/1*cwe0ECdhqd-87wa8RozIJw.png)

Gotcha: This might be only the difference of long-connection and short-connection instead of RPC and REST? Need close inspection.

* What's the general techniques to address multiple versions of arguments in RPC framework? The multiple versions of data can be introduced when the API is evolving, client and server are not keep in the same pace, which results in some client might have older version of API, while some server might provide new version of API service. How to make it still compatiable?
