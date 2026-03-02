package cloud.palmbiz.application.bookitem.service;

import cloud.palmbiz.common.service.BookItemService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtBookItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class BookItemCommandService {

    private final BookItemService bookItemService;

    @Transactional(rollbackFor = Exception.class)
    public MtBookItem addBookItem(MtBookItem mtBookItem) throws BusinessCheckException, ParseException {
        return bookItemService.addBookItem(mtBookItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtBookItem updateBookItem(MtBookItem mtBookItem) throws BusinessCheckException {
        return bookItemService.updateBookItem(mtBookItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelBook(Integer id, String remark) throws BusinessCheckException {
        return bookItemService.cancelBook(id, remark);
    }
}
