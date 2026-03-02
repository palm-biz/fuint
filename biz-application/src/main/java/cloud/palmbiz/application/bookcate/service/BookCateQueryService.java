package cloud.palmbiz.application.bookcate.service;

import cloud.palmbiz.common.book.dto.BookCatePage;
import cloud.palmbiz.application.book.service.BookCateService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtBookCate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCateQueryService {

    private final BookCateService bookCateService;

    public PaginationResponse<MtBookCate> queryBookCateListByPagination(BookCatePage bookCatePage) {
        return bookCateService.queryBookCateListByPagination(bookCatePage);
    }

    public MtBookCate getBookCateById(Integer id) {
        return bookCateService.getBookCateById(id);
    }

    public List<MtBookCate> getAvailableBookCate(Integer merchantId, Integer storeId) {
        return bookCateService.getAvailableBookCate(merchantId, storeId);
    }
}
