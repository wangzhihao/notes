# Check the repository

```shell
zhihaow@dev-dsk-zhihaow-ec2-2c-72819b04] sudo yum repolist                                                                                                                         ~
[sudo] password for zhihaow:
Loaded plugins: dkms-build-requires, fastestmirror, priorities, security, versionlock
Loading mirror speeds from cached hostfile
 * dev-dsk-main: dev-desktop-repos.amazon.com
 * dev-dsk-updates: dev-desktop-repos.amazon.com
repo id                                                                  repo name                                                                                             status
al-acc                                                                   2012.03 x86_64 ACC                                                                                      194
al-amazon                                                                x86_64 Amazon                                                                                           223
al-kernel-ac                                                             2012.03 x86_64 Avocado Kernel                                                                            73
al-main                                                                  2012.03 x86_64 Server                                                                                 3,330
al-updates                                                               2012.03 x86_64 Updates                                                                                2,136
clienteng-al12                                                           Client Engineering packages for AL12 - x86_64                                                             6
dev-dsk-main                                                             Amazon Developer Desktop GUI                                                                            935
dev-dsk-updates                                                          Amazon Developer Desktop GUI - Updates                                                                  265
docker-ce-edge                                                           Docker CE Edge - x86_64                                                                                  44
docker-ce-stable                                                         Docker CE Stable - x86_64                                                                                39
repolist: 7,245
```

# Add a new repository

Place the following into `/etc/yum.repos.d/FC6.repo`

```conf
[extras]
name=Fedora Core 6 Extras
mirrorlist=http://mirrors.fedoraproject.org/mirrorlist?repo=extras-6&arch=$basearch
enabled=0
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-fedora-extras
gpgcheck=0

[fc6-updates]
name=Fedora Core 6 Updates
mirrorlist=http://mirrors.fedoraproject.org/mirrorlist?repo=updates-released-fc6&arch=$basearch
enabled=0
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-fedora-extras
gpgcheck=0
```

# Enable the repository

```
sudo yum-config-manager --enable fc6-extras
```

We can also verify with the repolist command that the repo is enabled.

# Some popular repos
1. https://fedoraproject.org/wiki/EPEL
2. https://fedoraproject.org/wiki/Third_party_repositories

```shell
sudo yum install https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum install https://download1.rpmfusion.org/free/el/rpmfusion-free-release-7.noarch.rpm
```

# Some repo discovery service
https://rpms.remirepo.net/
