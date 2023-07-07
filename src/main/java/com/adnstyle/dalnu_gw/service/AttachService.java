package com.adnstyle.dalnu_gw.service;

import com.adnstyle.dalnu_gw.common.AttachYml;
import com.adnstyle.dalnu_gw.domain.Attach;
import com.adnstyle.dalnu_gw.repository.AttachRepository;
import com.adnstyle.dalnu_gw.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachService {
    
    private final AttachRepository attachRepository;
    
    private final AttachYml attachYml;
    
    private final CommonUtil commonUtil;
    
    /**
     * 첨부파일 등록
     * @param uploadFile
     * @param uid
     */
    public void insertAttach(MultipartFile uploadFile, String uid, String tableType) {
        String savedName = commonUtil.createUUID();//랜덤 문자열 생성
        String displayName = uploadFile.getOriginalFilename();
        long fileSize = uploadFile.getSize();
        
        Attach attach = Attach.builder()
            .tableType(tableType)
            .tableId(uid)
            .displayName(displayName)
            .savedName(savedName)
            .savedDir(attachYml.getUploadPath())
            .size(String.valueOf(fileSize))
            .build();
        attachRepository.insertAttach(attach);
    }
    

}
