package po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName UserInfoPO
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@Data
@TableName(value = "user_info")
public class UserInfoPO implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private Long roleId;

}