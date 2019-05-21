## Issue
```
shell> mosh server
The locale requested by LC_CTYPE=UTF-8 isn't available here.
Running `locale-gen UTF-8' may be necessary.

mosh-server needs a UTF-8 native locale to run.

Unfortunately, the local environment ([no charset variables]) specifies
the character set "US-ASCII",

The client-supplied environment (LC_CTYPE=UTF-8) specifies
the character set "US-ASCII".

locale: Cannot set LC_CTYPE to default locale: No such file or directory
locale: Cannot set LC_ALL to default locale: No such file or directory
LANG=
LC_CTYPE=UTF-8
LC_NUMERIC="POSIX"
LC_TIME="POSIX"
LC_COLLATE="POSIX"
LC_MONETARY="POSIX"
LC_MESSAGES="POSIX"
LC_PAPER="POSIX"
LC_NAME="POSIX"
LC_ADDRESS="POSIX"
LC_TELEPHONE="POSIX"
LC_MEASUREMENT="POSIX"
LC_IDENTIFICATION="POSIX"
LC_ALL=
Connection to dev-dsk-zhihaow-ec2-2c-72819b04.us-west-2.amazon.com closed.
/usr/local/bin/mosh: Did not find mosh server startup message. (Have you installed mosh on your server?)
```

## Solution

Use the following commands to check the locale configuration in your __client__ OS. Different OS have different naming conventions. In our examples, the right name can be en_US.UTF-8

```
shell> locale
LANG=
LC_COLLATE="C"
LC_CTYPE="en_GB.UTF-8"
LC_MESSAGES="C"
LC_MONETARY="C"
LC_NUMERIC="C"
LC_TIME="C"
LC_ALL=

shell> locale -a | grep UTF-8 | grep en
en_US.UTF-8
en_NZ.UTF-8
en_AU.UTF-8
en_GB.UTF-8
en_CA.UTF-8
en_IE.UTF-8
```

Now we can login with following command:
```
LC_CTYPE=en_US.UTF-8 mosh server
```

## Reference
https://github.com/mobile-shell/mosh/issues/98
