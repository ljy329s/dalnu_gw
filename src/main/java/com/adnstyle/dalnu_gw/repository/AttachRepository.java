package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Attach;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface AttachRepository {
    void insertAttach(Attach attach);
}
