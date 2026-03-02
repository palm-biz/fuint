package cloud.palmbiz.interfaces.backendApi.controller;

import cloud.palmbiz.common.domain.TreeNode;
import cloud.palmbiz.common.domain.TreeSelect;
import cloud.palmbiz.application.identity.account.dto.AccountInfoDto;
import cloud.palmbiz.common.source.dto.SourceDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.application.source.service.SourceService;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.TSource;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理控制类
 */
@Api(tags="管理端-后台菜单相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/source")
public class BackendSourceController extends BaseController {

    /**
     * 菜单服务接口
     */
    private SourceService sourceService;

    /**
     * 获取菜单列表
     */
    @ApiOperation(value = "获取菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("@pms.hasPermission('system:menu:index')")
    public ResponseObject list() {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        List<TreeNode> sources = sourceService.getSourceTree(accountInfo.getMerchantId(), "");
        return getSuccessResult(sources);
    }

    /**
     * 获取菜单详情
     */
    @ApiOperation(value = "获取菜单详情")
    @RequestMapping(value = "/info/{sourceId}", method = RequestMethod.GET)
    public ResponseObject info( @PathVariable("sourceId") Long sourceId) {
        TSource tSource = sourceService.getById(sourceId);

        SourceDto sourceDto = new SourceDto();
        sourceDto.setId(tSource.getSourceId());
        sourceDto.setName(tSource.getSourceName());
        if (tSource.getParentId() != null) {
            sourceDto.setParentId(tSource.getParentId());
        }
        sourceDto.setMerchantId(tSource.getMerchantId());
        sourceDto.setPath(tSource.getPath());
        sourceDto.setIcon(tSource.getNewIcon());
        sourceDto.setNewIcon(tSource.getNewIcon());
        sourceDto.setSort(tSource.getSourceStyle());
        sourceDto.setEname(tSource.getEname());
        sourceDto.setStatus(tSource.getStatus());
        sourceDto.setIsMenu(tSource.getIsMenu());
        sourceDto.setPerms(tSource.getPath().replaceAll("/", ":"));
        sourceDto.setDescription(tSource.getDescription());

        return getSuccessResult(sourceDto);
    }

    /**
     * 新增菜单
     */
    @ApiOperation(value = "新增菜单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("@pms.hasPermission('system:menu:add')")
    public ResponseObject addSource(@RequestBody Map<String, Object> param) {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();

        String name = param.get("name").toString();
        String status = param.get("status").toString();
        String parentId = param.get("parentId").toString();
        String icon = param.get("icon").toString();
        String path = param.get("path").toString();
        String sort = param.get("sort").toString();
        Integer isMenu = param.get("isMenu") == null ? 1 : Integer.parseInt(param.get("isMenu").toString());

        TSource tSource = new TSource();
        tSource.setSourceName(name);
        tSource.setMerchantId(accountInfo.getMerchantId());
        tSource.setStatus(status);
        tSource.setNewIcon(icon);
        tSource.setIsLog(1);
        tSource.setPath(path);
        tSource.setSourceStyle(sort);
        tSource.setIsMenu(isMenu);
        tSource.setSourceCode(path);

        String eName = "";
        String[] paths = path.split("/");
        for (int i = 0; i < paths.length; i++) {
             eName = eName + CommonUtil.firstLetterToUpperCase(paths[i]);
        }
        tSource.setEname(eName);

        if (StringUtil.isNotBlank(parentId)) {
            if (Integer.parseInt(parentId) > 0) {
                TSource parentSource = sourceService.getById(parentId);
                tSource.setParentId(parentSource.getSourceId());
                tSource.setSourceLevel(parentSource.getSourceLevel() + 1);
            } else {
                tSource.setSourceLevel(1);
            }
        } else {
            tSource.setSourceLevel(1);
        }
        sourceService.addSource(tSource, accountInfo.getId());
        return getSuccessResult(true);
    }

    /**
     * 修改菜单
     */
    @ApiOperation(value = "修改菜单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("@pms.hasPermission('system:menu:edit')")
    public ResponseObject update(@RequestBody Map<String, Object> param) {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();

        String name = param.get("name").toString();
        String status = param.get("status").toString();
        String parentId = param.get("parentId").toString();
        String icon = param.get("icon").toString();
        String path = param.get("path").toString();
        String sort = param.get("sort").toString();
        Integer isMenu = param.get("isMenu") == null ? 1 : Integer.parseInt(param.get("isMenu").toString());
        Long id = param.get("id") == null ? 0 : Long.parseLong(param.get("id").toString());

        TSource editSource = sourceService.getById(id);
        if (!editSource.getMerchantId().equals(accountInfo.getMerchantId()) && accountInfo.getMerchantId() > 0) {
            return getFailureResult(201, "抱歉，您没有修改的权限");
        }
        editSource.setSourceName(name);
        editSource.setStatus(status);
        editSource.setNewIcon(icon);
        editSource.setIsLog(1);
        editSource.setPath(path);
        editSource.setSourceStyle(sort);
        editSource.setIsMenu(isMenu);
        editSource.setSourceCode(editSource.getPath());

        String eName = "";
        String[] paths = path.split("/");
        for (int i = 0; i < paths.length; i++) {
             eName = eName + CommonUtil.firstLetterToUpperCase(paths[i]);
        }
        editSource.setEname(eName);

        if (StringUtil.isNotBlank(parentId)) {
            if (Integer.parseInt(parentId) > 0) {
                TSource parentSource = sourceService.getById(Long.parseLong(parentId));
                if (parentSource != null) {
                    editSource.setParentId(parentSource.getSourceId());
                    editSource.setSourceLevel(parentSource.getSourceLevel() + 1);
                }
            } else {
                editSource.setSourceLevel(1);
                editSource.setParentId(0);
            }
        } else {
            editSource.setSourceLevel(1);
        }
        sourceService.editSource(editSource);
        return getSuccessResult(true);
    }

    /**
     * 删除菜单
     */
    @ApiOperation(value = "删除菜单")
    @RequestMapping(value = "/delete/{sourceId}", method = RequestMethod.GET)
    @PreAuthorize("@pms.hasPermission('system:menu:delete')")
    public ResponseObject delete(@PathVariable("sourceId") Long sourceId) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        TSource tSource = sourceService.getById(sourceId);
        if (!tSource.getMerchantId().equals(accountInfo.getMerchantId()) && accountInfo.getMerchantId() > 0) {
            return getFailureResult(201, "抱歉，您没有删除的权限");
        }
        sourceService.deleteSource(tSource, StatusEnum.DISABLE.getKey());
        return getSuccessResult(true);
    }

    /**
     * 获取菜单下拉树列表
     */
    @ApiOperation(value = "获取菜单下拉树列表")
    @RequestMapping(value = "/treeselect", method = RequestMethod.GET)
    public ResponseObject treeselect() {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();

        List<TreeNode> sources = sourceService.getSourceTree(accountInfo.getMerchantId(), "");
        List<TreeSelect> data = sourceService.buildMenuTreeSelect(sources);

        return getSuccessResult(data);
    }
}
