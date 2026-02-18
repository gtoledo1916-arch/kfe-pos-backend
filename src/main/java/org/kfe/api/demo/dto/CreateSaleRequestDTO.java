package org.kfe.api.demo.dto;

import java.util.List;

public class CreateSaleRequestDTO {

    private Long cajeroId;
    private List<SaleItemDTO> items;

    // GETTERS
    public Long getCajeroId()           { return cajeroId; }
    public List<SaleItemDTO> getItems() { return items; }

    // SETTERS
    public void setCajeroId(Long cajeroId)        { this.cajeroId = cajeroId; }
    public void setItems(List<SaleItemDTO> items) { this.items = items; }
}