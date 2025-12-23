package eu.kotkas.villak.core.roosid.useCase;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.service.RoosidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetGamePdfUseCase {

    private final RoosidService roosidService;
    private final TemplateEngine templateEngine;

    public byte[] execute() {
        log.info("GetGamePdfUseCase.execute");
        Game initialGame = roosidService.getInitialGame();

        Map<String, Object> variables = new HashMap<>();
        variables.put("rounds", initialGame.getRounds());
        variables.put("fastMoneyQuestions", initialGame.getFastMoney().getQuestions());

        String html = renderTemplateToHtml("roosid-printout-template.html", variables);
        return convertHtmlToPdf(html);
    }

    public String renderTemplateToHtml(String templateName, Map<String, Object> variables) {
        Context ctx = new Context();
        ctx.setVariables(variables);
        return templateEngine.process(templateName, ctx);
    }

    private byte[] convertHtmlToPdf(String html) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to render PDF", e);
        }
    }

}
