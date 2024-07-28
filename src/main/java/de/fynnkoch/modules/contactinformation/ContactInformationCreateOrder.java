package de.fynnkoch.modules.contactinformation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformationCreateOrder {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
