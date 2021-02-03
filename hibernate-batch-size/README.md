# JpaPagingItemReader hibernate.default_batch_fetch_size issue

## Expected Behavior

```yaml
spring:
  jpa:
    properties:
      hibernate.default_batch_fetch_size: ${chunkSize:100}
```

```sql
select teacher0_.id as id1_13_, teacher0_.name as name2_13_, teacher0_.subject as subject3_13_ from teacher teacher0_ limit ?, ?
select students0_.teacher_id as teacher_3_12_1_, students0_.id as id1_12_1_, students0_.id as id1_12_0_, students0_.name as name2_12_0_, students0_.teacher_id as teacher_3_12_0_ from student students0_ where students0_.teacher_id in (?, ?, ?, ?, ?)
```
## Current Behavior

### truncate=true

```sql
select teacher0_.id as id1_1_, teacher0_.name as name2_1_, teacher0_.subject as subject3_1_ from teacher teacher0_ limit ?
select students0_.teacher_id as teacher_3_0_1_, students0_.id as id1_0_1_, students0_.id as id1_0_0_, students0_.name as name2_0_0_, students0_.teacher_id as teacher_3_0_0_ from student students0_ where students0_.teacher_id=?
select students0_.teacher_id as teacher_3_0_1_, students0_.id as id1_0_1_, students0_.id as id1_0_0_, students0_.name as name2_0_0_, students0_.teacher_id as teacher_3_0_0_ from student students0_ where students0_.teacher_id=?
select students0_.teacher_id as teacher_3_0_1_, students0_.id as id1_0_1_, students0_.id as id1_0_0_, students0_.name as name2_0_0_, students0_.teacher_id as teacher_3_0_0_ from student students0_ where students0_.teacher_id=?
select students0_.teacher_id as teacher_3_0_1_, students0_.id as id1_0_1_, students0_.id as id1_0_0_, students0_.name as name2_0_0_, students0_.teacher_id as teacher_3_0_0_ from student students0_ where students0_.teacher_id=?
select students0_.teacher_id as teacher_3_0_1_, students0_.id as id1_0_1_, students0_.id as id1_0_0_, students0_.name as name2_0_0_, students0_.teacher_id as teacher_3_0_0_ from student students0_ where students0_.teacher_id=?
```

### truncate=false

## Solution

