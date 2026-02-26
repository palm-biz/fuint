package cloud.plambiz.common.service;

import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.TGenCode;

/**
 * 代码生成服务接口
 */
public interface GenCodeService {

    /**
     * 分页查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<TGenCode> queryGenCodeListByPagination(PaginationRequest paginationRequest);

    /**
     * 添加生成代码
     *
     * @param  tGenCode 代码参数
     * @throws BusinessCheckException
     * @return
     */
    TGenCode addGenCode(TGenCode tGenCode) throws BusinessCheckException;

    /**
     * 根据ID获取信息
     *
     * @param  id
     * @return
     */
    TGenCode queryGenCodeById(Integer id);

    /**
     * 更新生成代码
     * @param  tGenCode
     * @throws BusinessCheckException
     * @return
     * */
    TGenCode updateGenCode(TGenCode tGenCode);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     * @return
     */
    void generatorCode(String tableName);

}
