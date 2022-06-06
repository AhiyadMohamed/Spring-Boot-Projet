package ma.patientmvc;

import ma.patientmvc.entities.Patient;
import ma.patientmvc.repositories.PatientRepository;
import ma.patientmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }


    //Si On Souhaite Utiliser Un Mot De Pass Ecripte sans {noop}
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(
                    new Patient(null,"hassan",new Date(),false,120));
            patientRepository.save(
                    new Patient(null,"mohamed",new Date(),true,100));
            patientRepository.save(
                    new Patient(null,"amine",new Date(),true,190));
            patientRepository.save(
                    new Patient(null,"kaoutar",new Date(),false,156));

            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());

            });
        };

    }
   //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("Mohamed","1234","1234");
            securityService.saveNewUser("Amine","1234","1234");
            securityService.saveNewUser("Hassan","1234","1234");

            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");

            securityService.addRoleToUser("Hassan","ADMIN");
            securityService.addRoleToUser("Hassan","USER");
            securityService.addRoleToUser("Amine","USER");
            securityService.addRoleToUser("Mohamed","USER");
        };
    }
}
