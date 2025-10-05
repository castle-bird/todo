package com.zerock.apiserver.repository.search;

import com.zerock.apiserver.dto.PageRequestDTO;
import com.zerock.apiserver.dto.PageResponseDTO;
import com.zerock.apiserver.dto.ProductDTO;

public interface ProductSearch {
    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);
}
