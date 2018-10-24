# YouCompleteMe Install Guide

This post describes a series of issues occurred when installing [YouCompleteMe](https://github.com/Valloric/YouCompleteMe) on an EC2 instance inside my company.

## ERROR: Python headers are missing in ...
The following error happens since my company has a dedicated building machine which build artifacts(including python3 here) and then sync these artifacts to my EC2 instance. Here the path starts with `/local/p4clients` only exists in the building machine and not in my EC2 machine.
```
zhihaow@dev-dsk-zhihaow-ec2-2c-72819b04] python3 install.py
Searching Python 3.4 libraries...
ERROR: Python headers are missing in /local/p4clients/pkgbuild-lZofH/workspace/src/CPython34Runtime/build/python3.4/include/python3.4m.
```

The YouCompleteMe try to get include directory from build-time variable, which stored in `_sysconfigdata.py`. see [code](https://github.com/Valloric/ycmd/blob/master/build.py#L236)

```
include_dir = sysconfig.get_config_var( 'INCLUDEPY' )
```
A more proper way is to get via the system path, as what the [python source code](https://github.com/python/cpython/blob/master/Lib/sysconfig.py#L424) do.

```
include_dir = sysconfig.get_path('include')
```
As a hot fix, I simply replace the build-time variable to the latter path variable in YouCompleteMe source code. Long term a Pull Request might be fired to YouCompleteMe.


## CC & CXX issue
By default the build script finds bcc (an old& simple compiler) instead of gcc & g++ on my machine. To inform the build script, which leverages cmake to do the trick under the scene, to use latter configuration, we can set some environment variables like following:

```
CXX=/local/home/zhihaow/local/bin/g++ CC=/local/home/zhihaow/local/bin/gcc  python3 install.py
```

## Linking issue
YouCompleteMe find the old system-default GLIBC even if there is a newer GLIBC installed. 
```
ImportError: /usr/lib64/libstdc++.so.6: version `GLIBCXX_3.4.20' not found (required by /local/home/zhihaow/.vim/plugged/YouCompleteMe/third_party/ycmd/ycmd/../ycm_core.so)
```
It turns out the vim's `py3` command modifies its `LD_LIBRARY_PATH` variable, Since `LD_LIBRARY_PATH` variable is of the top priority during linking, the newer GLIBC is hidden by the older one inside `/usr/lib64`

```
:py3 import os;print(os.environ['LD_LIBRARY_PATH'])
/apollo/env/envImprovement/python2.7/lib:/apollo/env/envImprovement/python3.6/lib:/lib64:/usr/lib64:/apollo/env/envImprovement/lib
```
Currently I workaround by injecting the link path inside [YouCompleteMe code](https://bit.ly/2EKcIRa) like this
```
189     my_env = os.environ.copy()
190     my_env["LD_LIBRARY_PATH"] = "/local/home/zhihaow/local/lib64/:" + my_env["LD_LIBRARY_PATH"]
191     self._server_popen = utils.SafePopen( args, stdin_windows = PIPE,
192                                           stdout = PIPE, stderr = PIPE, env= my_env)
```

