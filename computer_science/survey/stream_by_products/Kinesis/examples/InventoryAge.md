
Kinesis Analytics currently only support one Kinesis source stream, so we need to combine multiple streams into one, and then we can create multiple in-application streams to join them. The following are the schema of the source stream in json.

```json
{
    "merchant_customer_id": "integer",
    "marketplace_id": "integer",
    "fnsku": "varchar(16)",
    "quantity": "integer",
    "event_type": "varchar(16)",
    "event_time": "timestamp"
}
```

This is a cli command to populate data into kinesis.
```shell
AWS_CREDENTIALS_ODIN=com.amazon.access.fba-inv-health-for-devo-DataPlatformIAM-1 aws kinesis put-record --stream-name zhihaow_inventory_age_test --data '{"merchant_customer_id":1234567,"marketplace_id":1,"fnsku":"X00FINFSK","quantity":53,"event_time":"2019-03-10T00:00:00.000Z"}' --partition-key dummy
```

Here are the SQL in Kinesis Analytics:
```sql
create or replace stream "inventory" (
    "merchant_customer_id" integer);
    
create or replace pump "inventory_pump" as 
   insert into "inventory"
      select stream 
       "merchant_customer_id" -- the double quote is mandatory for variable
      from "SOURCE_SQL_STREAM_001"
      where "event_type" = 'total'; -- the single quote is for string constant
```
