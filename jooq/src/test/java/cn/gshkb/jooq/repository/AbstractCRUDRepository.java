package cn.gshkb.jooq.repository;


import cn.gshkb.jooq.pojo.*;
import org.assertj.core.util.*;
import org.jooq.*;
import org.jooq.impl.*;
import org.springframework.dao.*;
import org.springframework.util.*;

import java.sql.*;
import java.util.*;

import static org.jooq.impl.DSL.*;


//@Slf4j
public abstract class AbstractCRUDRepository<R extends UpdatableRecord, ID, P extends AbstractBasePojo> {
    protected final DSLContext dslContext;
    protected final Table<R>   table;
    protected final Field<ID>  idField;
    protected final Class<P>   pojoClass;

    public AbstractCRUDRepository(DSLContext dslContext, Table<R> table, Field<ID> idField, Class<P> pojoClass) {
        this.dslContext = dslContext;
        this.table = table;
        this.pojoClass = pojoClass;
        this.idField = idField;
    }

    public List<P> getPage(Integer page, Integer pageSize, String sortField, String sortOrder,
                           Map<String, Object> filterQuery) {
        return dslContext.selectFrom(table)
                         .limit(pageSize)
                         .offset((page - 1) * pageSize)
                         .fetchInto(pojoClass);
    }

   /* public Page<P> findByPageable(Pageable pageable, Map<String, Object> filterQuery) {
        Optional<Condition> condition = createWhereConditions(filterQuery);

        SelectConnectByStep<R> step =
                condition.isPresent() ? dslContext.selectFrom(table)
                                                  .where(condition.get()) : dslContext.selectFrom(table);

        long total = condition.isPresent() ? dslContext.selectCount()
                                                       .from(table)
                                                       .where(condition.get())
                                                       .fetchOne(0, Long.class)
                : dslContext.selectCount().from(table).fetchOne(0, Long.class);

        List<P> pList = step
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(Long.valueOf(pageable.getOffset()).intValue())
                .fetchInto(pojoClass);
        PageImpl<P> pPage = new PageImpl<P>(pList, pageable, total);
        return pPage;
    }


    public Page<P> findByPageable(Pageable pageable, Collection<? extends Condition> condition) {
        SelectConditionStep<R> step  = null;
        PageImpl<P>            pPage = null;
        try {
            step = dslContext.selectFrom(table).where(condition);
            long total = dslContext.selectCount().from(table).where(condition).fetchOne(0, Long.class);
            List<P> pList = step
                    .orderBy(getSortFields(pageable.getSort()))
                    .limit(pageable.getPageSize())
                    .offset(Long.valueOf(pageable.getOffset()).intValue())
                    .fetchInto(pojoClass);
            pPage = new PageImpl<P>(pList, pageable, total);
        } catch (Exception e) {
            log.info("关闭 step {}", e);
        } finally {
            this.stepClose(step);
        }
        return pPage;
    }*/


    public <Z> P fetchOne(Field<Z> field, Z value) {
        return dslContext.selectFrom(this.table).where(new Condition[]{field
                .equal(value)}).fetchOneInto(pojoClass);

    }

    public <Z> boolean isExistsByField(Field<Z> field, Z value) {
        return dslContext
                .select(count(idField))
                .from(table)
                .where(field.eq(value))
                .fetchOne(0, Integer.class) > 0;
    }


    public <Z> List<P> fetchList(Field<Z> field, Z value) {
        return dslContext.selectFrom(this.table).where(new Condition[]{field
                .equal(value)}).fetchInto(pojoClass);
    }

    public <Z> Optional<P> fetchOptional(Field<Z> field, Z value) {
        return Optional.ofNullable(this.fetchOne(field, value));
    }

    /*public Page<P> findByPageableNoTotal(Pageable pageable, Map<String, Object> filterQuery) {
        Optional<Condition> condition = createWhereConditions(filterQuery);

        SelectConnectByStep<R> step =
                condition.isPresent() ? dslContext.selectFrom(table)
                                                  .where(condition.get()) : dslContext.selectFrom(table);

        List<P> pList = step
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(Long.valueOf(pageable.getOffset()).intValue())
                .fetchInto(pojoClass);
        PageImpl<P> pPage = new PageImpl<P>(pList);
        return pPage;
    }*/

    @SuppressWarnings({"unchecked"})
    private Optional<Condition> createWhereConditions(Map<String, Object> conditionMap) {
        if (conditionMap == null) {
            return Optional.empty();
        }

        return conditionMap.keySet().stream()
                           .map(key -> {
                               Condition  condition;
                               TableField field = getTableField(key);
                               if (field.getDataType().getType() == String.class) {
                                   condition = field.like("%" + conditionMap.get(key) + "%");
                               } else {
                                   condition = field.eq(conditionMap.get(key));
                               }
                               return condition;
                           }).reduce(Condition::and);
    }


    /*rotected Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();

        if (sortSpecification == null) {
            return querySortFields;
        }

        Iterator<Sort.Order> specifiedFields = sortSpecification.iterator();

        while (specifiedFields.hasNext()) {
            Sort.Order specifiedField = specifiedFields.next();

            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();

            TableField   tableField     = getTableField(sortFieldName);
            SortField<?> querySortField = convertTableFieldToSortField(tableField, sortDirection);
            querySortFields.add(querySortField);
        }

        return querySortFields;
    }*/

