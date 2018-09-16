# Install LaTeX on Linux 
This is [a good toturial](https://linuxconfig.org/introduction-to-latex-on-linux)

```
yum install texlive texlive-latex
```

# To install LaTeX custom package.
1. Download LaTeX package from [CTAN](https://ctan.org/)
2. Unzip the package and there should be `*.ins` inside it
3. Generate `*.sty` file from `*.ins` via command `latex <filename>.ins`
4. move `*.sty` file to the latex directory, in linux the path is `/usr/share/texmf/tex/latex/`
5. Update LaTeX database via `sudo mktexlsr`
