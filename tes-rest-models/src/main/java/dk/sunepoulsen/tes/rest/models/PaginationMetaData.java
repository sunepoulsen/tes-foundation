package dk.sunepoulsen.tes.rest.models;

import lombok.Data;

@Data
public class PaginationMetaData implements BaseModel {
    private int page;
    private int totalPages;
    private long totalItems;
    private int size;
}
