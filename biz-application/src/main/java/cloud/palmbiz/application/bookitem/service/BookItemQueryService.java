package cloud.palmbiz.application.bookitem.service;

import cloud.palmbiz.common.book.dto.BookItemDto;
import cloud.palmbiz.common.book.dto.BookItemPage;
import cloud.palmbiz.application.book.service.BookItemService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtBookItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookItemQueryService {

    private final BookItemService bookItemService;

    public PaginationResponse<BookItemDto> queryBookItemListByPagination(BookItemPage bookItemPage) {
        return bookItemService.queryBookItemListByPagination(bookItemPage);
    }

    public MtBookItem getBookItemById(Integer id) {
        return bookItemService.getBookItemById(id);
    }

    public MtBookItem getUserBookItem(Integer bookId, Integer userId, Integer orderGoodsId) {
        return bookItemService.getUserBookItem(bookId, userId, orderGoodsId);
    }

    public BookItemDto getBookDetail(Integer id) {
        return bookItemService.getBookDetail(id);
    }

    public List<MtBookItem> queryBookItemListByParams(Map<String, Object> params) {
        return bookItemService.queryBookItemListByParams(params);
    }
}
