package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.exception.metadata.CannotUploadImageException;
import com.vitasoft.goodsgrapher.domain.model.dto.UploadImageDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Value("${image.upload-path.metadata}")
    private String imagePath;

    public ArticleFile uploadMetadataImage(String memberId, int metaSeq, MultipartFile file, int displayOrder) {
        try {
            UploadImageDto uploadImageDto = new UploadImageDto(file);
            uploadImage(imagePath, uploadImageDto);
            return new ArticleFile(metaSeq, uploadImageDto.getDisplayName(), uploadImageDto.getFileName(), uploadImageDto.getFileSize(), uploadImageDto.getFileType(), memberId, displayOrder);
        } catch (IOException e) {
            throw new CannotUploadImageException();
        }

    }

    private void uploadImage(String imagePath, UploadImageDto uploadImageDto) throws IOException {
        File directory = new File(imagePath, uploadImageDto.getDate());
        File image = new File(imagePath + File.separator + uploadImageDto.getFileName() + uploadImageDto.getFileType());
        FileUtils.forceMkdir(directory);

        uploadImageDto.getFile().transferTo(image);
    }
}
