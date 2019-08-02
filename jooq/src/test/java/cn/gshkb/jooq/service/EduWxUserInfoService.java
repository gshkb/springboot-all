package cn.gshkb.jooq.service;



/**
 * 用户信息表
 */
/*@Slf4j
@Service
public class EduWxUserInfoService  {

    @Autowired
    private EduWxUserInfoRepository eduWxUserInfoRepository;



     public Page<EduWxUserInfoPojo> findbyPageAndCondition(Pageable pageable, Collection<? extends Condition> condition) {
         return  eduWxUserInfoRepository.findByPageable(pageable, condition);
}


    public EduWxUserInfoPojo findById(Long id){
        return eduWxUserInfoRepository.fetchOne(EDU_WX_USER_INFO.ID,id);
    }
    public EduWxUserInfoPojo insert(EduWxUserInfoPojo pojo){
        pojo.setCreateAt(DateTools.getCurrentDateTime());
        pojo.setDelFlag(false);
        pojo = eduWxUserInfoRepository.insert(pojo);
        log.info("新增用户信息表成功。{}。", pojo.toString());
        return pojo;
    }
    public EduWxUserInfoPojo update(EduWxUserInfoPojo pojo){
        EduWxUserInfoPojo dbPojo = this.findById(pojo.getId());
        if(dbPojo==null){
         return null;}
        BeanUtil.copyPropertiesIgnoreNull(pojo, dbPojo);
        dbPojo.setUpdateAt(DateTools.getCurrentDateTime());
        eduWxUserInfoRepository.update(dbPojo);
        log.info("修改用户信息表成功。{}。", dbPojo.toString());
        return dbPojo;
    }
    public void delete(Long id){
        EduWxUserInfoPojo dbPojo = this.findById(id);
        dbPojo.setUpdateAt(DateTools.getCurrentDateTime());
        dbPojo.setDelFlag(true);
        eduWxUserInfoRepository.update(dbPojo);
        log.info("删除用户信息表成功。{}。", dbPojo.toString());
    }
}*/
