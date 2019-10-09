[This post](https://gist.github.com/austinhappel/5614113) suggests to use socks
server to bypass the firewall.

```sh
# Set the socks server remotely.
ssh -N -gv -D 8081 -i /home/ec2-user/zhihaow-ec2.pem ec2-user@ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com > /dev/null 2>&1 &
# Set up http server for the pac file
sudo python -m SimpleHTTPServer 80 > /dev/null 2>&1 &
# Test it
curl -x socks5h://18.136.102.7:8081 http://www.google.com/
```

Please ensure 8081 port to be accessible in AWS Security Group.
