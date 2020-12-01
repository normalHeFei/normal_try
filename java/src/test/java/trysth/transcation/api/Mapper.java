package trysth.transcation.api;

import org.apache.ibatis.annotations.Update;

/**
 * @author hf
 * @date 18-11-7
 */
@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    @Update("update sys_user set mobile = 'abcdef' where user_id = 1")
    int update();
}
