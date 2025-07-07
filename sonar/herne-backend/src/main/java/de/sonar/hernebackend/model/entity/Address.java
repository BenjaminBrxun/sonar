package de.sonar.hernebackend.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Schema(description = "Name of the street", example = "Bahnhofstraße")
    private String city;

    @Schema(description = "Name of the city", example = "Herne")
    private String street;

    @Schema(description = "House number with optional additions", example = "1")
    private String houseNumber;

    @Schema(description = "Postal code (PLZ)", example = "44623")
    private String postcode;

    @Schema(description = "City district or area", example = "Herne-Mitte")
    private String district;

}
