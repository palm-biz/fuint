package cloud.palmbiz.application.book.service;

import cloud.palmbiz.common.book.dto.BookDto;
import cloud.palmbiz.common.book.dto.BookPage;
import cloud.palmbiz.common.book.param.BookableParam;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookQueryService {

    private final BookService bookService;

    public PaginationResponse<BookDto> queryBookListByPagination(BookPage bookPage) {
        return bookService.queryBookListByPagination(bookPage);
    }

    public BookDto getBookById(Integer id, boolean fillDate) throws ParseException {
        return bookService.getBookById(id, fillDate);
    }

    public List<String> isBookable(BookableParam param) throws BusinessCheckException, ParseException {
        return bookService.isBookable(param);
    }

    public List<MtBook> getBookList(Integer merchantId, Integer storeId) {
        return bookService.getBookList(merchantId, storeId);
    }
}
