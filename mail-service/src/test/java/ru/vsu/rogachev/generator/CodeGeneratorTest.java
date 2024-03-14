package ru.vsu.rogachev.generator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CodeGeneratorTest {

    @Autowired
    private CodeGenerator codeGenerator;

    @Test
    void generateActivationCode() throws Exception {
        assertThat(CodeGenerator.CODE_LENGTH).isEqualTo(codeGenerator.generateActivationCode().length());
    }

}
