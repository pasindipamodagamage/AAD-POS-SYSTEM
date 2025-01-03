import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Author: pasindi
 * Date: 1/2/25
 * Time: 9:25 AM
 * Description:
 */

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ItemDTO {
    private String code;
    private String name;
    private double price;
    private int qty;
}
