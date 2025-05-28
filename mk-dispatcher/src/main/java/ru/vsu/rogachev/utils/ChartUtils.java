package ru.vsu.rogachev.utils;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.vsu.rogachev.client.mk.stats.dto.UserStatisticResponse;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.exception.BusinessLogicExceptions;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class ChartUtils {

    @Getter
    public enum StatType {
        RATING(UserStatisticResponse.GameResult::getNewRating, "Рейтинг", "рейтинга"),
        SCORE(UserStatisticResponse.GameResult::getScore, "Очки", "суммы очков за игру"),
        PERFORMANCE(UserStatisticResponse.GameResult::getPerformance, "Перфоманс", "перфоманса");

        private final Function<UserStatisticResponse.GameResult, Long> extractor;
        private final String title;
        private final String caption;

        StatType(Function<UserStatisticResponse.GameResult, Long> extractor, String title, String caption) {
            this.extractor = extractor;
            this.title = title;
            this.caption = caption;
        }

    }

    @NotNull
    public SendPhoto getStatsGraphic(
            @NotNull Long chatId,
            @NotNull UserStatisticResponse stats,
            @NotNull StatType statsType
    ) throws IOException {
        File chartFile = ChartUtils.buildChart(stats, statsType);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(chartFile));
        sendPhoto.setCaption(String.format("📈 Вот ваш график изменения %s", statsType.getCaption()));

        return sendPhoto;
    }

    private File buildChart(@NotNull UserStatisticResponse stats, @NotNull StatType statsType) throws IOException {
        List<UserStatisticResponse.GameResult> results = stats.getGameResults();
        if (results.isEmpty()) {
            throw BusinessLogicException.of(BusinessLogicExceptions.NO_DATA_FOR_STATS);
        }

        List<Date> xData = stats.getGameResults().stream()
                .map(result -> Timestamp.valueOf(result.getGameDateTime()))
                .collect(Collectors.toList());

        List<Long> yData = stats.getGameResults().stream()
                .map(statsType.getExtractor())
                .collect(Collectors.toList());

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(statsType.getTitle())
                .xAxisTitle("Дата")
                .yAxisTitle(statsType.getTitle())
                .build();

        chart.addSeries(statsType.getTitle(), xData, yData)
                .setMarker(SeriesMarkers.CIRCLE);

        File chartFile = File.createTempFile(statsType.name().toLowerCase() + "_chart", ".png");
        BitmapEncoder.saveBitmap(chart, chartFile.getAbsolutePath(), BitmapEncoder.BitmapFormat.PNG);
        return chartFile;
    }
}

