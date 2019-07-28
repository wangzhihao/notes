# Install LaTeX on Linux

It's very probably that `texlive` bundle available to your linux packages managers like yum. The texlive contains a rich collection of TeX/LaTeX tools.

## Check the version

The first step we need to check the version of texlive. It might be pretty old in some linux systems. For example, 2012 version doesn't contains [ctex](http://www.ctex.org/) package for Chinese. 

```
In AWS EC2 m5a.large
[ec2-user@ip-10-0-3-149 ~]$ yum info texlive
Loaded plugins: priorities, update-motd, upgrade-helper
Available Packages
Name        : texlive
Arch        : x86_64
Epoch       : 2
Version     : 2012
Release     : 38.20130427_r30134.24.amzn1
Size        : 18 k
Repo        : amzn-main/latest
Summary     : TeX formatting system
URL         : http://tug.org/texlive/
License     : Artistic 2.0 and GPLv2 and GPLv2+ and LGPLv2+ and LPPL and MIT and Public Domain and UCD and Utopia
Description : The TeX Live software distribution offers a complete TeX system for a
            : variety of Unix, Macintosh, Windows and other platforms. It
            : encompasses programs for editing, typesetting, previewing and printing
            : of TeX documents in many different languages, and a large collection
            : of TeX macros and font libraries.
            :
            : The distribution includes extensive general documentation about TeX,
            : as well as the documentation for the included software packages.
```

If you are good with the version, you can simply install with

```
yum install texlive
```

## To install LaTeX custom package

1. Download LaTeX package from [CTAN](https://ctan.org/)
2. Unzip the package and there should be `*.ins` inside it
3. Generate `*.sty` file from `*.ins` via command `latex <filename>.ins`
4. move `*.sty` file to the latex directory, in linux the path is `/usr/share/texmf/tex/latex/`
5. Update LaTeX database via `sudo mktexlsr`
