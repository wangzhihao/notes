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
ssserver -p 8443 -k dummy -m rc4-md5 -vv

# Or setup it in background
sudo ssserver -p 8443 -k dummy -m rc4-md5 --user nobody -d start

# Setup shadowsocks local
/usr/local/bin/sslocal -s ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com -p 8443 -k dummy -m rc4-md5 -vv

# Test with Curl
curl -x socks5h://127.0.0.1:1080 http://www.google.com/
```

Please ensure 8443 port to be accessible in AWS Security Group.

Here is the debug log

```txt
/usr/local/bin/sslocal -s ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com -p 8443 -k dummy -m rc4-md5 -vv

2019-10-10 09:54:34 INFO     loading libcrypto from /usr/lib/libcrypto.dylib
2019-10-10 09:54:34 INFO     starting local at 127.0.0.1:1080
2019-10-10 09:54:34 DEBUG    using event model: kqueue
2019-10-10 09:54:43 VERBOSE  fd 3 POLL_IN
2019-10-10 09:54:43 DEBUG    accept
2019-10-10 09:54:43 DEBUG    chosen server: ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com:8443
2019-10-10 09:54:43 VERBOSE  fd 7 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 7 POLL_IN
2019-10-10 09:54:43 INFO     connecting www.google.com:80 from 127.0.0.1:55927
2019-10-10 09:54:43 DEBUG    resolving b'ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com' with type 1 using server 10.4.4.10
2019-10-10 09:54:43 DEBUG    resolving b'ec2-18-136-102-7.ap-southeast-1.compute.amazonaws.com' with type 1 using server 192.168.199.1
2019-10-10 09:54:43 VERBOSE  fd 7 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_OUT
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:43 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:44 VERBOSE  fd 8 POLL_IN
2019-10-10 09:54:44 VERBOSE  fd 7 POLL_IN
2019-10-10 09:54:44 DEBUG    destroy: www.google.com:80
2019-10-10 09:54:44 DEBUG    destroying remote
2019-10-10 09:54:44 DEBUG    destroying local
2019-10-10 09:54:54 VERBOSE  sweeping timeouts

ssserver -p 8443 -k dummy -m rc4-md5 -vv
2019-10-10 01:54:20 INFO     loading libcrypto from libcrypto.so.10
2019-10-10 01:54:20 INFO     starting server at 0.0.0.0:8444
2019-10-10 01:54:20 DEBUG    using event model: epoll

tail -f /var/log/shadowsocks.log
2019-10-10 02:03:23 INFO     connecting www.google.com:80 from 54.240.199.97:16714
2019-10-10 02:03:29 INFO     connecting is.amazon.com:80 from 54.240.199.97:28931
```
