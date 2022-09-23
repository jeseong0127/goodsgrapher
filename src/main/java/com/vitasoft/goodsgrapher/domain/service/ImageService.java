package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.domain.exception.image.CannotViewImageException;
import com.vitasoft.goodsgrapher.domain.exception.image.ImageNotFoundException;
import com.vitasoft.goodsgrapher.domain.exception.metadata.CannotUploadImageException;
import com.vitasoft.goodsgrapher.domain.model.dto.UploadImageDto;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.ArticleFile;
import com.vitasoft.goodsgrapher.domain.model.kipris.entity.Metadata;
import com.vitasoft.goodsgrapher.domain.model.kipris.repository.ArticleFileRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${image.upload-path.metadata}")
    private String imagePath;

    private final ArticleFileRepository articleFileRepository;

    public ArticleFile uploadMetadataImage(String memberId, Metadata metadata, MultipartFile file, int displayOrder) {
        try {
            UploadImageDto uploadImageDto = new UploadImageDto(file);
            String fileName = formatFileName(memberId, metadata, displayOrder, uploadImageDto.getFileType());
            uploadImage(imagePath, uploadImageDto, fileName);
            return new ArticleFile(metadata.getMetaSeq(), fileName.substring(fileName.indexOf("/") + 1), fileName, uploadImageDto.getFileSize(), uploadImageDto.getFileType(), memberId, displayOrder);
        } catch (IOException e) {
            throw new CannotUploadImageException();
        }
    }

    private void uploadImage(String imagePath, UploadImageDto uploadImageDto, String fileName) throws IOException {
        File directory = new File(imagePath, fileName.substring(0, fileName.indexOf("/")));
        File image = new File(imagePath + File.separator + fileName);

        FileUtils.forceMkdir(directory);
        uploadImageDto.getFile().transferTo(image);
    }

    private String formatFileName(String memberId, Metadata metadata, int displayOrder, String fileType) {
        String formatMetaSeq = leadingZeros(String.valueOf(metadata.getMetaSeq()));

        String[] folderNameParts = {memberId, metadata.getLastRightHolderName(), metadata.getArticleName(), metadata.getModelName(), metadata.getRegistrationNumber(), metadata.getDsshpclsscd()};
        String folderName = String.join("_", folderNameParts);
        String fileName = "/VS_2022_" + formatMetaSeq + "_0_-1_" + (displayOrder + 1) + fileType;
        return folderName + fileName;
    }

    private String leadingZeros(String n) {
        StringBuilder zero = new StringBuilder();

        if (n.length() < 6) {
            zero.append("0".repeat(6 - n.length()));
        }
        return zero + n;
    }

    public byte[] viewImage(int imageId) {
        ArticleFile articleFile = articleFileRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        return this.viewImage(imagePath + File.separator + articleFile.getFileName());
    }

    private byte[] viewImage(String fileName) {
        try (
                FileInputStream inputStream = new FileInputStream(fileName);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1)
                outputStream.write(buffer, 0, length);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new CannotViewImageException(fileName);
        }
    }
}
