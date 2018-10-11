# Upload audio to youtube
First of all, convert the audio to video with the following script.
```
#!/bin/bash
set -x
# Install ffmpeg with Homebrew.
# Make sure the cover.jpg is in the current working directory.
# Sample ./convert.sh  sample.m4a
/usr/local/opt/ffmpeg/bin/ffmpeg  -loop 1 -framerate 1  -i ./cover.jpg -i "$1" -c:v libx264 -preset veryslow -crf 0 -c:a copy -shortest output.mkv
```
