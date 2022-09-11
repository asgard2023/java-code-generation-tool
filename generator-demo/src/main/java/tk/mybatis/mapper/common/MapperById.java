package tk.mybatis.mapper.common;

import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

public interface MapperById<T> extends Mapper<T> , InsertUseGeneratedKeysMapper<T> {
}
