package com.zerock.apiserver.controller;

import com.zerock.apiserver.domain.Product;
import com.zerock.apiserver.dto.PageRequestDTO;
import com.zerock.apiserver.dto.PageResponseDTO;
import com.zerock.apiserver.dto.ProductDTO;
import com.zerock.apiserver.service.ProductService;
import com.zerock.apiserver.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final CustomFileUtil fileUtil;
    private final ProductService productService;

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {
        return productService.getList(pageRequestDTO);
    }

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno) {
        return productService.get(pno);
    }

    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO) {
        List<MultipartFile> files = productDTO.getFiles();

        List<String> uploadFileNames = fileUtil.saveFile(files);

        productDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        long pno = productService.register(productDTO);

        return Map.of("result", pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable("pno") Long pno, ProductDTO productDTO) {
        // old product
        ProductDTO oldProductDTO = productService.get(pno);

        // file upload
        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFile(files);

        // keep files
        List<String> uploadedFileNames = productDTO.getUploadFileNames();

        if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        productService.modify(productDTO);

        List<String> oldFileNames = oldProductDTO.getUploadFileNames();
        if (oldFileNames != null && oldFileNames.size() > 0) {

            // 삭제된 파일들
            List<String> removeFiles = oldFileNames.stream().filter(fileName -> !uploadedFileNames.contains(fileName)).collect(Collectors.toList());


            fileUtil.deleteFiles(removeFiles);
        }// end if

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> delete(@PathVariable("pno") Long pno) {
        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);

        fileUtil.deleteFiles(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    };
}
