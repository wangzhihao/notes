```shell
AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis list-streams

Shell> AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis put-record --stream-name zhihaow_inventory_age_test --data XzxkYXRhPl8x --partition-key dummy
{
    "ShardId": "shardId-000000000000",
    "SequenceNumber": "49594348104124453756945298227039983254520891401675210754"
}
Shell> AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis get-shard-iterator --stream-name kinesis-analytics-demo-stream --shard-id shardId-000000000000 --shard-iterator-type LATEST
{
    "ShardIterator": "AAAAAAAAAAHbe4SRd1ZTKCLj4vKefXicac3kebFsaDpaMKJedZLsaskE8h07gCvIGZvs6iBT3JgVtFx8d2kyOqc7vKb3m5ObTRUhfSBPtlF8NCMqs06/d37yN9vUchJ7CrUyQTEfPz7twJBlzNMj1cRe7mTYj7J+Ii/LHd+mgPcyD3lYzzbEmRD8dl3HX9aLc+04jOMdELG5LlTiGX70EjVzgdEbxoVjJYB7KB6XTtqtlfXRjR7wKQ=="
}

Shell> AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis get-records --shard-iterator AAAAAAAAAAHbe4SRd1ZTKCLj4vKefXicac3kebFsaDpaMKJedZLsaskE8h07gCvIGZvs6iBT3JgVtFx8d2kyOqc7vKb3m5ObTRUhfSBPtlF8NCMqs06/d37yN9vUchJ7CrUyQTEfPz7twJBlzNMj1cRe7mTYj7J+Ii/LHd+mgPcyD3lYzzbEmRD8dl3HX9aLc+04jOMdELG5LlTiGX70EjVzgdEbxoVjJYB7KB6XTtqtlfXRjR7wKQ==
```
