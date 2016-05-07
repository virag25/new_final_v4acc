package amigoinn.models;

import java.util.List;

/**
 * Created by Maulik Patel on 10/10/2015.
 */
public class GroupItems
{
    public List<ChildItems> getItems() {
        return items;
    }

    public void setItems(List<ChildItems> items) {
        this.items = items;
    }

    List<ChildItems> items;

}
