package cloud.palmbiz.application.product.goods.service;

import cloud.palmbiz.application.product.goods.command.CreateGoodsCommand;
import cloud.palmbiz.application.product.goods.command.UpdateGoodsCommand;
import cloud.palmbiz.application.product.goods.command.UpdateGoodsStatusCommand;
import cloud.palmbiz.common.util.SeqUtil;
import cloud.palmbiz.domain.goods.model.Goods;
import cloud.palmbiz.domain.goods.model.GoodsId;
import cloud.palmbiz.domain.goods.model.GoodsSku;
import cloud.palmbiz.domain.goods.repository.GoodsRepository;
import cloud.palmbiz.domain.goods.service.GoodsValidationService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品命令服务
 * 处理商品的创建、修改、删除等命令操作
 *
 * @author DDD Refactoring
 */
@Service
@RequiredArgsConstructor
public class GoodsCommandService {

    private final GoodsRepository goodsRepository;
    private final GoodsValidationService validationService;

    /**
     * 创建商品
     */
    @Transactional(rollbackFor = Exception.class)
    public Goods createGoods(CreateGoodsCommand command) throws BusinessCheckException {
        if (command == null) {
            throw new BusinessCheckException("创建商品命令不能为空");
        }

        // 验证
        validationService.validateGoodsCreation(command.getName(), command.getPrice(), command.getStock());

        // 检查商品编码唯一性
        String goodsNo = command.getGoodsNo();
        if (goodsNo == null || goodsNo.isEmpty()) {
            goodsNo = SeqUtil.getNextId("goods");
        }
        boolean exists = goodsRepository.existsByGoodsNo(command.getMerchantId(), goodsNo);
        validationService.validateGoodsNoUniqueness(goodsNo, exists);

        // 创建商品聚合根
        Goods goods = Goods.create(
                command.getName(),
                command.getType(),
                command.getMerchantId(),
                command.getStoreId(),
                command.getCateId(),
                goodsNo,
                command.getPrice(),
                command.getLinePrice(),
                command.getCostPrice(),
                command.getStock()
        );

        // 设置其他属性
        if (command.getLogo() != null) {
            goods.setInfo(command.getName(), command.getLogo(), command.getImages(), command.getDescription());
        }
        if (command.getSalePoint() != null) {
            goods.setSalePoint(command.getSalePoint());
        }
        if (command.getSort() != null) {
            goods.setSort(command.getSort());
        }
        if (command.getCanUsePoint() != null) {
            goods.setCanUsePoint(command.getCanUsePoint());
        }
        if (command.getIsMemberDiscount() != null) {
            goods.setIsMemberDiscount(command.getIsMemberDiscount());
        }
        if (command.getCouponIds() != null) {
            goods.setCouponIds(command.getCouponIds());
        }
        if (command.getServiceTime() != null) {
            goods.setServiceTime(command.getServiceTime());
        }
        if (command.getWeight() != null) {
            goods.setWeight(command.getWeight());
        }

        // 添加SKU
        if (command.getSkuList() != null && !command.getSkuList().isEmpty()) {
            List<GoodsSku> skuList = new ArrayList<>();
            for (CreateGoodsCommand.SkuItem item : command.getSkuList()) {
                GoodsSku sku = GoodsSku.create(
                        item.getSkuNo(),
                        goods.getId(),
                        item.getSpecIds(),
                        item.getPrice(),
                        item.getLinePrice(),
                        item.getCostPrice(),
                        item.getStock(),
                        item.getWeight()
                );
                skuList.add(sku);
            }
            goods.addSkuList(skuList);
        }

        // 保存
        goodsRepository.save(goods);

        return goods;
    }

    /**
     * 更新商品
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateGoods(UpdateGoodsCommand command) throws BusinessCheckException {
        if (command == null || command.getGoodsId() == null) {
            throw new BusinessCheckException("更新商品命令不能为空");
        }

        // 查找商品
        Goods goods = goodsRepository.findById(GoodsId.of(command.getGoodsId()));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        // 更新信息
        if (command.getName() != null) {
            goods.setInfo(command.getName(), command.getLogo(), command.getImages(), command.getDescription());
        }
        if (command.getCateId() != null) {
            goods.setCategory(command.getCateId());
        }
        if (command.getPrice() != null) {
            validationService.validatePriceUpdate(command.getPrice(), command.getCostPrice());
            goods.updatePrice(command.getPrice(), command.getLinePrice(), command.getCostPrice());
        }
        if (command.getStock() != null) {
            goods.setStock(command.getStock());
        }
        if (command.getSalePoint() != null) {
            goods.setSalePoint(command.getSalePoint());
        }
        if (command.getSort() != null) {
            goods.setSort(command.getSort());
        }

        // 保存
        goodsRepository.save(goods);
    }

    /**
     * 更新商品状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateGoodsStatusCommand command) throws BusinessCheckException {
        if (command == null || command.getGoodsId() == null) {
            throw new BusinessCheckException("更新状态命令不能为空");
        }

        Goods goods = goodsRepository.findById(GoodsId.of(command.getGoodsId()));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        validationService.validateStatusChange(goods, command.getStatus());

        if ("A".equals(command.getStatus())) {
            goods.activate(command.getOperator());
        } else if ("D".equals(command.getStatus())) {
            goods.delete(command.getOperator());
        } else if ("N".equals(command.getStatus())) {
            goods.deactivate(command.getOperator());
        }

        goodsRepository.save(goods);
    }

    /**
     * 删除商品
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoods(Integer goodsId, String operator) throws BusinessCheckException {
        if (goodsId == null) {
            throw new BusinessCheckException("商品ID不能为空");
        }

        Goods goods = goodsRepository.findById(GoodsId.of(goodsId));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        goods.delete(operator);
        goodsRepository.save(goods);
    }

    /**
     * 增加库存
     */
    @Transactional(rollbackFor = Exception.class)
    public void addStock(Integer goodsId, Double amount) throws BusinessCheckException {
        if (goodsId == null || amount == null || amount <= 0) {
            throw new BusinessCheckException("参数不合法");
        }

        Goods goods = goodsRepository.findById(GoodsId.of(goodsId));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        goods.addStock(amount);
        goodsRepository.save(goods);
    }

    /**
     * 扣减库存
     */
    @Transactional(rollbackFor = Exception.class)
    public void deductStock(Integer goodsId, Double amount) throws BusinessCheckException {
        if (goodsId == null || amount == null || amount <= 0) {
            throw new BusinessCheckException("参数不合法");
        }

        Goods goods = goodsRepository.findById(GoodsId.of(goodsId));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        validationService.validateGoodsSale(goods, amount);
        goods.deductStock(amount);
        goodsRepository.save(goods);
    }

    /**
     * 增加销量
     */
    @Transactional(rollbackFor = Exception.class)
    public void addSale(Integer goodsId, Double amount) throws BusinessCheckException {
        if (goodsId == null || amount == null || amount <= 0) {
            throw new BusinessCheckException("参数不合法");
        }

        Goods goods = goodsRepository.findById(GoodsId.of(goodsId));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        goods.addSale(amount);
        goodsRepository.save(goods);
    }
}
