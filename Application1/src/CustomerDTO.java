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

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
}
