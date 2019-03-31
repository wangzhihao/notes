```shell
AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis list-streams

Shell> AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis put-record --stream-name zhihaow_inventory_age_test --data XzxkYXRhPl8x --partition-key dummy
{
    "ShardId": "shardId-000000000000",
    "SequenceNumber": "49594348104124453756945298227039983254520891401675210754"
}
Shell> AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis get-shard-iterator --stream-name kinesis-analytics-demo-stream --shard-id shardId-000000000000 --shard-iterator-type TRIM_HORIZON 
{
    "ShardIterator": "AAAAAAAAAAHbe4SRd1ZTKCLj4vKefXicac3kebFsaDpaMKJedZLsaskE8h07gCvIGZvs6iBT3JgVtFx8d2kyOqc7vKb3m5ObTRUhfSBPtlF8NCMqs06/d37yN9vUchJ7CrUyQTEfPz7twJBlzNMj1cRe7mTYj7J+Ii/LHd+mgPcyD3lYzzbEmRD8dl3HX9aLc+04jOMdELG5LlTiGX70EjVzgdEbxoVjJYB7KB6XTtqtlfXRjR7wKQ=="
}

Shell> AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis get-records --shard-iterator AAAAAAAAAAFW49TNoTNhqrfYcyUkN4muB70WC3E5dsA+wD7Bls82354F8lYuCd0CBHtW0pQUCL4BmIu48yPyP7lT7sOaxMBgj5a9BhH1pmPdLO1QKQAAdqTMRjiiAcdHYBU1uONUk5J6Ii1KdzseV+Hh5Zde0DaZD6t0OJQBTHCbtG1yE+WO7KgNaxj4keakA7yAMEYaWrp3s2LzvHTBPsG7EbdkyfRGcIvCjl3+2QpivXMglwSfyw== --limit 1
{
    "Records": [
        {
            "ApproximateArrivalTimestamp": 1554008691.137,
            "SequenceNumber": "49594346130240894744602781133208255458965826669761265666",
            "Data": "eyJUSUNLRVJfU1lNQk9MIjoiQ1ZCIiwiU0VDVE9SIjoiVEVDSE5PTE9HWSIsIkNIQU5HRSI6MC4yMywiUFJJQ0UiOjUzLjA1fQ==",
            "PartitionKey": "PartitionKey"
        }
    ],
    "MillisBehindLatest": 7680000,
    "NextShardIterator": "AAAAAAAAAAHb9pgXRZSRcy4FQ7nFQQhGNC6DYLCKBkv3EKE3wvMKyyVwfNDWOXpC8woTAghs2PqSpXjn8NVlLKYppdeBfIKz3bynDkmh2OmkjjzbK26GGlFdRMzOjgkcdF6n7xCd29l1D6AtKEMJX1x9SZQkl4LB6wDzE9zJKde2CZO0xfmR80FWVBXRZLcZigqIv0xKSaJz65O79o5S+QGXAtSBpO9vzMg9PbI50tE20WQbmkU6RA=="
}
Shell> echo eyJUSUNLRVJfU1lNQk9MIjoiQ1ZCIiwiU0VDVE9SIjoiVEVDSE5PTE9HWSIsIkNIQU5HRSI6MC4yMywiUFJJQ0UiOjUzLjA1fQ== | base64 -d
{"TICKER_SYMBOL":"CVB","SECTOR":"TECHNOLOGY","CHANGE":0.23,"PRICE":53.05}%
```
