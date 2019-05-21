# Problem version 1
Think about on a page with several links. For different regions we want to show parts of links.
```
NA:                EU:               FE:
--------------     --------------    ------------- 
|   Link 1   |     |   Link 1   |    |  Link 1   |
|   Link 2   |     |   Link 3   |    |  Link 3   |
|   Link 3   |     --------------    |  Link 4   |
|   Link 4   |                       ------------- 
-------------- 
```

How do we write a clean and flexible code for this?

# Attempt 1
One proposal is to write a checker method for each link, and thus one config slot for each link in config file and one if-statement for each slot in code. It looks like this:
```java
if (ConfigUtils.isLink1Visible()) {
   displayLink1();
}

if (ConfigUtils.isLink2Visible()) {
   displayLink2();
}

if (ConfigUtils.isLink3Visible()) {
   displayLink3();
}

if (ConfigUtils.isLink4Visible()) {
   displayLink4();
}
```

The drawback of the proposal is that we will have too many config slots to maintain when the similar pages increase in the project. 

# Problem version 2
Think about the case that "Link 1 and Link 3" can be fall into a category, which usually are luanched together.

# Reference
https://code.amazon.com/reviews/CR-5605013

