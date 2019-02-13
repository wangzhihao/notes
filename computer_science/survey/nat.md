A Formally Verified NAT: https://vignat.github.io/

This [AWS VPC Turorial](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_Scenario2.html) introduces a common example which is a multi-tier website, with the web servers in a public subnet and the database servers in a private subnet. Please note the public subnet can access the private subnet directly, since public subnet is 10.0.__0__.0/24 and private subnet is 10.0.__1__.0/24, as also indicated in [this post](https://medium.com/@carlos.ribeiro/connecting-on-rds-server-that-is-not-publicly-accessible-1aee9e43b870).

[A good QA](https://stackoverflow.com/questions/22188444/why-do-we-need-private-subnet-in-vpc) on "Why do we need private subnet in VPC?"