    private TableField getTableField(String fieldName) {
        TableField tableField;
        try {
            tableField = (TableField) table.getClass().getField(fieldName.toUpperCase()).get(table);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            String errorMessage = String.format("Could not find table field: {%s}", fieldName);
            throw new InvalidDataAccessApiUsageException(errorMessage, ex);
        }

        return tableField;
    }

    /*private SortField<?> convertTableFieldToSortField(TableField tableField, Sort.Direction sortDirection) {
        if (sortDirection == Sort.Direction.ASC) {
            return tableField.asc();
        } else {
            return tableField.desc();
        }
    }*/

    public P getById(ID id) {
        R byId = getRecordById(id);
        return byId == null ? null : byId.into(pojoClass);
    }

    public void delete(ID id) {
        dslContext.delete(table).where(idField.eq(id)).execute();
    }

    protected R getRecordById(ID id) {
        R one = dslContext.selectFrom(table)
                          .where(idField.eq(id))
                          .fetchOne();
        return one;
    }

    protected R newRecord(Object obj) {
        return dslContext.newRecord(table, obj);
    }

    /*public P insert(P object) {
        object.setCreateAt(DateTools.getCurrentDateTime());
        object.setDelFlag(false);
        object.setVersion(0L);
        UpdatableRecord r = this.newRecord(object);
        createInitID(r);
        r.insert();
        return r.into(object);
    }*/

    /**
     * 新增 初始化 ID
     *
     * @param r
     */
    private void createInitID(UpdatableRecord r) {
//        Long id = SnowflakeIdWorker.getNextIdLong();
//        r.setValue(idField, (ID) id);
    }


   /* public void insert(P... objects) {
        this.insert((Collection) Arrays.asList(objects));
    }*/

    /*public void insert(Collection<P> objects) {
        if (objects.size() > 1) {
            int[] ints = dslContext.batchInsert((Collection<? extends TableRecord<?>>) this.records(objects, false))
                                   .execute();
        } else if (objects.size() == 1) {
            UpdatableRecord r = this.records(objects, false).get(0);
            int             i = r.insert();
        }

    }*/

    /*public void update(P object) {
        object.setUpdateAt(DateTools.getCurrentDateTime());
        update(Collections.singletonList(object));
    }*/

  /*  public int updateReturnEffRowsCount(P object) {
        int[] result = update(Collections.singletonList(object));
        if (result.length > 0) {
            return result[0];
        }
        return 0;
    }*/

   // @SuppressWarnings("unchecked")
    /*public void update(P... objects) {
        update(asList(objects));
    }*/

    @SuppressWarnings("unchecked")
  /*  public int[] update(Collection<P> objects) {
        int[] result = {};
        if (objects.size() > 1) {
            result = this.dslContext.batchUpdate((Collection<? extends UpdatableRecord<?>>) records(objects, true))
                                    .execute();
        } else if (objects.size() == 1) {
            result = new int[1];
            int update = records(objects, true).get(0).update();
            result[0] = update;
        }
        return result;
    }*/

    private Field<?>[] pk() {
        UniqueKey<?> key = table.getPrimaryKey();
        return key == null ? null : key.getFieldsArray();
    }

    /*private List<R> records(Collection<P> objects, boolean forUpdate) {
        List<R>    result = Lists.newArrayList();
        Field<?>[] pk     = pk();
        for (AbstractBasePojo object : objects) {
            //如果修改数据，更新修改时间字段
            if (forUpdate) {
                object.setUpdateAt(DateTools.getCurrentDateTime());
            } else {
                //新增数据，初始化字段
                object.setCreateAt(DateTools.getCurrentDateTime());
                object.setDelFlag(false);
                object.setVersion(0L);
            }
            R record = dslContext.newRecord(table, object);
            if (forUpdate && pk != null) {
                for (Field<?> field : pk) {
                    record.changed(field, false);
                }
            }
            //新增数据，重新插入ID
            if (!forUpdate) {
                createInitID(record);
            }
            // resetChangedOnNotNull(record);
            result.add(record);
        }

        return result;
    }*/


//    static final void resetChangedOnNotNull(Record record) {
//        int size = record.size();
//
//        for (int i = 0; i < size; i++) {
//            if (record.get(i) == null) {
//                if (!record.field(i).getDataType().nullable()) {
//                    record.changed(i, false);
//                }
//            }
//        }
//    }

    protected SelectConditionStep<Record> getSelectConditionStep(SelectOnConditionStep<Record> step,
                                                                 String conditionSql) {
        if (StringUtils.isEmpty(conditionSql)) {
            return step.where(Lists.newArrayList());
        }
        return step.where(conditionSql);
    }

    protected Long getTotalCount(SelectOnConditionStep<Record1<Integer>> step, String conditionSql) {
        if (StringUtils.isEmpty(conditionSql)) {
            return step.where(Lists.newArrayList()).fetchOne(0, Long.class);
        }
        return step.where(conditionSql).fetchOne(0, Long.class);
    }

    protected List<Field<?>> getFields(List<String> cols) {
        List<Field<?>> fieldList = Lists.newArrayList();
        if (cols == null || cols.size() <= 0) {
            return fieldList;
        }
        for (String item : cols) {
            fieldList.add(DSL.field(item));
        }
        return fieldList;
    }

    protected void stepClose(SelectConditionStep step) {
        try {
            if (step != null) {
                step.close();
            }
        } catch (Exception e) {
            //log.error("关闭step", e);
        }
    }

    protected void requestSetClose(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
           // log.error("关闭resultSet", e);
        }
    }


}