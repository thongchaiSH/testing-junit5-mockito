package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;


    //Convert to BDD Testing

    @Test
    void testDeleteByObject() {
        //given
        Speciality speciality=new Speciality();

        //when
        service.delete(speciality);

        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
//        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void testFindById() {
        //given
        Speciality speciality=new Speciality();
//        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //When
        Speciality foundSpecialy=service.findById(1L);

        //Then
        assertThat(foundSpecialy).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
//        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void findByIdBddTest() {
        //given
        Speciality speciality=new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality foundSpeciality=service.findById(1L);

        //then
        assertThat(foundSpeciality).isNotNull();
        //then
//        verify(specialtyRepository).findById(anyLong());
//        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //given - none

        //when
        service.deleteById(1l);
        service.deleteById(1l);

        //then
        then(specialtyRepository).should(times(2)).deleteById(1L);
//        verify(specialtyRepository,times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtLest() {
        //given - none
        //when
        service.deleteById(1l);
        service.deleteById(1l);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1l);
//        verify(specialtyRepository,atLeastOnce()).deleteById(1l); //อย่างน้อย 1 ครั้ง
    }

    @Test
    void deleteByIdAtMost() {
        //given - none

        //when
        service.deleteById(1l);
        service.deleteById(1l);

        //then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
//        verify(specialtyRepository,atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        //when
        service.deleteById(1l);
        service.deleteById(1l);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1l);
        then(specialtyRepository).should(never()).deleteById(5l);

//        verify(specialtyRepository,atLeastOnce()).deleteById(1l);
//        verify(specialtyRepository,never()).deleteById(5l);
    }


    @Test
    void testDelete() {
        service.delete(new Speciality());
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class,()->{
            specialtyRepository.delete(new Speciality());
        });

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIdThrow() {
        //given
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));
        //when
        assertThrows(RuntimeException.class,()->{
            service.findById(1L);
        });
        //then
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD() {
        //given
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
        //when
        assertThrows(RuntimeException.class,()->{
            specialtyRepository.delete(new Speciality());
        });
        //then
        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testSaveLambda() {
        //given
        final  String MATCH_ME="MATCH_ME";
        Speciality speciality=new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality saveSpeciality=new Speciality();
        saveSpeciality.setId(1L);
        given(specialtyRepository.save(argThat(argument->argument.getDescription().equals(MATCH_ME)))).willReturn(saveSpeciality);

        //when
        Speciality returnedSpeciality=service.save(speciality);
        //then
        assertThat(returnedSpeciality.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNoMatch() {
        //given
        final  String MATCH_ME="MATCH_ME";
        Speciality speciality=new Speciality();
        speciality.setDescription("No Match");

        Speciality saveSpeciality=new Speciality();
        saveSpeciality.setId(1L);
//        lenient().given(specialtyRepository.save(argThat(argument->argument.getDescription().equals(MATCH_ME)))).willReturn(saveSpeciality);
        lenient().when(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).thenReturn(saveSpeciality);
        //when
        Speciality returnedSpeciality=service.save(speciality);
        //then
        assertNull(returnedSpeciality);
    }
}