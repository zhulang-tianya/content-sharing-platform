package com.content.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.content.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门Mapper接口
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 查询所有部门
     */
    @Select("SELECT * FROM sys_dept WHERE deleted = 0 ORDER BY sort")
    List<Dept> selectAllDepts();

    /**
     * 根据父ID查询子部门数量
     */
    @Select("SELECT COUNT(1) FROM sys_dept WHERE parent_id = #{parentId} AND deleted = 0")
    int countByParentId(@Param("parentId") Long parentId);

    /**
     * 根据部门ID查询用户数量
     */
    @Select("SELECT COUNT(1) FROM sys_user WHERE dept_id = #{deptId} AND deleted = 0")
    int countUsersByDeptId(@Param("deptId") Long deptId);
}
