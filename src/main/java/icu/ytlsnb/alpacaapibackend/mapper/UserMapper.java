package icu.ytlsnb.alpacaapibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.ytlsnb.alpacaapibackend.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
