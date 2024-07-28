package de.fynnkoch.modules.contactinformation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContactInformationFactory {

    public static ContactInformation contactInformation() {
        return ContactInformation.builder()
                .firstName("Tom")
                .lastName("Smith")
                .build();
    }
}
