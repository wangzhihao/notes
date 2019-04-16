How to include svg images in LaTeX? 

How to install Inkscape?

	On mac use brew 
	On Fedora, use yum with this repo: http://wiki.inkscape.org/wiki/index.php/FC3
	https://w.amazon.com/bin/view/Rudresha/Sshfs/

What are the famous and popular yum repo?
	https://fedoraproject.org/wiki/EPEL
	https://fedoraproject.org/wiki/Third_party_repositories

	sudo yum install https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
	sudo yum install https://download1.rpmfusion.org/free/el/rpmfusion-free-release-7.noarch.rpm

How to handle this error?
	Error: Package: 4:perl-5.8.8-12.i386 (fc6-updates)
	           Requires: libdb-4.3.so
	 You could try using --skip-broken to work around the problem
	 You could try running: rpm -Va --nofiles --nodigest

The pacakge is a bit of older, we already have libdb-4.7.so, but it's still asking for libdb-4.3
	zhihaow@dev-dsk-zhihaow-ec2-2c-72819b04] locate libdb 
	/lib/libdb-4.7.so
	/lib64/libdb-4.7.so
	/lib64/libdbus-1.so.3
	/lib64/libdbus-1.so.3.4.0
	/usr/lib/libdb-4.7.so
	/usr/lib64/libdb-4.3.so
	/usr/lib64/libdb-4.7.so
	/usr/lib64/libdb_cxx-4.3.so
	/usr/lib64/libdbus-c++-1.so.0
	/usr/lib64/libdbus-c++-1.so.0.0.0
	/usr/lib64/libdbus-glib-1.so.2
	/usr/lib64/libdbus-glib-1.so.2.1.0
	/usr/lib64/compiz/libdbus.so
	/usr/lib64/xulrunner/components/libdbusservice.so
	/usr/share/doc/rsyslog-5.8.10/omlibdbi.html
	/var/snitch/agent/V2.267/shlib/i686-linux-64int/libdb-4.3.so

Error: xz compression not available
https://www.centos.org/forums/viewtopic.php?t=61044

zhihaow@dev-dsk-zhihaow-ec2-2c-72819b04] cat /etc/redhat-release
Red Hat Enterprise Linux Server release 5.3 (Tikanga)

https://bobcares.com/blog/fix-error-xz-compression-not-available/

```
sudo yum --disablerepo=* --enablerepo=epel list | wc -l

https://rpms.remirepo.net/rpmphp/zoom.php?rpm=inkscape

https://fedora.pkgs.org/29/fedora-x86_64/fedora-repos-29-1.noarch.rpm.html
https://fedora.pkgs.org/29/fedora-i386/fedora-repos-29-1.noarch.rpm.html
http://download-ib01.fedoraproject.org/pub/fedora-secondary/releases/29/Everything/i386/os/Packages/f/fedora-repos-29-1.noarch.rpm

sudo yum install http://springdale.math.ias.edu/data/puias/unsupported/7/x86_64/dnf-conf-0.6.4-2.sdl7.noarch.rpm
sudo yum install http://springdale.math.ias.edu/data/puias/unsupported/7/x86_64//dnf-0.6.4-2.sdl7.noarch.rpm
sudo yum install http://springdale.math.ias.edu/data/puias/unsupported/7/x86_64/python-dnf-0.6.4-2.sdl7.noarch.rpm
yum install python-dnf-0.6.4-2.sdl7.noarch.rpm  dnf-0.6.4-2.sdl7.noarch.rpm dnf-conf-0.6.4-2.sdl7.noarch.rpm
```
