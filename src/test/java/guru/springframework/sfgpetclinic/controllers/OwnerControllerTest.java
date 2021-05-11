package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult result;

    @InjectMocks
    OwnerController controller;

    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner=new Owner(1L,"Jim","Bob");
        given(result.hasErrors()).willReturn(true);
        //when
        String viewName=controller.processCreationForm(owner,result);
        //then
        assertThat(viewName).isEqualTo("owners/createOrUpdateOwnerForm");
    }

    @Test
    void processCreationFormNoErrors() {
        //given
        Owner owner=new Owner(1L,"Jim","Bob");
        given(result.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);
        //when
        String viewName=controller.processCreationForm(owner,result);
        //then
        assertThat(viewName).isEqualTo("redirect:/owners/" + owner.getId());

    }
}