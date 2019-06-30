When using the network commands like _ssh_, _ping_, _nc_ etc. We need to keep in mind that the local preference won't take effect if the remote configurations turn them off. In other words, the options will take effects on mutual aggrements. 

Example 1, ping unreachable. It doesn't always mean the host is unreachable. The remote side might simply ignore ICMP requests by some firewall like _iptables_.

```
ping 188.166.238.205
PING 188.166.238.205 (188.166.238.205): 56 data bytes
Request timeout for icmp_seq 0
Request timeout for icmp_seq 0
^C
--- 188.166.238.205 ping statistics ---
2 packets transmitted, 0 packets received, 100.0% packet loss
```

Example 2, ssh with password didn't work. This might be the remote side sshd disallow to tunnel with password by configuration `PasswordAuthentication no`

```
ssh -o PreferredAuthentications=password -o PubkeyAuthentication=no  188.166.238.205
Permission denied (publickey).
```

Example 3, ssh failed with Permission denied (publickey). This might be the remote side didn't allow the ssh public key in `~/.ssh/authorized_keys`.
