When the first launch after installed in mac. It may complains issue like

```
Session Ended (broken pipe). Restart it?
```

From the console we can see:

```
bash - Cannot exec /bin/false: no such file or directory - Unix ...
```

It can be fixed by change command in 'Preference > Profile > Command' to existing commands like `/bin/bash` or `/bin/zsh`
