package de.fynnkoch.modules.contactinformation;

import de.fynnkoch.core.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation extends AbstractEntity {

    @NotNull
    @Column
    private String firstName;

    @NotNull
    @Column
    private String lastName;
}
