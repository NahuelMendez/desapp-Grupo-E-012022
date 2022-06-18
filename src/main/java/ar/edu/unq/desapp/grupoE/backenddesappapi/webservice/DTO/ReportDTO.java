package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.TradedVolumeReport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportDTO {

    private final Integer user;
    private final LocalDateTime dateTime;
    private final Double totalValueInUSD;
    private final Double totalValueInPesos;
    private final List<AssetDTO> assets;

    public ReportDTO(TradedVolumeReport report) {
        this.user = report.getUser().getId();
        this.dateTime = LocalDateTime.now();
        this.totalValueInUSD = report.getTotalValueInUSD();
        this.totalValueInPesos = report.getTotalValueInPesos();
        this.assets = report.getAssets().stream().map(AssetDTO::new).collect(Collectors.toList());
    }

    public Integer getUser() {
        return user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Double getTotalValueInUSD() {
        return totalValueInUSD;
    }

    public Double getTotalValueInPesos() {
        return totalValueInPesos;
    }

    public List<AssetDTO> getAssets() {
        return assets;
    }
}
