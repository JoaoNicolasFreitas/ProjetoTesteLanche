package com.snack.services;

import com.snack.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProductServiceTest {
    ProductService productService;
    Product produtoXBurguer;
    Product produtoXSalada;
    Product produtoXBaconInvalido;

    @BeforeEach
    public void setUp() {
        produtoXBurguer = new Product(2, "x-burguer", 10, "src\\test\\java\\com\\snack\\imagens\\x-burguer.jpg");
        produtoXSalada = new Product(1, "x-salada", 15, "src\\test\\java\\com\\snack\\imagens\\x-salada.jpg");
        produtoXBaconInvalido = new Product(3, "X-bacon", 20, "caminho_inexistente.jpg");
        productService = new ProductService();

    }

    @Test
    public void deveAtualizarProdutoExistenteComSucesso() {

        productService.save(produtoXBurguer);
        productService.update(produtoXSalada);


        Assertions.assertEquals("x-salada", produtoXSalada.getDescription());
        Assertions.assertEquals(15, produtoXSalada.getPrice());
        Path path = Paths.get(produtoXSalada.getImage());
        Assertions.assertTrue(Files.exists(path));
    }

    @Test
    public void deveSalvarProdutoComImagemValida() {
        boolean resultado = productService.save(produtoXBurguer);

        Assertions.assertTrue(resultado);
    }

    @Test
    public void deveFalharAoSalvarProdutoComImagemInvalida() {
        boolean result = productService.save(produtoXBaconInvalido);

        Assertions.assertFalse(result);
    }

    @Test
    void deveRemoverProdutoExistenteEImagem() {
        Product produto = new Product(2, "x-burguer", 10, "src\\test\\java\\com\\snack\\imagens\\X-burguer.jpg");
        productService.save(produto);

        Path caminhoImagemDestino = Paths.get(String.format("src\\test\\java\\com\\snack\\imagesBox\\%d.jpg", produto.getId()));
        Assertions.assertTrue(Files.exists(caminhoImagemDestino));

        productService.remove(produto.getId());

        Assertions.assertFalse(Files.exists(caminhoImagemDestino));
    }

    @Test
    public void deveRetornarCaminhoDaImagemPorId() {
        productService.save(produtoXBurguer);

        String caminhoDaImagem = productService.getImagePathById(produtoXBurguer.getId());
        Assertions.assertTrue(Files.exists(Paths.get(caminhoDaImagem)));
    }

}

