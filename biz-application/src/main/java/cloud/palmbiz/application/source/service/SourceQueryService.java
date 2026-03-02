package cloud.palmbiz.application.source.service;

import cloud.palmbiz.common.domain.TreeNode;
import cloud.palmbiz.common.domain.TreeSelect;
import cloud.palmbiz.common.service.SourceService;
import cloud.palmbiz.interfaces.vo.RouterVo;
import cloud.palmbiz.infrastructure.model.TSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceQueryService {

    private final SourceService sourceService;

    public List<TSource> getAvailableSources(Integer merchantId, String status) {
        return sourceService.getAvailableSources(merchantId, status);
    }

    public List<TreeNode> getSourceTree(Integer merchantId, String status) {
        return sourceService.getSourceTree(merchantId, status);
    }

    public List<TSource> findDatasByIds(String[] ids) {
        return sourceService.findDatasByIds(ids);
    }

    public List<TSource> getMenuListByUserId(Integer merchantId, Integer accountId) {
        return sourceService.getMenuListByUserId(merchantId, accountId);
    }

    public List<RouterVo> buildMenus(List<TreeNode> treeNodes) {
        return sourceService.buildMenus(treeNodes);
    }

    public List<TreeNode> buildMenuTree(List<TreeNode> menus) {
        return sourceService.buildMenuTree(menus);
    }

    public List<TreeSelect> buildMenuTreeSelect(List<TreeNode> menus) {
        return sourceService.buildMenuTreeSelect(menus);
    }
}
