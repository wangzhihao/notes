# Show pacakge version

You can find the version number of a package in your repositories with the `yum info` command. [link](https://serverfault.com/a/385234/176713)

## Show the dependency

For simple dependency questions we can use `yum deplist`, For complex dependency questions we can use `repoquery`. For example.

```
Shell> repoquery --tree-requires --recursive --level 1 --resolve texlive

texlive-2:2012-38.20130427_r30134.24.amzn1.x86_64 [cmd line]
 \_  texlive-collection-latexrecommended-2:svn25795.0-38.20130427_r30134.24.amzn1.noarch [1: texlive-collection-latexrecommended]
 \_  texlive-kpathsea-2:svn28792.0-38.24.amzn1.noarch [1: tex-kpathsea]
 \_  texlive-scheme-basic-2:svn25923.0-38.20130427_r30134.24.amzn1.noarch [1: texlive-scheme-basic]
 \_  texlive-tetex-2:svn29585.3.0-38.24.amzn1.noarch [1: tex-tetex]
```
