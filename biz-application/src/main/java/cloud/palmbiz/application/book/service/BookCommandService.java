package cloud.palmbiz.application.book.service;

import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCommandService {

    private final BookService bookService;

    @Transactional(rollbackFor = Exception.class)
    public MtBook addBook(MtBook mtBook) throws BusinessCheckException {
        return bookService.addBook(mtBook);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtBook updateBook(MtBook mtBook) throws BusinessCheckException {
        return bookService.updateBook(mtBook);
    }
}
