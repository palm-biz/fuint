package cloud.palmbiz.application.bookcate.service;

import cloud.palmbiz.application.book.service.BookCateService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtBookCate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCateCommandService {

    private final BookCateService bookCateService;

    @Transactional(rollbackFor = Exception.class)
    public MtBookCate addBookCate(MtBookCate mtBookCate) throws BusinessCheckException {
        return bookCateService.addBookCate(mtBookCate);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtBookCate updateBookCate(MtBookCate mtBookCate) throws BusinessCheckException {
        return bookCateService.updateBookCate(mtBookCate);
    }
}
