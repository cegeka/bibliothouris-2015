package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ItemsListingTO<T> {

    private List<T> items = new ArrayList<>();

    private Long itemsCount;

    public ItemsListingTO() {}

    public ItemsListingTO(List<T> items, Long itemsCount) {
        this.items = items;
        this.itemsCount = itemsCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Long itemsCount) {
        this.itemsCount = itemsCount;
    }
}