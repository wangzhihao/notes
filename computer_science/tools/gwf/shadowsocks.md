One shadowsocks instance is setup on my aws account(accept.acm@gmail.com) in singapore region. The endpoint is:

```
ec2-52-77-239-113.ap-southeast-1.compute.amazonaws.com
```

The Pem is put here: https://drive.google.com/file/d/1_zfNMAFvULLziZLqXlAv8DkwLP36mg3b/view?usp=drive_open

## Shadowsocks Debug

Here are steps to debug shadowsocks.

```sh
# Login remote server
ssh -o TCPKeepAlive=no -o ServerAliveInterval=15 -i ~/zhihaow-ec2.pem ec2-user@ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com

# Setup shadowsocks server
ssserver -p 8443 -k confidence -m rc4-md5 -vv

# Setup shadowsocks local
/usr/local/bin/sslocal -s ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com -p 8443 -k confidence -m rc4-md5 -vv

# Test with Curl
curl -x socks5h://127.0.0.1:1080 http://www.google.com/
```

Please ensure 8443 port to be accessible in AWS Security Group.
