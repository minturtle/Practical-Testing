package sample.cafekiosk.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;
import sample.cafekiosk.spring.service.ProductService;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.*;


@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("HTTP POST를 통해 신규 상품을 등록한다")
    void t1() throws Exception {
        //given
        ProductController.NewProductRequest requestBody = ProductController.NewProductRequest.builder()
                .productName("테스트 상품")
                .productType(ProductType.BOTTLE)
                .sellingType(ProductSellingType.SELLING)
                .price(10000)
                .build();


        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/new")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}