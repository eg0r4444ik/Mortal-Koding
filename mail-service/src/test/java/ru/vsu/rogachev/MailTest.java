package ru.vsu.rogachev;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vsu.rogachev.generator.CodeGenerator;
import ru.vsu.rogachev.repositories.ConfirmRepository;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.MailSenderService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MailTest {

    @Autowired
    private CodeGenerator generator;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ConfirmRepository confirmRepository;

    @Autowired
    private ConfirmService confirmService;

    @Test
    void contextLoads() throws Exception {
        assertThat(generator).isNotNull();
        assertThat(mailSenderService).isNotNull();
        assertThat(confirmRepository).isNotNull();
        assertThat(confirmService).isNotNull();
    }

}
