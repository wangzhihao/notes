```shell
AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis put-record --stream-name zhihaow_inventory_age_test --data '{"merchant_customer_id":1234567,"marketplace_id":1,"fnsku":"X00FINFSK","quantity":53,"event_time":"2019-03-10T00:00:00.000Z"}' --partition-key dummy
```

```sql
CREATE OR REPLACE STREAM "DESTINATION_SQL_STREAM" (
    "merchant_customer_id" numeric);

CREATE OR REPLACE PUMP "myPUMP" AS
   INSERT INTO "DESTINATION_SQL_STREAM"
      SELECT STREAM
       merchant_customer_id
      FROM "SOURCE_SQL_STREAM_001";
```
