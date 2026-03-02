package cloud.palmbiz.application.source.service;

import cloud.palmbiz.common.service.SourceService;
import cloud.palmbiz.infrastructure.model.TSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SourceCommandService {

    private final SourceService sourceService;

    @Transactional(rollbackFor = Exception.class)
    public void addSource(TSource tSource, Integer accountId) {
        sourceService.addSource(tSource, accountId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editSource(TSource source) {
        sourceService.editSource(source);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSource(TSource source, String status) {
        sourceService.deleteSource(source, status);
    }
}
