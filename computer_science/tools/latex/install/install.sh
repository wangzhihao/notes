#!/bin/bash
set -e

function main() {
        rm -rf ~/tex-installation && mkdir -p ~/tex-installation && cd ~/tex-installation
        # https://stackoverflow.com/a/874542/1494097
        # pwd
        curl -LO http://mirror.utexas.edu/ctan/systems/texlive/tlnet/install-tl-unx.tar.gz
        tar -xvf install-tl-unx.tar.gz

        cd install-tl-20190728
        TEXLIVE_INSTALL_PREFIX=~/tex-installation ./install-tl
}

main
