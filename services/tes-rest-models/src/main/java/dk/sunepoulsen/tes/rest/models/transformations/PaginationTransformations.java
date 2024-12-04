package dk.sunepoulsen.tes.rest.models.transformations;

import dk.sunepoulsen.tes.rest.models.PaginationModel;
import org.springframework.data.domain.Page;

public class PaginationTransformations {
    private PaginationTransformations() {
    }

    public static <T> PaginationModel<T> toPaginationResult(Page<T> page) {
        PaginationModel<T> paginationResult = new PaginationModel<>();
        paginationResult.getMetadata().setPage(page.getNumber());
        paginationResult.getMetadata().setSize(page.getSize());
        paginationResult.getMetadata().setTotalItems(page.getTotalElements());
        paginationResult.getMetadata().setTotalPages(page.getTotalPages());
        paginationResult.setResults(page.stream().toList());

        return paginationResult;
    }
}
