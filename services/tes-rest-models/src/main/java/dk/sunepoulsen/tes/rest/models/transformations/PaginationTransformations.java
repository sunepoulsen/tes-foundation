package dk.sunepoulsen.tes.rest.models.transformations;

import dk.sunepoulsen.tes.rest.models.PaginationModel;
import org.springframework.data.domain.Page;

/**
 * Converts Spring Data pages to REST pagination models.
 */
public class PaginationTransformations {
    private PaginationTransformations() {
    }

    /**
     * Copies a page's contents and pagination metadata into a model.
     *
     * @param <T> type of the page contents
     * @param page page to convert
     * @return pagination model containing the page contents and metadata
     */
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
