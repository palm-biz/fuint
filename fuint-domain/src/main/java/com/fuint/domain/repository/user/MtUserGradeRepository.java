package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtUserGrade;

import java.util.List;
import java.util.Optional;

/**
 * 会员等级仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtUserGradeRepository {

    MtUserGrade save(MtUserGrade mtUserGrade);

    Optional<MtUserGrade> findById(Long id);

    List<MtUserGrade> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtUserGrade> findByStatus(String status);
}
