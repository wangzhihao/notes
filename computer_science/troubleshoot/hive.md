# could only be replicated to 0 nodes instead of minReplication (=1)

```
Caused by: org.apache.hadoop.hive.ql.metadata.HiveException: org.apache.hadoop.ipc.RemoteException(java.io.IOException): File /tmp/hive/hadoop/0f8953c5-56b9-418d-9240-a842161fe673/hive_2019-04-16_03-17-54_378_2895178286416517212-1/_task_tmp.-mr-10000/_tmp.000052_1 could only be replicated to 0 nodes instead of minReplication (=1). 
```
This error can be casued by out of disk. If the hive run on EMR, you can verify disk usage on EMR's monitor. Other possible reasons can be found [here](https://stackoverflow.com/a/36310025)
